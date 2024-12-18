package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteStepParamRequired;
import com.pro.snowball.common.dao.ExecuteStepParamRequiredDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 执行步骤需要的参数服务
 */
@Service
@Slf4j
public class ExecuteStepParamRequiredService extends BaseService<ExecuteStepParamRequiredDao, ExecuteStepParamRequired> {
    public List<ExecuteStepParamRequired> getActiveList(ExecuteStepParamRequired executeStepParamRequired) {
        return this.lambdaQuery().setEntity(executeStepParamRequired)
                .orderByAsc(ExecuteStepParamRequired::getSort)
                .eq(ExecuteStepParamRequired::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
