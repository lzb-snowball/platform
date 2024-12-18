package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.MyExecuteTemplateParam;
import com.pro.snowball.common.dao.MyExecuteTemplateParamDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 我的执行模板的参数服务
 */
@Service
@Slf4j
public class MyExecuteTemplateParamService extends BaseService<MyExecuteTemplateParamDao, MyExecuteTemplateParam> {
    public List<MyExecuteTemplateParam> getActiveList(MyExecuteTemplateParam myExecuteTemplateParam) {
        return this.lambdaQuery().setEntity(myExecuteTemplateParam)
                .orderByAsc(MyExecuteTemplateParam::getSort)
                .eq(MyExecuteTemplateParam::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
