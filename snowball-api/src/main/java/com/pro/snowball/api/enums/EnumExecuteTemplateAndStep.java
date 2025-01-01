package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.snowball.api.model.db.ExecuteStep;
import com.pro.snowball.api.model.db.ExecuteTemplate;
import com.pro.snowball.api.model.db.ExecuteTemplateAndStep;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public enum EnumExecuteTemplateAndStep implements  IEnumToDbEnum<ExecuteTemplateAndStep> {
    _1(      1L,   1L,  "",  10010,   true,    null ),
    _2(      1L,   2L,  "",  10020,   true,    null ),
    _3(      1L,   3L,  "",  10040,   true,    null ),
    _4(      3L,   1L,  "",  10010,   true,    null ),
    _5(      3L,   2L,  "",  10020,   true,    null ),
    _6(      3L,   6L,  "",  10020,   true,    null );

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

    private final String forceChangeTime;
}
