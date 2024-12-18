package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.MyExecuteTemplate;
import com.pro.snowball.common.service.MyExecuteTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 我的执行模板管理
 */
@Api(tags = "我的执行模板")
@RestController
@RequestMapping("/myExecuteTemplate")
public class AdminMyExecuteTemplateController {
    @Autowired
    private MyExecuteTemplateService myExecuteTemplateService;

    @GetMapping("/get")
    @ApiOperation(value = "我的执行模板 查询")
    public Page<MyExecuteTemplate> get(MyExecuteTemplate myExecuteTemplate) {
//        myExecuteTemplateService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "我的执行模板 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "我的执行模板", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) MyExecuteTemplate myExecuteTemplate) {
        myExecuteTemplateService.lambdaUpdate();
        return R.ok();
    }

}
