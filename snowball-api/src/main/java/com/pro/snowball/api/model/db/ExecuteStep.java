package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.api.enums.IEnumToDbDbId;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "步骤配置")
@JTDTable(entityId = 10009, module = "snowball")
public class ExecuteStep extends BaseModel implements IConfigClass, IEnumToDbDbId {
    @ApiModelProperty(value = "步骤名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "推荐排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
    @ApiModelProperty(value = "排序")
    transient private Integer no;
}
