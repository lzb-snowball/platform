package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.ExecuteOrderStepCommand;
import com.pro.snowball.common.service.ExecuteOrderStepCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 执行订单步骤管理
 */
@Api(tags = "执行订单步骤")
@RestController
@RequestMapping("/executeOrderStepCommand")
public class AdminExecuteOrderStepCommandController {
    @Autowired
    private ExecuteOrderStepCommandService executeOrderStepCommandService;

    @GetMapping("/get")
    @ApiOperation(value = "执行订单步骤 查询")
    public Page<ExecuteOrderStepCommand> get(ExecuteOrderStepCommand executeOrderStepCommand) {
//        executeOrderStepCommandService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "执行订单步骤 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "执行订单步骤", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) ExecuteOrderStepCommand executeOrderStepCommand) {
        executeOrderStepCommandService.lambdaUpdate();
        return R.ok();
    }

}
