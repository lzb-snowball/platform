package com.pro.snowball.api.model.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("服务器")
public class Server {
    @ApiModelProperty("编号")
    private String code;
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("地址")
    private String host;
    @ApiModelProperty("端口")
    private String port;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("私钥存储地址")
    private String privateKeyLocalPath;
    @ApiModelProperty("私钥存储访问密码")
    private String privateKeyPassword;
    @ApiModelProperty("部署根路径")
    private String deployBasePath;
}
