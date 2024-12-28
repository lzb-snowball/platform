package com.pro.snowball.common.service;

import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteGroup;
import com.pro.snowball.common.dao.ExecuteGroupDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 我的分组服务
 */
@Service
@Slf4j
public class ExecuteGroupService extends BaseService<ExecuteGroupDao, ExecuteGroup> {
    public List<ExecuteGroup> getActiveList(ExecuteGroup executeGroup) {
        return this.lambdaQuery().setEntity(executeGroup)
                .orderByAsc(ExecuteGroup::getSort)
                .eq(ExecuteGroup::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
