package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteStepCommand;
import com.pro.snowball.common.dao.ExecuteStepCommandDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 执行步骤命令行服务
 */
@Service
@Slf4j
public class ExecuteStepCommandService extends BaseService<ExecuteStepCommandDao, ExecuteStepCommand> {
    public List<ExecuteStepCommand> getActiveList(ExecuteStepCommand executeStepCommand) {
        return this.lambdaQuery().setEntity(executeStepCommand)
                .orderByAsc(ExecuteStepCommand::getSort)
                .eq(ExecuteStepCommand::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
