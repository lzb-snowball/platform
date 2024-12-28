package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteTemplateAndStep;
import com.pro.snowball.common.dao.ExecuteTemplateAndStepDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板配置和步骤配置的关联服务
 */
@Service
@Slf4j
public class ExecuteTemplateAndStepService extends BaseService<ExecuteTemplateAndStepDao, ExecuteTemplateAndStep> {
    public List<ExecuteTemplateAndStep> getActiveList(ExecuteTemplateAndStep executeTemplateAndStep) {
        return this.lambdaQuery().setEntity(executeTemplateAndStep)
                .orderByAsc(ExecuteTemplateAndStep::getSort)
                .eq(ExecuteTemplateAndStep::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
