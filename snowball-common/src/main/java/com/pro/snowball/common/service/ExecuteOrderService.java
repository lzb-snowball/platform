package com.pro.snowball.common.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pro.common.modules.api.dependencies.message.ToSocket;
import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.service.dependencies.modelauth.base.MessageService;
import com.pro.common.modules.service.dependencies.properties.CommonProperties;
import com.pro.common.modules.service.dependencies.util.SpringContextUtils;
import com.pro.common.modules.service.dependencies.util.upload.FileUploadUtils;
import com.pro.common.modules.service.dependencies.util.upload.UploadModuleModel;
import com.pro.framework.api.structure.Tuple2;
import com.pro.framework.api.util.AssertUtil;
import com.pro.framework.api.util.BeanUtils;
import com.pro.framework.api.util.CollUtils;
import com.pro.framework.api.util.StrUtils;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.SnowballConst;
import com.pro.snowball.api.enums.EnumExecuteOrderState;
import com.pro.snowball.api.model.db.*;
import com.pro.snowball.api.model.vo.RemoteServer;
import com.pro.snowball.common.dao.ExecuteOrderDao;
import com.pro.snowball.common.service.cmd.*;
import com.pro.snowball.common.util.Command;
import com.pro.snowball.common.util.CommandParser;
import com.pro.snowball.common.util.TemplateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.ExecuteException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
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

    /**
     * 正在进行中的步骤默认加0.1
     */
    public static final BigDecimal STEP_NO_DOING = new BigDecimal("0.1");
    MessageService messageService;
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
    List<ICmdService> cmdServices;

    public List<ExecuteOrder> getActiveList(ExecuteOrder executeOrder) {
        return this.lambdaQuery()
                .setEntity(executeOrder)
                .list();
    }

    @Override
