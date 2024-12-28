package com.pro.snowball.common.service;

import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteStepCommand;
import com.pro.snowball.common.dao.ExecuteStepCommandDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 步骤配置_命令行服务
 */
@Service
@Slf4j
public class ExecuteStepCommandService extends BaseService<ExecuteStepCommandDao, ExecuteStepCommand> {
    public List<ExecuteStepCommand> getActiveList(ExecuteStepCommand executeStepCommand, List<Long> stepIds) {
        return this.lambdaQuery().setEntity(executeStepCommand)
                .orderByAsc(ExecuteStepCommand::getSort)
                .eq(ExecuteStepCommand::getEnabled, CommonConst.Num.YES)
                .in(ExecuteStepCommand::getStepId, stepIds)
                .last(stepIds.isEmpty(), "limit 0")
                .list();
    }
}
