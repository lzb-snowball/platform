package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.snowball.api.model.db.ExecuteTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumExecuteTemplate implements  IEnumToDbEnum<ExecuteTemplate> {

    _1("更新-jar", 100, true, null),
    _2("更新-ui", 100, true, null),
    _3("更新-jar-lib", 100, true, null);

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;

    private final String forceChangeTime;
}
