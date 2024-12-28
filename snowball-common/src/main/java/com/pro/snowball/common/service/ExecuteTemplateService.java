package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteTemplate;
import com.pro.snowball.common.dao.ExecuteTemplateDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模板配置服务
 */
@Service
@Slf4j
public class ExecuteTemplateService extends BaseService<ExecuteTemplateDao, ExecuteTemplate> {
    public List<ExecuteTemplate> getActiveList(ExecuteTemplate executeTemplate) {
        return this.lambdaQuery().setEntity(executeTemplate)
                .orderByAsc(ExecuteTemplate::getSort)
                .eq(ExecuteTemplate::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
