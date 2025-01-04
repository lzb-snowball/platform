package com.pro.snowball.common.service.cmd;

import com.pro.snowball.api.model.vo.RemoteServer;

import java.util.List;

public interface ICmdRemoteService extends ICmdService{
    boolean execute(RemoteServer remoteServer, List<String> commands, String infoLogFile, String errorLogFile, String orderKey);
}
