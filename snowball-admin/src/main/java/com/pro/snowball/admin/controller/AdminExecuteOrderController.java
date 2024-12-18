package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.ExecuteOrder;
import com.pro.snowball.common.service.ExecuteOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 执行订单管理
 */
@Api(tags = "执行订单")
@RestController
@RequestMapping("/executeOrder")
public class AdminExecuteOrderController {
    @Autowired
    private ExecuteOrderService executeOrderService;

    @GetMapping("/get")
    @ApiOperation(value = "执行订单 查询")
    public Page<ExecuteOrder> get(ExecuteOrder executeOrder) {
//        executeOrderService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "执行订单 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "执行订单", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) ExecuteOrder executeOrder) {
        executeOrderService.lambdaUpdate();
        return R.ok();
    }

}
