package com.pro.snowball.common.service.cmd;

import com.pro.common.modules.api.dependencies.message.ToSocket;
import com.pro.common.modules.service.dependencies.modelauth.base.MessageService;
import com.pro.snowball.api.SnowballConst;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LoggerExtendService {
    MessageService messageService;

    public void receiveLine(String logKey, String line) {
        if (line.contains("Building snowball-admin 2.0.0")) {
            log.warn("===line 2{}", line);
        }
        ToSocket toSocket = ToSocket.toAllUser("", line);
        toSocket.setTopic(logKey);
        messageService.sendToManager(toSocket);
        log.info(line);
    }
}
