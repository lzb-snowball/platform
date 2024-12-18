package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.Demo;
import com.pro.snowball.common.dao.DemoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ${label}服务
 */
@Service
@Slf4j
public class DemoService extends BaseService<DemoDao, Demo> {
    public List<Demo> getActiveList(Demo demo) {
        return this.lambdaQuery().setEntity(demo)
                /*GG  #foreach($field in ${fields})             GG*/
                /*GG    #if(${field.columnName}=='enabled')     GG*/
                .eq(Demo::getEnabled, CommonConst.Num.YES)
                /*GG    #elseif(${field.columnName}=='sort')    GG*/
                .orderByAsc(Demo::getSort)
                /*GG    #end                                    GG*/
                /*GG  #end                                      GG*/
                .list();
    }
}
