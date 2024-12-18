package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.snowball.api.enums.EnumExecuteStepParamInputType;
import com.pro.snowball.api.enums.EnumExecuteStepParamType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "执行步骤需要的参数")
@JTDTable(entityId = 10007, module = "snowball")
public class ExecuteStepParamRequired extends BaseModel implements IConfigClass {
    @ApiModelProperty(value = "参数编号")
    private String code;
    @ApiModelProperty(value = "参数名称")
    private String name;
    @ApiModelProperty(value = "默认值")
    @JTDField(mainLength = 2048)
    private String defaultValue;
    @ApiModelProperty(value = "值的类型")
    private EnumExecuteStepParamType type;
    @ApiModelProperty(value = "单值或列表")
    @JTDField(defaultValue = "1")
    private Boolean isList;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
}
