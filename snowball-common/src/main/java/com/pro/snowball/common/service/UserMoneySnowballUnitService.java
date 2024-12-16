package com.pro.snowball.common.service;

import com.pro.common.module.api.usermoney.model.db.UserMoneyRecord;
import com.pro.common.module.api.usermoney.model.dto.UserMoneyChangeDTO;
import com.pro.common.module.api.usermoney.service.UserMoneyRecordService;
import com.pro.common.module.api.usermoney.service.UserMoneyUnitService;
import com.pro.snowball.api.model.db.UserMoneySnowball;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
@Getter
public class UserMoneySnowballUnitService extends UserMoneyUnitService<UserMoneySnowball, UserMoneyRecord, UserMoneyChangeDTO> {
    @Autowired
    private UserSnowballService userService;
    @Autowired
    @Lazy
    private UserMoneySnowballService amountEntityService;
    @Autowired
    private UserMoneyRecordService amountEntityRecordService;
}
