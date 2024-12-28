package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.MyExecuteTemplate;
import com.pro.snowball.common.dao.MyExecuteTemplateDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 我的模板服务
 */
@Service
@Slf4j
public class MyExecuteTemplateService extends BaseService<MyExecuteTemplateDao, MyExecuteTemplate> {
    public List<MyExecuteTemplate> getActiveList(MyExecuteTemplate myExecuteTemplate) {
        return this.lambdaQuery().setEntity(myExecuteTemplate)
                .orderByAsc(MyExecuteTemplate::getSort)
                .eq(MyExecuteTemplate::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
