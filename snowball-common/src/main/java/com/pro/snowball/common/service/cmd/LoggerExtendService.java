package com.pro.snowball.common.service.cmd;

import com.pro.common.modules.api.dependencies.message.ToSocket;
import com.pro.common.modules.service.dependencies.modelauth.base.MessageService;
import com.pro.snowball.api.SnowballConst;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoggerExtendService {
    MessageService messageService;

    public void receiveLine(String logKey, String line) {
        ToSocket toSocket = ToSocket.toAllUser("", line);
        toSocket.setTopic(logKey);
        messageService.sendToManager(toSocket);
    }
}
