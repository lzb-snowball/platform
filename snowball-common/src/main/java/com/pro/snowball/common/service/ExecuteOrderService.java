package com.pro.snowball.common.service;

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
import com.pro.snowball.api.enums.EnumExecuteOrderState;
import com.pro.snowball.api.model.db.*;
import com.pro.snowball.common.dao.ExecuteOrderDao;
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

    public List<ExecuteOrder> getActiveList(ExecuteOrder executeOrder) {
        return this.lambdaQuery().setEntity(executeOrder)
                .list();
    }

    @Override
    public boolean saveOrUpdate(ExecuteOrder entity) {
        String optType = entity.getOptType();
        //noinspection SwitchStatementWithTooFewBranches
        switch (optType) {
            // 修改状态
            case "changeState":
                EnumExecuteOrderState state = entity.getState();
                switch (state) {
                    // 运行中 后台开始
                    case DOING -> {
                        entity.setId(null);
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
                        List<ExecuteOrderStepCommand> orderStepCommands = this.loadCommands(templateId, entity.getInputParamMap(), myTemplate);
                        entity.setTemplateId(templateId);
                        entity.setStepNoAll((int) orderStepCommands.stream().map(ExecuteOrderStepCommand::getStepId).distinct().count());
                        super.save(entity);
                        Long orderId = entity.getId();
                        for (ExecuteOrderStepCommand command : orderStepCommands) {
                            command.setOrderId(orderId);
                        }
                        executeOrderStepCommandService.saveBatch(orderStepCommands);
                        String orderKey = "executeOrder_" + orderId;

                        // 创建日志文件
                        UploadModuleModel fileModel = commonProperties.getFiles().getModules().get(commonProperties.getPlatform());
                        Tuple2<String, String> filePathsFull = FileUploadUtils.newFile(fileModel, now, "full", orderKey + ".log");
                        Tuple2<String, String> filePathsError = FileUploadUtils.newFile(fileModel, now, "error", orderKey + ".log");
                        entity.setLogFileFull(filePathsFull.getT2());
                        entity.setLogFileFull(filePathsError.getT2());
                        entity.setStateTime(now);
                        threadService.startThread(orderKey, () -> {
                            // 执行任务 记录日志文件
                            String logFileFull = filePathsFull.getT1();
                            String logFileError = filePathsError.getT1();
                            orderStepCommands.forEach(command -> {
                                //执行 command 记录日志
                            });
                        });
                        myExecuteTemplateService.lambdaUpdate()
                                .set(MyExecuteTemplate::getLastOrderState, entity.getState())
                                .set(MyExecuteTemplate::getLastOrderStateTime, entity.getStateTime())
                                .set(MyExecuteTemplate::getLastOrderId, entity.getId())
                                .eq(MyExecuteTemplate::getId, myTemplateId)
                                .update();
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
                    }
                }
                entity.setStateTime(LocalDateTime.now());
                break;
        }
        this.beforeUpdate(entity);
        return super.saveOrUpdate(entity);
    }

    //    public List<ExecuteOrderStepCommand> loadCommandsParams(Long templateId) {
