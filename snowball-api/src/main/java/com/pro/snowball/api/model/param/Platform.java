package com.pro.snowball.api.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("平台")
public class Platform {
    @ApiModelProperty("编号")
    private String code;
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("部署根目录")
    private String deployRoot;
}
