package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteStep;
import com.pro.snowball.common.dao.ExecuteStepDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 执行步骤服务
 */
@Service
@Slf4j
public class ExecuteStepService extends BaseService<ExecuteStepDao, ExecuteStep> {
    public List<ExecuteStep> getActiveList(ExecuteStep executeStep) {
        return this.lambdaQuery().setEntity(executeStep)
                .orderByAsc(ExecuteStep::getSort)
                .eq(ExecuteStep::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