//        List<ExecuteOrderStepCommand> orderStepCommands = loadCommands(templateId);
//        orderStepCommands.set
//        XmlParser.extractParameters()
//    }
    public List<ExecuteOrderStepCommand> loadCommands(Long templateId, Map<String, Object> inputParamMap, MyExecuteTemplate myTemplate) {
        ExecuteTemplateAndStep param = new ExecuteTemplateAndStep();
        param.setTemplateId(templateId);
        List<ExecuteTemplateAndStep> templateAndSteps = executeTemplateAndStepService.getActiveList(param);

        List<Long> stepIds = templateAndSteps.stream().map(ExecuteTemplateAndStep::getStepId).toList();
        Map<Long, ExecuteStep> stepMap = CollUtils.listToMap(executeStepService.listByIds(stepIds).stream().filter(ExecuteStep::getEnabled).toList(), BaseModel::getId);
        List<ExecuteStep> steps = templateAndSteps.stream().map(templateAndStep -> stepMap.get(templateAndStep.getStepId())).filter(Objects::nonNull).toList();


        List<ExecuteStepCommand> stepCommands = executeStepCommandService.getActiveList(null, stepIds);
        AssertUtil.isTrue(!stepCommands.isEmpty(), "没有有效的步骤命令");

        if (inputParamMap != null && !inputParamMap.isEmpty() && null != myTemplate) {
            Set<String> inputParamSet = inputParamMap.keySet();
            Map<String, ExecuteParam> paramMap = executeParamService.getParamMap(myTemplate, inputParamSet);
            // 解析value中的json数组,组成 Map<modelCode,dataCode,DataObj> paramValueMap
            Map<String, Map<String, JSONObject>> paramValueMap = paramMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> jsonArrayToMap(e.getValue().getValue())));
            List<ExecuteParamModel> models = executeParamModelService.lambdaQuery().eq(ExecuteParamModel::getEnabled, true).in(ExecuteParamModel::getCode, inputParamSet).list();
            models.forEach(model -> {
                String modelCode = model.getCode();
                Object value = inputParamMap.get(modelCode);
                if (model.getMultipleField()) {
                    // code 转 对象
                    //noinspection unchecked
                    List<JSONObject> values = ((List<String>) value).stream().map(val -> paramValueMap.get(modelCode).get(val)).toList();
                    inputParamMap.put(modelCode, values);
                } else {
                    if (value instanceof String && model.getMultipleValue()) {
                        // 多值切割处理
                        List<String> values = Arrays.stream(((String) value).split("\n")).toList();
                        inputParamMap.put(modelCode, values);
                    }
                }
            });
        }

        Map<Long, List<ExecuteStepCommand>> commandListMap = stepCommands.stream().collect(Collectors.groupingBy(ExecuteStepCommand::getStepId));
        return steps.stream().flatMap(step -> {
            List<ExecuteStepCommand> executeStepCommands = commandListMap.get(step.getId());
            return IntStream.range(0, executeStepCommands.size())
                    .mapToObj(i -> {
                        // 保存执行的命令行
                        ExecuteStepCommand command = executeStepCommands.get(i);
                        String content = command.getContent();
                        List<String> paramCodeRequireds = TemplateUtil.extractRootVariables(content);
                        String contentAfter = "";
                        if (inputParamMap != null) {
                            Set<String> inputParamSet = inputParamMap.keySet();
                            for (String paramCode : paramCodeRequireds) {
                                AssertUtil.isTrue(inputParamSet.contains(paramCode), "_参数指定", paramCode);
                            }
                            // 带参数解析xml内容
                            log.info("解析 {} {} \n{}", step.getName(), inputParamMap, content);
                            contentAfter = TemplateUtil.analysis(content, inputParamMap);
                        }
                        //noinspection UnnecessaryLocalVariable
                        ExecuteOrderStepCommand build = ExecuteOrderStepCommand.builder()
                                .no(i + 1)
                                //.orderId(orderId)
                                .stepId(step.getId())
                                .stepName(step.getName())
                                .stepSort(step.getSort())
                                .content(content)
                                .contentParamRequired(String.join(",", paramCodeRequireds))
                                .contentParamRequireds(paramCodeRequireds)
                                .contentParamJson(JSONUtil.toJsonStr(inputParamMap))
                                .contentAfter(contentAfter)
                                //.success(false)
                                //.remark("")
                                .build();
                        return build;
                    });
        }).toList();
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

    private static Map<String, JSONObject> jsonArrayToMap(String jsonString) {
        if (!JSONUtil.isTypeJSONArray(jsonString)) {
            return Collections.emptyMap();
        }
        // 将 JSON 字符串解析为 JSONArray
        JSONArray jsonArray = new JSONArray(jsonString);

        // 创建一个空的 Map 来存储结果
        Map<String, JSONObject> resultMap = new HashMap<>();

        // 遍历 JSONArray，将每个元素的 code 作为键，整个 JSONObject 作为值放入 Map
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String code = jsonObject.getStr("code");
            resultMap.put(code, jsonObject);
        }

        return resultMap;
    }
}
