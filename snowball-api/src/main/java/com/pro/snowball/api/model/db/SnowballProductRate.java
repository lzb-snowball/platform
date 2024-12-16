package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.classes.BaseConfigModel;
import com.pro.framework.api.enums.IEnumToDbDbId;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JTDTable(
        module = "snowball",
        entityId = 10006
)
@ApiModel(description = "刮刮乐产品概率")
public class SnowballProductRate extends BaseConfigModel implements IEnumToDbDbId {
    @ApiModelProperty("刮刮乐产品")
    @JTDField(entityClass = SnowballProduct.class, entityClassKey = "id", entityClassLabel = "price")
    private Long productId;
    @ApiModelProperty("百分比概率")
    private String rate;

    @ApiModelProperty("百分比概率")
    private transient BigDecimal rateNum;
}
