package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.snowball.api.model.db.ExecuteParamModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumExecuteParamModel implements  IEnumToDbEnum<ExecuteParamModel> {

    _1("modules", "模块", "", 100, true, true, true, false, null),
    _2("servers", "服务器", "", 100, true, true, true, false, null),
    _3("repositories", "仓库地址", "", 100, true, false, true, false, null),
    _4("platform", "平台", "", 100, true, false, false, false, null),
    _5("repositories_parent_ui", "parent-ui仓库地址", "", 100, true, false, false, false, null);

    @ApiModelProperty(value = "取值编号")
    private String code;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
    @ApiModelProperty(value = "是否多属性")
    private Boolean multipleField;
    @ApiModelProperty(value = "是否多值")
    private Boolean multipleValue;
    @ApiModelProperty(value = "系统固有")
    private Boolean systemFlag;

    private final String forceChangeTime;
}
