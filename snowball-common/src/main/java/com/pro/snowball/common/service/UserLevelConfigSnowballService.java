package com.pro.snowball.common.service;

import com.pro.common.module.api.userlevel.model.intf.IUserLevelConfigService;
import com.pro.common.module.service.userlevel.service.UserLevelConfigService;
import com.pro.snowball.api.model.db.UserLevelConfigSnowball;
import com.pro.snowball.common.dao.UserLevelConfigSnowballDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class UserLevelConfigSnowballService extends UserLevelConfigService<UserLevelConfigSnowballDao, UserLevelConfigSnowball> implements IUserLevelConfigService<UserLevelConfigSnowball> {
}
