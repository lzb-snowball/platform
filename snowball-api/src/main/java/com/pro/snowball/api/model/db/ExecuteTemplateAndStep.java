package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.api.enums.IEnumToDbDbId;
import com.pro.framework.api.model.IdModel;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "模板配置和步骤配置的关联")
@JTDTable(entityId = 10005, module = "snowball")
public class ExecuteTemplateAndStep extends IdModel implements IConfigClass, IEnumToDbDbId {
    @ApiModelProperty(value = "模板配置")
    @JTDField(entityClass = ExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long templateId;
    @ApiModelProperty(value = "步骤配置")
    @JTDField(entityClass = ExecuteStep.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long stepId;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "推荐")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
    @ApiModelProperty(value = "创建时间")
    protected LocalDateTime createTime;

    @Override
    public LocalDateTime getUpdateTime() {
        return createTime;
    }

    @Override
    public void setUpdateTime(LocalDateTime updateTime) {
        createTime= updateTime;
    }
}
