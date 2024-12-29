package com.pro.snowball.common.service.cmd;

import java.util.List;

public interface ICmdLocalService {
    boolean execute(List<String> commands, String infoLogFile, String errorLogFile);
}