//    @Transactional
    public boolean saveOrUpdate(ExecuteOrder entity) {
        String optType = entity.getOptType();
        Long orderId = entity.getId();
        boolean isNew = orderId == null;

        //noinspection SwitchStatementWithTooFewBranches
        switch (optType) {
            // 修改状态
            case "changeState":
                EnumExecuteOrderState state = entity.getState();
                switch (state) {
                    // 运行中 后台开始
                    case DOING -> {
                        ExecuteOrder order;
                        if (isNew) {
                            order = entity;
                        } else {
                            order = this.getById(orderId);
                            BeanUtils.copyProperties(entity, order);
                            order.setInputParamMap(entity.getInputParamMap());
                        }
                        this.startOrder(order, isNew, orderId);
                    }
                    // 运行成功 自动成功
                    case SUCCESS -> {

                    }
                    // 错误终止 错误终止
                    case FAIL -> {
                    }
                    // 手动停止 手动停止
                    case STOP -> {
                        if (orderId == null) {
                            return false;
                        }
                        if (threadService.isThreadRunning(SnowballConst.Str.THREAD_HEAD + orderId)) {
                            // 结束线程
                            ExecuteOrder order;
                            order = this.getById(orderId);
                            cmdServices.forEach(service -> service.destroy(getOrderKey(order)));
                            threadService.stopThread(SnowballConst.Str.THREAD_HEAD + orderId);
                        }
                    }
                }
                entity.setStateTime(LocalDateTime.now());
                break;
        }
        this.beforeUpdate(entity);
        return super.saveOrUpdate(entity);
    }

    private void startOrder(ExecuteOrder order, boolean isNew, Long orderId) {
//                        String orderNo = isNew ? IdUtil.simpleUUID() : entity.getNo();
        LocalDateTime now = LocalDateTime.now();
        Long myTemplateId = order.getMyTemplateId();
        MyExecuteTemplate myTemplate = myExecuteTemplateService.getById(myTemplateId);
        AssertUtil.notEmpty(myTemplate, "我的模板Id不存在", myTemplateId);
        AssertUtil.isTrue(myTemplate.getEnabled(), "我的模板未开启", myTemplate.getName());

        Long templateId = myTemplate.getTemplateId();
        ExecuteTemplate template = executeTemplateService.getById(templateId);
        AssertUtil.notEmpty(template, "模板Id不存在", templateId);
        AssertUtil.isTrue(template.getEnabled(), "模板未开启", template.getName());

        // 加载指令
        List<ExecuteOrderStepCommand> orderStepCommands = this.loadCommands(templateId,
                order.getInputParamMap(), myTemplate);
        order.setTemplateId(templateId);
        order.setStepNoAll(new BigDecimal(orderStepCommands.stream()
                .map(ExecuteOrderStepCommand::getStepId)
                .distinct()
                .count()));


        // 旧订单,先前成功的步骤不执行了
        if (isNew) {
            order.setStepNoCurrent(BigDecimal.ZERO);
            super.save(order);
            orderId = order.getId();
            myExecuteTemplateService.lambdaUpdate()
                    .set(MyExecuteTemplate::getLastOrderId, orderId)
                    .eq(MyExecuteTemplate::getId, myTemplateId).update();
        } else {
            orderStepCommands = orderStepCommands.stream()
                    .filter(s -> s.getStepNo().compareTo(order.getStepNoCurrent()) >= 0)
                    .toList();
        }
        assert orderId != null;
        String orderIdStr = orderId.toString();
        String filePathsWorkspaceInner;
        if (isNew) {
            // 创建工作空间
            UploadModuleModel workspaceFolder = commonProperties.getFiles()
                    .getModules()
                    .get(SnowballConst.Str.folder_snowballWorkspace);
            Tuple2<String, String> filePathsWorkspace = FileUploadUtils.newFolder(workspaceFolder, now,
                    orderIdStr);
            filePathsWorkspaceInner = filePathsWorkspace.getT2();
            order.setFilePathsWorkspaceInner(filePathsWorkspaceInner);

            // 创建日志文件
            UploadModuleModel fileModel = commonProperties.getFiles()
                    .getModules()
                    .get(SnowballConst.Str.folder_snowballLog);
            Tuple2<String, String> filePathsFull = FileUploadUtils.newFile(fileModel, now, null,
                    orderId + "_full" + ".log", true);
            Tuple2<String, String> filePathsError = FileUploadUtils.newFile(fileModel, now, null,
                    orderIdStr + "_error" + ".log", true);
            order.setLogFileFull(FileUploadUtils.FILE_PREPEND + filePathsFull.getT1());
            order.setLogFileError(FileUploadUtils.FILE_PREPEND + filePathsError.getT1());
            order.setLogFileFullInner(filePathsFull.getT2());
            order.setLogFileErrorInner(filePathsError.getT2());

        } else {
            filePathsWorkspaceInner = order.getFilePathsWorkspaceInner();
            // 清空日志 避免日志污染
            FileUtil.writeUtf8String("", new File(order.getLogFileFullInner()));
            FileUtil.writeUtf8String("", new File(order.getLogFileErrorInner()));
        }
        order.setState(EnumExecuteOrderState.DOING);
        order.setStateTime(now);
        for (ExecuteOrderStepCommand command : orderStepCommands) {
            command.setOrderId(orderId);
            command.setContentAfter(command.getContentAfter()
                    .replaceAll("@/", filePathsWorkspaceInner));
        }
        executeOrderStepCommandService.saveBatch(orderStepCommands);

        List<ExecuteOrderStepCommand> finalOrderStepCommands = orderStepCommands;

        // 新线程
        threadService.startThread(SnowballConst.Str.THREAD_HEAD + orderIdStr,
                () -> SpringContextUtils.getBean(getClass())
                        // 执行命令
                        .executeOrderCommands(order, finalOrderStepCommands));
    }

    //    @Transactional
    public void executeOrderCommands(ExecuteOrder entity, List<ExecuteOrderStepCommand> orderStepCommands) {
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
        BigDecimal stepNo;

        for (int i = 0; i < stepIds.size(); i++) {
            Long stepId = stepIds.get(i);
            List<ExecuteOrderStepCommand> orderStepCommandsSub = listMap.get(stepId);
            stepNo = orderStepCommandsSub.get(0).getStepNo();
            boolean successStep = true;
            // 结束了
            ExecuteOrder updateEntity = new ExecuteOrder();
            updateEntity.setId(entity.getId());
            updateEntity.setTemplateId(entity.getTemplateId());
            // 1-0.9=0.1=10% 表示刚开始
            updateEntity.setStepNoCurrent(stepNo.subtract(BigDecimal.ONE).add(STEP_NO_DOING));
            this.updateOrder(updateEntity);

            for (ExecuteOrderStepCommand command : orderStepCommandsSub) {
                stepNo = command.getStepNo();
                // 执行单行命令
                boolean success = this.executeOrderCommand(entity, command, logFileFull, logFileError);
                if (!success) {
                    successStep = false;
                    // socket推送步骤更新
                    break;
                }
            }
            // 结束了
            if (successStep) {
                updateEntity.setStepNoCurrent(stepNo);
            }
            if (!successStep || i == (stepIds.size() - 1)) {
                updateEntity.setState(successStep ? EnumExecuteOrderState.SUCCESS : EnumExecuteOrderState.FAIL);
            }
            updateOrder(updateEntity);
            if (!successStep) {
                break;
            }
        }
    }

    private void updateOrder(ExecuteOrder updateEntity) {
        this.beforeUpdate(updateEntity);
        super.saveOrUpdate(updateEntity);
        messageService.sendToManager(ToSocket.toAllUser(SnowballConst.EntityClass.ExecuteOrder, updateEntity));
    }

    private boolean executeOrderCommand(ExecuteOrder order, ExecuteOrderStepCommand orderCommand, String logFileFull, String logFileError) {
        List<Command> commands = CommandParser.parseCommands(orderCommand.getContentAfter());
        for (Command command : commands) {
            log.info("执行命令-开始 {}", JSONUtil.toJsonStr(command));
            // 执行一行命令
            boolean success = false;
            try {
                success = this.executeCommand(order, command, logFileFull, logFileError);
            } catch (Exception e) {
                //noinspection ConstantValue
                if (e instanceof InterruptedException) {
                    log.info("执行命令-手动终止 {}", JSONUtil.toJsonStr(command));
                    return false;
                } else if (e instanceof ExecuteException && !CmdLocalServiceImpl.processMap.containsKey(
                        getOrderKey(order))) {
                    log.info("执行命令-手动终止 {}", JSONUtil.toJsonStr(command));
                    return false;
                } else {
                    throw e;
                }
            }
            if (!success) {
                log.warn("执行命令-异常 {}", JSONUtil.toJsonStr(command));
                return false;
            } else {
                log.info("执行命令-结束 {}", JSONUtil.toJsonStr(command));
            }
        }
        return true;
    }

    private boolean executeCommand(ExecuteOrder order, Command command, String logFileFull, String logFileError) {
        String cmdContent = command.getCmdContent();
        String orderKey = getOrderKey(order);
        //noinspection EnhancedSwitchMigration
        switch (command.getCmdType()) {
            case local:
                // 本地 执行
                return cmdLocalService.execute(Collections.singletonList(cmdContent),
                        logFileFull,
                        logFileError, orderKey);
            case remote:
                // 远程服务器 执行
                RemoteServer remoteServer = JSONUtil.toBean(command.getParams(), RemoteServer.class);
                return cmdRemoteService.execute(remoteServer, Collections.singletonList(cmdContent),
                        logFileFull, logFileError, orderKey);

        }
        return false;
    }

    private static String getOrderKey(ExecuteOrder order) {
        return "ExecuteOrder_" + order.getId().toString();
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
            step.setNo(new BigDecimal(no));
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
                List<String> valueCodes = Collections.emptyList();
                List<Object> valueObjects = new ArrayList<>();
                // 多选中值
                if (model.getMultipleValue()) {
                    if (value instanceof String) {
                        // 多值切割处理
                        valueCodes = Arrays.stream(((String) value).split("\n")).filter(StrUtils::isNotBlank).toList();
                    } else if (value instanceof Collection) {
                        valueCodes = ((Collection<?>) value).stream().map(Object::toString).filter(StrUtils::isNotBlank).toList();
                    }
                }
                // 单选中值
                else {
                    valueCodes = Collections.singletonList(value.toString());
                }
                // 多属性(对象)
                if (model.getMultipleField()) {
                    // code 转 对象 参数转成对象 List<JSONObject>
                    for (String valueCode : valueCodes) {
                        valueObjects.add(paramValueMap.get(modelCode).get(valueCode));
                    }
                } else {
                    valueObjects.addAll(valueCodes);
                }
                inputParamMapResult.put(modelCode, model.getMultipleValue() ? valueObjects : (valueObjects.isEmpty() ? null : valueObjects.get(0)));
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
                                        AssertUtil.isTrue(inputParamSet.contains(paramCode), "_参数未指定", paramCode);
                                    }
                                    // 带参数解析xml内容
                                    contentAfter = TemplateUtil.analysis(content, inputParamMapResult);
                                    log.info("解析 {} {} \n{} \n\n {}", step.getName(), JSONUtil.toJsonStr(inputParamMapResult), content, contentAfter);
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
        messageService.sendToManager(ToSocket.toAllUser(SnowballConst.EntityClass.ExecuteOrder, entity));
        this.beforeUpdate(entity);
        return super.updateById(entity);
    }

    private void beforeUpdate(ExecuteOrder entity) {
        LocalDateTime stateTime = entity.getStateTime();
        EnumExecuteOrderState state = entity.getState();
        BigDecimal stepNoAll = entity.getStepNoAll();
        BigDecimal stepNoCurrent = entity.getStepNoCurrent();
        if (state != null || stateTime != null || stepNoAll != null || stepNoCurrent != null) {
            List<MyExecuteTemplate> templates = myExecuteTemplateService.lambdaQuery()
                    .eq(MyExecuteTemplate::getLastOrderId, entity.getId())
                    .list();
            for (MyExecuteTemplate template : templates) {
                MyExecuteTemplate update = new MyExecuteTemplate();
                update.setId(template.getId());

                update.setLastOrderStateTime(stateTime);
                update.setLastOrderState(state);
                update.setLastOrderStepNoAll(stepNoAll);
                update.setLastOrderStepNoCurrent(stepNoCurrent);
                myExecuteTemplateService.updateById(update);
                messageService.sendToManager(ToSocket.toAllUser(SnowballConst.EntityClass.MyExecuteTemplate, update));
            }
        }
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
