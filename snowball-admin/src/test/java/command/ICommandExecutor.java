package command;

import com.pro.snowball.api.model.vo.RemoteServer;

import java.util.Map;

public interface ICommandExecutor {
    /**
     * 访问远程服务器
     *
     * @param remoteServer 服务器信息
     * @param command      ssh命令
     * @param params       暂时无用
     * @return 访问结果
     */
    BaseCommandResult execute(RemoteServer remoteServer, String command, Map<String, Object> params);
}
