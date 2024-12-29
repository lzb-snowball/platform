package com.pro.snowball.common.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.service.dependencies.properties.CommonProperties;
import com.pro.common.modules.service.dependencies.util.upload.FileUploadUtils;
import com.pro.common.modules.service.dependencies.util.upload.UploadModuleModel;
import com.pro.framework.api.structure.Tuple2;
import com.pro.framework.api.util.AssertUtil;
import com.pro.framework.api.util.CollUtils;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.SnowballConst;
import com.pro.snowball.api.enums.EnumExecuteOrderState;
import com.pro.snowball.api.model.db.*;
import com.pro.snowball.common.dao.ExecuteOrderDao;
import com.pro.snowball.common.service.cmd.CmdLocalServiceImpl;
import com.pro.snowball.common.service.cmd.CmdRemoteServiceImpl;
import com.pro.snowball.common.service.cmd.ICmdLocalService;
import com.pro.snowball.common.service.cmd.ICmdRemoteService;
import com.pro.snowball.common.util.Command;
import com.pro.snowball.common.util.CommandParser;
import com.pro.snowball.common.util.TemplateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 执行订单服务
 */
@AllArgsConstructor
@Service
@Slf4j
public class ExecuteOrderService extends BaseService<ExecuteOrderDao, ExecuteOrder> {

    ThreadService threadService;
    CommonProperties commonProperties;
    ExecuteStepService executeStepService;
    ExecuteTemplateService executeTemplateService;
    MyExecuteTemplateService myExecuteTemplateService;
    ExecuteStepCommandService executeStepCommandService;
    ExecuteTemplateAndStepService executeTemplateAndStepService;
    ExecuteOrderStepCommandService executeOrderStepCommandService;
    ExecuteParamModelService executeParamModelService;
    ExecuteParamService executeParamService;
    ICmdLocalService cmdLocalService;
    ICmdRemoteService cmdRemoteService;

    public List<ExecuteOrder> getActiveList(ExecuteOrder executeOrder) {
        return this.lambdaQuery()
                .setEntity(executeOrder)
                .list();
    }

    @Override
    public boolean saveOrUpdate(ExecuteOrder entity) {
        String optType = entity.getOptType();
        boolean isNew = entity.getId() == null;
        ExecuteOrder orderOld = isNew ? null : this.getById(entity.getId());
        //noinspection SwitchStatementWithTooFewBranches
        switch (optType) {
            // 修改状态
            case "changeState":
                EnumExecuteOrderState state = entity.getState();
                switch (state) {
                    // 运行中 后台开始
                    case DOING -> {
                        String orderNo = isNew ? IdUtil.simpleUUID() : entity.getNo();
                        LocalDateTime now = LocalDateTime.now();
                        Long myTemplateId = entity.getMyTemplateId();
                        MyExecuteTemplate myTemplate = myExecuteTemplateService.getById(myTemplateId);
                        AssertUtil.notEmpty(myTemplate, "我的模板Id不存在", myTemplateId);
                        AssertUtil.isTrue(myTemplate.getEnabled(), "我的模板未开启", myTemplate.getName());

                        Long templateId = myTemplate.getTemplateId();
                        ExecuteTemplate template = executeTemplateService.getById(templateId);
                        AssertUtil.notEmpty(template, "模板Id不存在", templateId);
                        AssertUtil.isTrue(template.getEnabled(), "模板未开启", template.getName());

                        // 加载指令
                        List<ExecuteOrderStepCommand> orderStepCommands = this.loadCommands(templateId,
                                entity.getInputParamMap(), myTemplate);
                        entity.setTemplateId(templateId);
                        entity.setStepNoAll((int) orderStepCommands.stream()
                                .map(ExecuteOrderStepCommand::getStepId)
                                .distinct()
                                .count());

                        // 旧订单,先前成功的步骤不执行了
                        if (!isNew) {
                            orderStepCommands = orderStepCommands.stream()
                                    .filter(s -> s.getStepNo() >= orderOld.getStepNoCurrent())
                                    .toList();
                        }
                        // 创建工作空间
                        UploadModuleModel workspaceFolder = commonProperties.getFiles()
                                .getModules()
                                .get(SnowballConst.Str.folder_snowballWorkspace);
                        Tuple2<String, String> filePathsWorkspace = FileUploadUtils.newFolder(workspaceFolder, now,
                                orderNo);


                        // 创建日志文件
                        UploadModuleModel fileModel = commonProperties.getFiles()
                                .getModules()
                                .get(SnowballConst.Str.folder_snowballLog);
                        Tuple2<String, String> filePathsFull = FileUploadUtils.newFile(fileModel, now, null,
                                orderNo + "_error" + ".log");
                        Tuple2<String, String> filePathsError = FileUploadUtils.newFile(fileModel, now, null,
                                orderNo + "_full" + ".log");
                        entity.setNo(orderNo);
                        entity.setLogFileFull(filePathsFull.getT1());
                        entity.setLogFileError(filePathsError.getT1());
                        entity.setLogFileFullInner(filePathsFull.getT2());
                        entity.setLogFileErrorInner(filePathsError.getT2());
                        entity.setState(EnumExecuteOrderState.DOING);
                        super.saveOrUpdate(entity);
                        Long orderId = entity.getId();
                        for (ExecuteOrderStepCommand command : orderStepCommands) {
                            command.setOrderId(orderId);
                            command.setContentAfter(command.getContentAfter()
                                    .replaceAll("@/", filePathsWorkspace.getT2()));
                        }
                        executeOrderStepCommandService.saveBatch(orderStepCommands);


                        entity.setStateTime(now);

                        myExecuteTemplateService.lambdaUpdate()
                                .set(MyExecuteTemplate::getLastOrderState, entity.getState())
                                .set(MyExecuteTemplate::getLastOrderStateTime, entity.getStateTime())
                                .set(MyExecuteTemplate::getLastOrderId, entity.getId())
                                .eq(MyExecuteTemplate::getId, myTemplateId)
                                .update();


                        // 新线程 执行命令
                        List<ExecuteOrderStepCommand> finalOrderStepCommands = orderStepCommands;
                        threadService.startThread(SnowballConst.Str.THREAD_HEAD + orderNo,
                                () -> executeOrderCommands(entity, finalOrderStepCommands));
                        return true;
                    }
                    // 运行成功 自动成功
                    case SUCCESS -> {

                    }
                    // 错误终止 错误终止
                    case FAIL -> {
                    }
                    // 手动停止 手动停止
                    case STOP -> {
                        Long orderId = entity.getId();
                        if (orderId == null) {
                            return false;
                        }
                        if (threadService.isThreadRunning(SnowballConst.Str.THREAD_HEAD + orderOld.getNo())) {
                            // 结束线程
                            threadService.stopThread(SnowballConst.Str.THREAD_HEAD + orderOld.getNo());
                        }
                    }
                }
                entity.setStateTime(LocalDateTime.now());
                break;
        }
        this.beforeUpdate(entity);
        return super.saveOrUpdate(entity);
    }

