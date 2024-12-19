package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "我的分组")
@JTDTable(entityId = 10004, module = "snowball")
public class MyExecuteGroup extends BaseModel implements IConfigClass {
    @ApiModelProperty(value = "参数名称")
    private String name;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
}
