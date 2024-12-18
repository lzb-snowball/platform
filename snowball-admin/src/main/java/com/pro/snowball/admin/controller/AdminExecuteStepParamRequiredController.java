package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.ExecuteStepParamRequired;
import com.pro.snowball.common.service.ExecuteStepParamRequiredService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 执行步骤需要的参数管理
 */
@Api(tags = "执行步骤需要的参数")
@RestController
@RequestMapping("/executeStepParamRequired")
public class AdminExecuteStepParamRequiredController {
    @Autowired
    private ExecuteStepParamRequiredService executeStepParamRequiredService;

    @GetMapping("/get")
    @ApiOperation(value = "执行步骤需要的参数 查询")
    public Page<ExecuteStepParamRequired> get(ExecuteStepParamRequired executeStepParamRequired) {
//        executeStepParamRequiredService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "执行步骤需要的参数 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "执行步骤需要的参数", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) ExecuteStepParamRequired executeStepParamRequired) {
        executeStepParamRequiredService.lambdaUpdate();
        return R.ok();
    }

}
