package com.pro.snowball.common.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.RemoteServer;
import com.pro.snowball.common.dao.RemoteServerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 远程服务器服务
 */
@Service
@Slf4j
public class RemoteServerService extends BaseService<RemoteServerDao, RemoteServer> {
    public List<RemoteServer> getActiveList(RemoteServer remoteServer) {
        return this.lambdaQuery().setEntity(remoteServer)
                .orderByAsc(RemoteServer::getSort)
                .eq(RemoteServer::getEnabled, CommonConst.Num.YES)
                .list();
    }
}
