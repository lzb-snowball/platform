package com.pro.snowball.common.service.cmd;

import com.pro.common.modules.api.dependencies.message.ISocketSender;
import com.pro.common.modules.api.dependencies.message.ToSocket;
import com.pro.common.modules.service.dependencies.modelauth.base.MessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LoggerExtendService {
//    MessageService messageService;
    @Autowired
    private ISocketSender socketSender;
    public void receiveLine(String orderKey, String line) {
        ToSocket toSocket = ToSocket.toAllUser("", line);
        toSocket.setTopic(orderKey);
//        messageService.sendToManager(toSocket);
        socketSender.send(toSocket);
        log.info(line);
    }
}
