package com.pro.snowball.api.model.db;

import com.pro.common.module.api.user.model.db.User;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.framework.javatodb.constant.JTDConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JTDTable(
        module = "user",
        entityId = 10001,
        sequences = {
                "UNIQUE KEY `uk_username` (`username`)",
                "UNIQUE KEY `idx_code` (`code`)",
        }
)
@ApiModel(description = "用户")
public class UserSnowball extends User {
}
