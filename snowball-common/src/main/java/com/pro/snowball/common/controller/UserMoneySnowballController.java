package com.pro.snowball.common.controller;

import com.pro.common.module.api.usermoney.controller.UserMoneyController;
import com.pro.common.module.api.usermoney.model.db.UserMoneyRecord;
import com.pro.common.module.api.usermoney.model.dto.UserMoneyChangeDTO;
import com.pro.common.module.api.usermoney.service.UserMoneyRecordService;
import com.pro.snowball.api.model.db.UserMoneySnowball;
import com.pro.snowball.common.service.UserMoneySnowballService;
import com.pro.snowball.common.service.UserMoneySnowballUnitService;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户余额")
@RestController
@RequestMapping("/userMoney")
@Getter
public class UserMoneySnowballController extends UserMoneyController<UserMoneySnowball, UserMoneyRecord, UserMoneyChangeDTO, UserMoneySnowballUnitService> {
    @Autowired
    private UserMoneySnowballUnitService amountEntityUnitService;
    @Autowired
    private UserMoneySnowballService amountEntityService;
    @Autowired
    private UserMoneyRecordService amountEntityRecordService;
}
