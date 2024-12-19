package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.Module;
import com.pro.snowball.common.dao.ModuleDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模块服务
 */
@Service
@Slf4j
public class ModuleService extends BaseService<ModuleDao, Module> {
    public List<Module> getActiveList(Module module) {
        return this.lambdaQuery().setEntity(module)
                .orderByAsc(Module::getSort)
                .eq(Module::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
