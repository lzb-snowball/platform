package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "我的模板分组")
@JTDTable(description = "")
public class MyExecuteGroup extends BaseModel {
    @ApiModelProperty(value = "参数名称")
    private String name;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
}
