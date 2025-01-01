package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.snowball.api.model.db.ExecuteStep;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行参数字典枚举
 */
@Getter
@AllArgsConstructor
public enum EnumExecuteStep implements  IEnumToDbEnum<ExecuteStep> {

    _1("拉取代码-git", null, 10010, true, null),
    _2("编译打包-maven", null, 10020, true, null),
    _3("连接服务器-重启-ssh-jar", null, 10040, true, null),
    _4("连接服务器-启动新进程-ssh-jar", null, 10050, true, null),
    _5("连接服务器-复制新软件包-ssh-jar", null, 10030, false, null),
    _6("压缩lib-复制lib-连接服务器-解压", null, 10060, true, null);

    @ApiModelProperty(value = "步骤名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "推荐排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;

    /**
     * 统一重置修改掉,这个配置时间以前的,旧的配置
     */
    private final String forceChangeTime;
}
