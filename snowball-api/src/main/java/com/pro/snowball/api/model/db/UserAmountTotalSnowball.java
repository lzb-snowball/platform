package com.pro.snowball.api.model.db;

import com.pro.common.module.api.usermoney.model.db.UserAmountTotal;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(description = "用户数额总计")
@JTDTable(
        module = "user",
        entityId = 10004,
        sequences = {"UNIQUE KEY `uk_userId` (`user_id`)",}
)
@Data
public class UserAmountTotalSnowball extends UserAmountTotal {

}
