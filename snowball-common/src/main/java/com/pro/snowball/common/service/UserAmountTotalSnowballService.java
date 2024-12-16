package com.pro.snowball.common.service;

import com.pro.common.module.api.usermoney.service.IDayClearService;
import com.pro.common.module.api.usermoney.service.UserAmountTotalService;
import com.pro.snowball.api.enums.EnumTradeTypeSnowball;
import com.pro.snowball.api.model.db.UserAmountTotalSnowball;
import com.pro.snowball.common.dao.UserAmountTotalSnowballDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Primary
@Service
public class UserAmountTotalSnowballService extends UserAmountTotalService<UserAmountTotalSnowballDao, UserAmountTotalSnowball> implements IDayClearService {
    @Override
    public void clearDay() {
        this.lambdaUpdate()
                .set(UserAmountTotalSnowball::getTodayCommissionMoney, BigDecimal.ZERO)
                .set(UserAmountTotalSnowball::getTodayTkMoney, BigDecimal.ZERO)
                .set(UserAmountTotalSnowball::getTodayTkTimes, BigDecimal.ZERO)
                .set(UserAmountTotalSnowball::getTodayRechargeMoney, BigDecimal.ZERO)
                .update();
    }

    @Override
    public UserAmountTotalSnowball newTotalZero(Long userId) {
        UserAmountTotalSnowball entity = super.newTotalZero(userId);
        return entity;
    }

    @Override
    public void addUserAmountTotal(UserAmountTotalSnowball total, String recordType, BigDecimal amount) {
        super.addUserAmountTotal(total, recordType, amount);
        this.addUserAmountTotalAdd(total, recordType, amount);
    }

    public void addUserAmountTotalAdd(UserAmountTotalSnowball total, String recordType, BigDecimal amount) {
        EnumTradeTypeSnowball tradeType = EnumTradeTypeSnowball.MAP.get(recordType);
        if (tradeType != null) {
        }
    }
}