    private void executeOrderCommands(ExecuteOrder entity, List<ExecuteOrderStepCommand> orderStepCommands) {
        // 执行任务 记录日志文件
        String logFileFull = entity.getLogFileFullInner();
        String logFileError = entity.getLogFileErrorInner();

        Map<Long, List<ExecuteOrderStepCommand>> listMap = orderStepCommands.stream()
                .collect(Collectors.groupingBy(ExecuteOrderStepCommand::getStepId, LinkedHashMap::new,
                        // 显式指定使用 LinkedHashMap
                        Collectors.toList()));
        List<Long> stepIds = listMap.keySet()
                .stream()
                .toList();
        boolean successAll = true;
        int stepNo;
        step:
        for (Long stepId : stepIds) {
            List<ExecuteOrderStepCommand> orderStepCommandsSub = listMap.get(stepId);
            for (ExecuteOrderStepCommand command : orderStepCommandsSub) {
                // 执行单行命令
                boolean success = this.executeOrderCommand(command, logFileFull, logFileError);
                stepNo = command.getStepNo();
                ExecuteOrder updateEntity = new ExecuteOrder();
                updateEntity.setId(entity.getId());
                updateEntity.setStepNoCurrent(stepNo);
                this.saveOrUpdate(updateEntity);
                if (!success) {
                    successAll = false;
                    // socket推送步骤更新
                    break step;
                }
            }
        }
        ExecuteOrder updateEntity = new ExecuteOrder();
        updateEntity.setId(entity.getId());
        updateEntity.setState(successAll ? EnumExecuteOrderState.SUCCESS : EnumExecuteOrderState.FAIL);
        this.saveOrUpdate(updateEntity);
    }

