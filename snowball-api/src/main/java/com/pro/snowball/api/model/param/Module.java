package com.pro.snowball.api.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("模块")
public class Module {
    @ApiModelProperty("编号")
    private String code;
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("java环境信息")
    private String javaEnv;
    @ApiModelProperty("切换node版本_并安装项目")
    private String nodeToInstall;
}
