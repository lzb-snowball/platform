package com.pro.snowball.common.controller;

import com.pro.common.module.api.usermoney.controller.AmountEntityBaseController;
import com.pro.snowball.api.model.db.UserSnowballAmount;
import com.pro.snowball.api.model.db.UserSnowballAmountRecord;
import com.pro.snowball.api.model.dto.UserSnowballAmountRecordDTO;
import com.pro.snowball.common.service.UserSnowballAmountRecordService;
import com.pro.snowball.common.service.UserSnowballAmountService;
import com.pro.snowball.common.service.UserSnowballAmountUnitServiceImpl;
import io.swagger.annotations.Api;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "数额变化")
@RestController
@RequestMapping("/userSnowballAmount")
@Getter
public class UserSnowballAmountController extends AmountEntityBaseController<UserSnowballAmount, UserSnowballAmountRecord, UserSnowballAmountRecordDTO, UserSnowballAmountUnitServiceImpl> {
    @Autowired
    private UserSnowballAmountUnitServiceImpl amountEntityUnitService;
    @Autowired
    private UserSnowballAmountService amountEntityService;
    @Autowired
    private UserSnowballAmountRecordService amountEntityRecordService;

}
