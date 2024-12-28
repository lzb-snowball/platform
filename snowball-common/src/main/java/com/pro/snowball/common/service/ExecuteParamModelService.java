package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteParamModel;
import com.pro.snowball.common.dao.ExecuteParamModelDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 参数模型服务
 */
@Service
@Slf4j
public class ExecuteParamModelService extends BaseService<ExecuteParamModelDao, ExecuteParamModel> {
    public List<ExecuteParamModel> getActiveList(ExecuteParamModel executeParamModel) {
        return this.lambdaQuery().setEntity(executeParamModel)
                .orderByAsc(ExecuteParamModel::getSort)
                .eq(ExecuteParamModel::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
