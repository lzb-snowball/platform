package com.pro.snowball.admin.controller;

import com.pro.common.modules.api.dependencies.R;
import com.pro.snowball.api.model.db.*;
import com.pro.snowball.common.service.ExecuteOrderService;
import com.pro.snowball.common.service.ExecuteParamService;
import com.pro.snowball.common.service.MyExecuteTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 执行管理
 */
@Api(tags = "执行")
@RestController
@RequestMapping("/execute")
public class AdminExecuteController {
    @Autowired
    private MyExecuteTemplateService myExecuteTemplateService;
    @Autowired
    private ExecuteOrderService executeOrderService;
    @Autowired
    private ExecuteParamService executeParamService;

    @GetMapping("/loadCommands")
    @ApiOperation(value = "查询执行所需参数")
    public R<ExecuteOrder> get(ExecuteOrder request) {
        Long myTemplateId = request.getMyTemplateId();

        MyExecuteTemplate myTemplate = myExecuteTemplateService.getById(myTemplateId);
        Map<String, ExecuteParam> paramMap = executeParamService.getParamMap(myTemplate, null);
        Set<String> modelCodes = paramMap.keySet();
        List<ExecuteOrderStepCommand> commands = executeOrderService.loadCommands(myTemplate.getTemplateId(), null, myTemplate);
        for (ExecuteOrderStepCommand command : commands) {
            List<String> contentParamRequireds = new ArrayList<>(command.getContentParamRequireds());
            contentParamRequireds.removeAll(modelCodes);
            // 未配置参数
            command.setContentParamRequiredsLack(contentParamRequireds);
        }
        ExecuteOrder order =new ExecuteOrder();
        order.setOrderStepCommands(commands);
        order.setExecuteParamMap(paramMap);
        return R.ok(order);
    }


}
