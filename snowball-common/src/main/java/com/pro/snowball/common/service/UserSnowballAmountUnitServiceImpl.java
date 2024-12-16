package com.pro.snowball.common.service;

import com.pro.common.module.api.usermoney.service.AmountEntityUnitService;
import com.pro.snowball.api.model.db.UserSnowballAmount;
import com.pro.snowball.api.model.db.UserSnowballAmountRecord;
import com.pro.snowball.api.model.dto.UserSnowballAmountRecordDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数额变化的基础类
 */
@Service
@Getter
public class UserSnowballAmountUnitServiceImpl extends AmountEntityUnitService<UserSnowballAmount, UserSnowballAmountRecord, UserSnowballAmountRecordDTO> {
    @Autowired
    private UserSnowballAmountService amountEntityService;
    @Autowired
    private UserSnowballAmountRecordService amountEntityRecordService;
}
