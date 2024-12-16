package com.pro.snowball.common.service;

import com.pro.common.module.api.usermoney.service.UserMoneyService;
import com.pro.snowball.api.model.db.UserMoneySnowball;
import com.pro.snowball.common.dao.UserMoneySnowballDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
//        (CommonConst.Bean.userMoneyService)
public class UserMoneySnowballService extends UserMoneyService<UserMoneySnowballDao, UserMoneySnowball> {
}