    private boolean executeOrderCommand(ExecuteOrderStepCommand orderCommand, String logFileFull, String logFileError) {
        List<Command> commands = CommandParser.parseCommands(orderCommand.getContentAfter());
        for (Command command : commands) {
            log.info("执行命令-开始 {}", JSONUtil.toJsonStr(command));
            // 执行一行命令
            boolean success = this.executeCommand(command, logFileFull, logFileError);
            if (!success) {
                log.warn("执行命令-异常 {}", JSONUtil.toJsonStr(command));
                return false;
            } else {
                log.info("执行命令-结束 {}", JSONUtil.toJsonStr(command));
            }
        }
//        if (orderCommand.getRemoteServerFlag()) {
//            AssertUtil.isTrue(orderCommand.getContentParamRequireds()
//                    .indexOf(Str.PARAM_MODEL_CODE_SERVERS) == 0, "服务器配置必须放在第一行");
//            Map<String, Object> inputParamMapResult = orderCommand.getInputParamMapResult();
//            //noinspection unchecked
//            List<JSONObject> servers = (List<JSONObject>) inputParamMapResult.get(Str.PARAM_MODEL_CODE_SERVERS);
//            AssertUtil.notEmpty(servers, "服务器未配置", Str.PARAM_MODEL_CODE_SERVERS);
//            List<RemoteServer> remoteServers = servers.stream()
//                    .map(s -> s.toBean(RemoteServer.class))
//                    .toList();
//            remoteServers.forEach(remoteServer -> {
//                // 远程服务器 执行
//                cmdRemoteService.execute(remoteServer, Collections.singletonList(orderCommand.getContentAfter()),
//                        logFileFull, logFileError);
//            });
//        } else {
//            // 本地 执行
//            cmdLocalService.execute(Collections.singletonList(orderCommand.getContentAfter()), logFileFull,
//                    logFileError);
//        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        Command command = JSONUtil.toBean(
                "{\"cmdType\":\"remote\",\"cmdContent\":\"cd /project/snowball/\\n                tar --no-xattr -xzf libs.tar.gz -C lib\",\"params\":{\"privateKeyLocalPath\":\"/Users/zubin/.ssh/id_rsa_github2\",\"code\":\"111.230.10.171\",\"port\":\"22\",\"name\":\"111.230.10.171服务器\",\"host\":\"111.230.10.171\",\"username\":\"root\"}}",
                Command.class);
        RemoteServer remoteServer = JSONUtil.toBean(command.getParams(), RemoteServer.class);
        boolean execute = new CmdLocalServiceImpl().execute(
                Collections.singletonList(command.getCmdContent()),
                "/Users/zubin/IdeaProjects/snowball/.idea/info.log",
                "/Users/zubin/IdeaProjects/snowball/.idea/error.log");
        System.out.println("execute=" + execute);
        Thread.sleep(2000);
    }

    private boolean executeCommand(Command command, String logFileFull, String logFileError) {
        String cmdContent = command.getCmdContent();
        //noinspection EnhancedSwitchMigration
        switch (command.getCmdType()) {
            case local:
                // 本地 执行
                return cmdLocalService.execute(Collections.singletonList(cmdContent),
                        logFileFull,
                        logFileError);
            case remote:
                // 远程服务器 执行
                RemoteServer remoteServer = JSONUtil.toBean(command.getParams(), RemoteServer.class);
                return cmdRemoteService.execute(remoteServer, Collections.singletonList(cmdContent),
                        logFileFull, logFileError);

        }
        return false;
    }

    public List<ExecuteOrderStepCommand> loadCommands(Long templateId, Map<String, Object> inputParamMap, MyExecuteTemplate myTemplate) {
        ExecuteTemplateAndStep param = new ExecuteTemplateAndStep();
        param.setTemplateId(templateId);
        List<ExecuteTemplateAndStep> templateAndSteps = executeTemplateAndStepService.getActiveList(param);

        List<Long> stepIds = templateAndSteps.stream()
                .map(ExecuteTemplateAndStep::getStepId)
                .toList();
        AssertUtil.notEmpty(stepIds, "没有有效的步骤命令");
        Map<Long, ExecuteStep> stepMap = CollUtils.listToMap(executeStepService.listByIds(stepIds)
                .stream()
                .filter(ExecuteStep::getEnabled)
                .toList(), BaseModel::getId);
        List<ExecuteStep> steps = templateAndSteps.stream()
                .map(templateAndStep -> stepMap.get(templateAndStep.getStepId()))
                .filter(Objects::nonNull)
                .filter(ExecuteStep::getEnabled)
                .toList();
        stepIds = steps.stream()
                .map(BaseModel::getId)
                .toList();
        int no = 1;
        for (ExecuteStep step : steps) {
            step.setNo(no);
            no++;
        }

        List<ExecuteStepCommand> stepCommands = executeStepCommandService.getActiveList(null, stepIds);
        AssertUtil.isTrue(!stepCommands.isEmpty(), "没有有效的步骤命令");
        Map<String, Object> inputParamMapResult;
        if (inputParamMap != null && !inputParamMap.isEmpty() && null != myTemplate) {
            inputParamMapResult = new HashMap<>(inputParamMap);
            Set<String> inputParamSet = inputParamMap.keySet();
            Map<String, ExecuteParam> paramMap = executeParamService.getParamMap(myTemplate, inputParamSet);
            // 解析value中的json数组,组成 Map<modelCode,dataCode,DataObj> paramValueMap
            Map<String, Map<String, JSONObject>> paramValueMap = paramMap.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, e -> jsonArrayToCodeMap(e.getValue()
                            .getValue())));
            List<ExecuteParamModel> models = executeParamModelService.lambdaQuery()
                    .eq(ExecuteParamModel::getEnabled, true)
                    .in(ExecuteParamModel::getCode, inputParamSet)
                    .list();
            models.forEach(model -> {
                String modelCode = model.getCode();
                Object value = inputParamMapResult.get(modelCode);
                if (model.getMultipleField()) {
                    // code 转 对象 参数转成对象 List<JSONObject>
                    //noinspection unchecked
                    List<JSONObject> values = ((List<String>) value).stream()
                            .map(val -> paramValueMap.get(modelCode)
                                    .get(val))
                            .toList();
                    inputParamMapResult.put(modelCode, values);
                } else {
                    if (value instanceof String && model.getMultipleValue()) {
                        // 多值切割处理
                        List<String> values = Arrays.stream(((String) value).split("\n"))
                                .toList();
                        inputParamMapResult.put(modelCode, values);
                    }
                }
            });
        } else {
            inputParamMapResult = null;
        }

        Map<Long, List<ExecuteStepCommand>> commandListMap = stepCommands.stream()
                .collect(Collectors.groupingBy(ExecuteStepCommand::getStepId));
        return steps.stream()
                .flatMap(step -> {
                    List<ExecuteStepCommand> executeStepCommands = commandListMap.get(step.getId());
                    return IntStream.range(0, executeStepCommands.size())
                            .mapToObj(i -> {
                                // 保存执行的命令行
                                ExecuteStepCommand command = executeStepCommands.get(i);
                                String content = command.getContent();
                                List<String> paramCodeRequireds = TemplateUtil.extractRootVariables(content);
                                String contentAfter = "";
                                if (inputParamMapResult != null) {
                                    Set<String> inputParamSet = inputParamMapResult.keySet();
                                    for (String paramCode : paramCodeRequireds) {
                                        AssertUtil.isTrue(inputParamSet.contains(paramCode), "_参数指定", paramCode);
                                    }
                                    // 带参数解析xml内容
                                    log.info("解析 {} {} \n{}", step.getName(), inputParamMapResult, content);
                                    contentAfter = TemplateUtil.analysis(content, inputParamMapResult);
                                }
                                //noinspection UnnecessaryLocalVariable
                                ExecuteOrderStepCommand build = ExecuteOrderStepCommand.builder()
                                        .no(i + 1)
                                        //.orderId(orderId)
                                        .stepId(step.getId())
                                        .stepName(step.getName())
                                        .stepSort(step.getSort())
                                        .stepNo(step.getNo())
                                        .content(content)
//                                        .remoteServerFlag(command.getRemoteServerFlag())
                                        .contentParamRequired(String.join(",", paramCodeRequireds))
                                        .contentParamRequireds(paramCodeRequireds)
                                        .contentParamJson(JSONUtil.toJsonStr(inputParamMapResult))
                                        .inputParamMapResult(inputParamMapResult)
                                        .contentAfter(contentAfter)
                                        //.success(false)
                                        //.remark("")
                                        .build();
                                return build;
                            });
                })
                .toList();
    }

    @Override
    public boolean updateById(ExecuteOrder entity) {
        this.beforeUpdate(entity);
        return super.updateById(entity);
    }

    private void beforeUpdate(ExecuteOrder entity) {
        myExecuteTemplateService.lambdaUpdate()
                .set(MyExecuteTemplate::getLastOrderState, entity.getState())
                .set(MyExecuteTemplate::getLastOrderStateTime, entity.getStateTime())
                .eq(MyExecuteTemplate::getLastOrderId, entity.getId())
                .update();
    }

    private static Map<String, JSONObject> jsonArrayToCodeMap(String jsonString) {
        if (!JSONUtil.isTypeJSONArray(jsonString)) {
            return Collections.emptyMap();
        }
        // 将 JSON 字符串解析为 JSONArray
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        JSONArray jsonArray = new JSONArray(jsonString);

        // 创建一个空的 Map 来存储结果
        Map<String, JSONObject> resultMap = new HashMap<>();

        // 遍历 JSONArray，将每个元素的 code 作为键，整个 JSONObject 作为值放入 Map
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String code = jsonObject.getStr(SnowballConst.Str.KEY_PROP_NAME_CODE);
            resultMap.put(code, jsonObject);
        }

        return resultMap;
    }
}
