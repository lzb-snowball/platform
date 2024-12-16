package com.pro.snowball.api.model.db;

import com.pro.common.module.api.usermoney.model.db.UserMoney;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "用户余额")
@JTDTable(
        entityId = 10010,
        module = "user",
        sequences = {"UNIQUE KEY `uk_userId_type` (`user_id`,`amount_type`)",})
@Data
public class UserMoneySnowball extends UserMoney {
}
