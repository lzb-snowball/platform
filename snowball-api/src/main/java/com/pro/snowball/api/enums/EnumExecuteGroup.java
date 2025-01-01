package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.snowball.api.model.db.ExecuteGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumExecuteGroup implements IEnumToDbEnum<ExecuteGroup> {

    _1("滚雪球分组1", 100, true, null),
    _2("滚雪球分组2", 100, true, null);

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;

    private final String forceChangeTime;
}
