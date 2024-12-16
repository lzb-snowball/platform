package com.pro.snowball.api.model.db;

import com.pro.common.module.api.usermoney.model.modelbase.AmountEntityRecord;
import com.pro.common.modules.api.dependencies.model.classes.IUserRecordClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JTDTable(module = "snowball",
        entityId = 10009
)
@ApiModel(description = "用户项目数量变动")
@Data
public class UserSnowballAmountRecord extends AmountEntityRecord implements IUserRecordClass {
    @ApiModelProperty("产品面值")
    @JTDField(entityClass = SnowballProduct.class, entityClassKey = "id", entityClassLabel = "price", sort = 1)
    private Long productId;
}
