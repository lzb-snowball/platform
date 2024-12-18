package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.MyExecuteTemplateParam;
import com.pro.snowball.common.service.MyExecuteTemplateParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 我的执行模板的参数管理
 */
@Api(tags = "我的执行模板的参数")
@RestController
@RequestMapping("/myExecuteTemplateParam")
public class AdminMyExecuteTemplateParamController {
    @Autowired
    private MyExecuteTemplateParamService myExecuteTemplateParamService;

    @GetMapping("/get")
    @ApiOperation(value = "我的执行模板的参数 查询")
    public Page<MyExecuteTemplateParam> get(MyExecuteTemplateParam myExecuteTemplateParam) {
//        myExecuteTemplateParamService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "我的执行模板的参数 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "我的执行模板的参数", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) MyExecuteTemplateParam myExecuteTemplateParam) {
        myExecuteTemplateParamService.lambdaUpdate();
        return R.ok();
    }

}
