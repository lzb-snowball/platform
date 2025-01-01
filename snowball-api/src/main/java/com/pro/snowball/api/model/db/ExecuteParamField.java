package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.api.enums.IEnumToDbDbId;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "参数模型属性")
@JTDTable(entityId = 10015, module = "snowball", sequences = {
        "UNIQUE KEY `uk_modelCode_code` (`model_code`,`code`)"
})
public class ExecuteParamField extends BaseModel implements IConfigClass, IEnumToDbDbId {
    @ApiModelProperty(value = "参数模型")
    @JTDField(entityClass = ExecuteParamModel.class)
    private String modelCode;
    @ApiModelProperty(value = "编号")
    private String code;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
    @ApiModelProperty(value = "系统固有")
    private Boolean systemFlag;
}
