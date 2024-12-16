package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.snowball.api.model.db.Demo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${label}管理
 */
@Api(tags = "${label}")
@RestController
@RequestMapping("/demo")
public class AdminDemoController {
//    @Autowired
//    private DemoService demoService;

    @GetMapping("/get")
    @ApiOperation(value = "${label} 复杂查询")
    public Page<Demo> get(Demo demo) {
//        demoService.lambdaQuery();
        return null;
    }
}
