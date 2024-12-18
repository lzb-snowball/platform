package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.ExecuteStep;
import com.pro.snowball.common.service.ExecuteStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 执行步骤管理
 */
@Api(tags = "执行步骤")
@RestController
@RequestMapping("/executeStep")
public class AdminExecuteStepController {
    @Autowired
    private ExecuteStepService executeStepService;

    @GetMapping("/get")
    @ApiOperation(value = "执行步骤 查询")
    public Page<ExecuteStep> get(ExecuteStep executeStep) {
//        executeStepService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "执行步骤 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "执行步骤", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) ExecuteStep executeStep) {
        executeStepService.lambdaUpdate();
        return R.ok();
    }

}
