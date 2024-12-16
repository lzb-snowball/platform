package com.pro.snowball.common.service;

import com.pro.common.module.api.user.service.UserService;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.snowball.api.model.db.UserSnowball;
import com.pro.snowball.common.dao.UserSnowballDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service(CommonConst.Bean.userService)
public class UserSnowballService extends UserService<UserSnowballDao, UserSnowball> {
    @Override
    public UserSnowball newInstant() {
        return new UserSnowball();
    }
}
