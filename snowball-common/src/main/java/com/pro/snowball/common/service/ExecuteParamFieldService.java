package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteParamField;
import com.pro.snowball.common.dao.ExecuteParamFieldDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数模型属性服务
 */
@Service
@Slf4j
public class ExecuteParamFieldService extends BaseService<ExecuteParamFieldDao, ExecuteParamField> {
    public List<ExecuteParamField> getActiveList(ExecuteParamField executeParamField) {
        return this.lambdaQuery().setEntity(executeParamField)
                .orderByAsc(ExecuteParamField::getSort)
                .eq(ExecuteParamField::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
