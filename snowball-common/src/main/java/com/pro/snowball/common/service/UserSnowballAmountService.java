package com.pro.snowball.common.service;

import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.UserSnowballAmount;
import com.pro.snowball.common.dao.UserSnowballAmountDao;
import org.springframework.stereotype.Service;

@Service
public class UserSnowballAmountService extends BaseService<UserSnowballAmountDao, UserSnowballAmount> {
}
