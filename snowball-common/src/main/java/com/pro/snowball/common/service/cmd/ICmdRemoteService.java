package com.pro.snowball.common.service.cmd;

import com.pro.snowball.api.model.db.RemoteServer;

import java.util.List;

public interface ICmdRemoteService {
    boolean execute(RemoteServer remoteServer, List<String> commands, String infoLogFile, String errorLogFile, String logKey);
}
