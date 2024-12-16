package com.pro.snowball.api.model.db;

import lombok.Data;

/**
 * 远程服务器
 */
@Data
public class RemoteServer {
    /**
     * 别名
     */
    private String name;

    /**
     * ssh地址
     */
    private String host;
    /**
     * ssh端口
     */
    private Integer port = 22;
    /**
     * 用户名
     */
    private String username;
    /**
     * 私钥本地地址
     */
    private String privateKeyLocalPath;
}
