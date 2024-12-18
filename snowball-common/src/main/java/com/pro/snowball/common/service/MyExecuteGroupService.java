package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.MyExecuteGroup;
import com.pro.snowball.common.dao.MyExecuteGroupDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 我的模板分组服务
 */
@Service
@Slf4j
public class MyExecuteGroupService extends BaseService<MyExecuteGroupDao, MyExecuteGroup> {
    public List<MyExecuteGroup> getActiveList(MyExecuteGroup myExecuteGroup) {
        return this.lambdaQuery().setEntity(myExecuteGroup)
                .eq(MyExecuteGroup::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
