package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.ExecuteTemplate;
import com.pro.snowball.common.service.ExecuteTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 执行模板的初始配置管理
 */
@Api(tags = "执行模板的初始配置")
@RestController
@RequestMapping("/executeTemplate")
public class AdminExecuteTemplateController {
    @Autowired
    private ExecuteTemplateService executeTemplateService;

    @GetMapping("/get")
    @ApiOperation(value = "执行模板的初始配置 查询")
    public Page<ExecuteTemplate> get(ExecuteTemplate executeTemplate) {
//        executeTemplateService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "执行模板的初始配置 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "执行模板的初始配置", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) ExecuteTemplate executeTemplate) {
        executeTemplateService.lambdaUpdate();
        return R.ok();
    }

}
