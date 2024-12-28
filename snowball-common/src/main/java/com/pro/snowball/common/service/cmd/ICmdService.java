package com.pro.snowball.common.service.cmd;

import java.util.List;

public interface ICmdService {
    public void execute(List<String> commands, String infoLogFile, String errorLogFile);
}
