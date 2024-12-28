package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteOrderStepCommand;
import com.pro.snowball.common.dao.ExecuteOrderStepCommandDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 执行订单的步骤命令行服务
 */
@Service
@Slf4j
public class ExecuteOrderStepCommandService extends BaseService<ExecuteOrderStepCommandDao, ExecuteOrderStepCommand> {
    public List<ExecuteOrderStepCommand> getActiveList(ExecuteOrderStepCommand executeOrderStepCommand) {
        return this.lambdaQuery().setEntity(executeOrderStepCommand)
                .list();
    }
}
