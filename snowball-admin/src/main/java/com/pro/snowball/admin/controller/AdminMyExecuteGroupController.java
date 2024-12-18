package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.MyExecuteGroup;
import com.pro.snowball.common.service.MyExecuteGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 我的模板分组管理
 */
@Api(tags = "我的模板分组")
@RestController
@RequestMapping("/myExecuteGroup")
public class AdminMyExecuteGroupController {
    @Autowired
    private MyExecuteGroupService myExecuteGroupService;

    @GetMapping("/get")
    @ApiOperation(value = "我的模板分组 查询")
    public Page<MyExecuteGroup> get(MyExecuteGroup myExecuteGroup) {
//        myExecuteGroupService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "我的模板分组 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "我的模板分组", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) MyExecuteGroup myExecuteGroup) {
        myExecuteGroupService.lambdaUpdate();
        return R.ok();
    }

}
