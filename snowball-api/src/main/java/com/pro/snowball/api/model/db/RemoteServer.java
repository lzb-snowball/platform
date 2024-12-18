package com.pro.snowball.api.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "远程服务器")
@JTDTable(entityId = 10001, module = "snowball")
public class RemoteServer extends BaseModel {
    @ApiModelProperty(value = "别名")
    private String name;
    @ApiModelProperty(value = "地址", notes = "例如 11.11.11.11")
    private String host;
    @ApiModelProperty(value = "端口")
    @JTDField(defaultValue = "22")
    private Integer port;
    @ApiModelProperty(value = "登录名")
    @JTDField(defaultValue = "root")
    private String username;
    // todo 安全性代办 1.私钥最好加解密存储,不要直接存储  2.设置私钥密码 (也是加解密存储)
    @ApiModelProperty(value = "私钥存储地址", notes = "例如 /Users/zubin/.ssh/id_rsa")
    private String privateKeyLocalPath;
    @ApiModelProperty(value = "私钥存储访问密码", notes = "例如 aaa123123")
    @JsonIgnore
    private String privateKeyPassword;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;

}
