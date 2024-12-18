package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.framework.javatodb.annotation.JTDField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "我的执行模板")
public class MyExecuteTemplate extends BaseModel {
    @ApiModelProperty(value = "名称")
    @JTDField(entityClass = MyExecuteGroup.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long groupId;
    @ApiModelProperty(value = "名称")
    @JTDField(entityClass = ExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long templateId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
}
