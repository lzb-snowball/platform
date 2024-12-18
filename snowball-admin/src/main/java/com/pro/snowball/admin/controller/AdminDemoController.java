package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.Demo;
import com.pro.snowball.common.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ${label}管理
 */
@Api(tags = "${label}")
@RestController
@RequestMapping("/demo")
public class AdminDemoController {
    @Autowired
    private DemoService demoService;

    @GetMapping("/get")
    @ApiOperation(value = "${label} 查询")
    public Page<Demo> get(Demo demo) {
//        demoService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "${label} 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "${label}", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) Demo demo) {
        demoService.lambdaUpdate();
        return R.ok();
    }

}
