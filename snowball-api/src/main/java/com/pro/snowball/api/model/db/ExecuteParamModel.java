package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "参数模型")
@JTDTable(entityId = 10014, module = "snowball", sequences = {
        "UNIQUE KEY `uk_code` (`code`)"
})
public class ExecuteParamModel extends BaseModel implements IConfigClass {
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
    private Boolean system;
}
