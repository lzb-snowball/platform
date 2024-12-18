package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.ExecuteTemplateAndStep;
import com.pro.snowball.common.service.ExecuteTemplateAndStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 执行模板和步骤的关联管理
 */
@Api(tags = "执行模板和步骤的关联")
@RestController
@RequestMapping("/executeTemplateAndStep")
public class AdminExecuteTemplateAndStepController {
    @Autowired
    private ExecuteTemplateAndStepService executeTemplateAndStepService;

    @GetMapping("/get")
    @ApiOperation(value = "执行模板和步骤的关联 查询")
    public Page<ExecuteTemplateAndStep> get(ExecuteTemplateAndStep executeTemplateAndStep) {
//        executeTemplateAndStepService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "执行模板和步骤的关联 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "执行模板和步骤的关联", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) ExecuteTemplateAndStep executeTemplateAndStep) {
        executeTemplateAndStepService.lambdaUpdate();
        return R.ok();
    }

}
