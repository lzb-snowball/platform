package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.framework.api.model.IdModel;
import com.pro.framework.javatodb.annotation.JTDField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "执行模板和步骤的关联")
public class ExecuteTemplateAndStep extends IdModel {
    @ApiModelProperty(value = "模板")
    @JTDField(entityClass = ExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long templateId;
    @ApiModelProperty(value = "步骤")
    @JTDField(entityClass = ExecuteStep.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long stepId;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "步骤")
    private Integer step;
    @ApiModelProperty(value = "创建时间")
    protected LocalDateTime createTime;
}