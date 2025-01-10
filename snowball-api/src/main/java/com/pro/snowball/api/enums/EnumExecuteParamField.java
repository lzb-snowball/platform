package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.constant.JTDConst;
import com.pro.snowball.api.model.db.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行参数字典枚举
 */
@Getter
@AllArgsConstructor
public enum EnumExecuteParamField implements  IEnumToDbEnum<ExecuteParamField> {

    _1("modules", "code", "编号", "", 100, true, true, null),
    _2("modules", "name", "名称", "", 100, true, true, null),
    _3("modules", "javaEnv", "java环境信息", "", 100, true, true, null),
    _4("servers", "name", "名称", "", 100, true, true, null),
    _5("servers", "host", "地址", "", 100, true, true, null),
    _6("servers", "port", "端口", "", 100, true, true, null),
    _7("servers", "username", "用户名", "", 100, true, true, null),
    _8("servers", "privateKeyLocalPath", "私钥存储地址", "", 100, true, true, null),
    _9("servers", "privateKeyPassword", "私钥存储访问密码", "", 100, true, true, null);

    @ApiModelProperty(value = "参数模型")
    @JTDField(entityClass = ExecuteParamModel.class)
    private final String modelCode;

    @ApiModelProperty(value = "编号")
    private final String code;

    @ApiModelProperty(value = "名称")
    private final String name;

    @ApiModelProperty(value = "备注")
    private final String remark;

    @ApiModelProperty(value = "排序")
    private final Integer sort;

    @ApiModelProperty(value = "开关")
    private final Boolean enabled;

    @ApiModelProperty(value = "系统固有")
    private final Boolean systemFlag;

    /**
     * 统一重置修改掉,这个配置时间以前的,旧的配置
     */
    private final String forceChangeTime;
}
