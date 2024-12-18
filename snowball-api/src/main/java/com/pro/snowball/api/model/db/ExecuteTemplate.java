package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "执行模板的初始配置")
public class ExecuteTemplate extends BaseModel {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "推荐排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
}
