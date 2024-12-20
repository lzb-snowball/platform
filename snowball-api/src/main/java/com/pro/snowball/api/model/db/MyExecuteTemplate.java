package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.framework.javatodb.constant.JTDConst;
import com.pro.snowball.api.enums.EnumExecuteOrderState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "我的模板")
@JTDTable(entityId = 10003, module = "snowball")
public class MyExecuteTemplate extends BaseModel implements IConfigClass {
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "分组")
    @JTDField(entityClass = MyExecuteGroup.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long groupId;
    @ApiModelProperty(value = "模板配置")
    @JTDField(entityClass = ExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long templateId;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
    @ApiModelProperty(value = "最后订单")
    @JTDField(defaultValue = "NULL", notNull = JTDConst.EnumFieldNullType.can_null)
    private Long lastOrderId;
    @ApiModelProperty(value = "最后订单状态")
    @JTDField(defaultValue = "NULL", notNull = JTDConst.EnumFieldNullType.can_null)
    private EnumExecuteOrderState lastOrderState;
    @ApiModelProperty(value = "最后订单状态时间")
    private LocalDateTime lastOrderStateTime;
}
