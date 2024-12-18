package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteOrder;
import com.pro.snowball.common.dao.ExecuteOrderDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 执行订单服务
 */
@Service
@Slf4j
public class ExecuteOrderService extends BaseService<ExecuteOrderDao, ExecuteOrder> {
    public List<ExecuteOrder> getActiveList(ExecuteOrder executeOrder) {
        return this.lambdaQuery().setEntity(executeOrder)
                .list();
    }
}
