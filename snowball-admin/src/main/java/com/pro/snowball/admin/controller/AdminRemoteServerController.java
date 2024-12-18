package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.RemoteServer;
import com.pro.snowball.common.service.RemoteServerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 远程服务器管理
 */
@Api(tags = "远程服务器")
@RestController
@RequestMapping("/remoteServer")
public class AdminRemoteServerController {
    @Autowired
    private RemoteServerService remoteServerService;

    @GetMapping("/get")
    @ApiOperation(value = "远程服务器 查询")
    public Page<RemoteServer> get(RemoteServer remoteServer) {
//        remoteServerService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "远程服务器 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "远程服务器", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) RemoteServer remoteServer) {
        remoteServerService.lambdaUpdate();
        return R.ok();
    }

}
