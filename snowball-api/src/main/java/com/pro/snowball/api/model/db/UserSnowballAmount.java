package com.pro.snowball.api.model.db;

import com.pro.common.module.api.usermoney.model.modelbase.AmountEntity;
import com.pro.common.modules.api.dependencies.model.classes.IUserRecordClass;
import com.pro.framework.api.FrameworkConst;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JTDTable(
        module = "snowball",
        entityId = 10002,
        sequences = "UNIQUE KEY `uk_userId_productId` (`user_id`,`product_id`) USING BTREE"
)
@ApiModel(description = "用户项目卡包数量")
public class UserSnowballAmount extends AmountEntity implements IUserRecordClass {
    @ApiModelProperty("产品面值")
    @JTDField(entityClass = SnowballProduct.class, entityClassKey = "id", entityClassLabel = "price", sort = 1)
    private Long productId;

    @Override
    public String getEntityKey() {
        return productId + FrameworkConst.Str.split_pound + getUserId();
    }

    @Override
    public void setEntityKey(String key) {
        this.productId = Long.valueOf(key.split(FrameworkConst.Str.split_pound)[0]);
        setUserId(Long.valueOf(key.split(FrameworkConst.Str.split_pound)[1]));
    }
}
