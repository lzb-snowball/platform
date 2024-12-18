package com.pro.snowball.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pro.common.modules.api.dependencies.R;
import com.pro.framework.api.enums.EnumMethodType;
import com.pro.snowball.api.model.db.ExecuteStepCommand;
import com.pro.snowball.common.service.ExecuteStepCommandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 执行步骤命令行管理
 */
@Api(tags = "执行步骤命令行")
@RestController
@RequestMapping("/executeStepCommand")
public class AdminExecuteStepCommandController {
    @Autowired
    private ExecuteStepCommandService executeStepCommandService;

    @GetMapping("/get")
    @ApiOperation(value = "执行步骤命令行 查询")
    public Page<ExecuteStepCommand> get(ExecuteStepCommand executeStepCommand) {
//        executeStepCommandService.lambdaQuery();
        return null;
    }
    @PostMapping("/post")
    @ApiOperation(value = "执行步骤命令行 提交")
    // todo 其他接口鉴权问题
//    @LogAnnotation(module = "energy", desc = "执行步骤命令行", methodType = EnumMethodType.UPDATE)
    public R<?> post(@RequestBody(required = false) ExecuteStepCommand executeStepCommand) {
        executeStepCommandService.lambdaUpdate();
        return R.ok();
    }

}
