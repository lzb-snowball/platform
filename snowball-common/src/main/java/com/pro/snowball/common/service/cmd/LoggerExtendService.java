package com.pro.snowball.common.service.cmd;

import com.pro.common.modules.api.dependencies.message.ToSocket;
import com.pro.common.modules.service.dependencies.modelauth.base.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LoggerExtendService {
    MessageService messageService;

    public void receiveLine(String orderKey, String line) {
        ToSocket toSocket = ToSocket.toAllUser("", line);
        toSocket.setTopic(orderKey);
        messageService.sendToManager(toSocket);
        log.info(line);
    }
}
