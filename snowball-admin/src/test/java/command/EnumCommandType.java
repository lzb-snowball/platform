package command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public enum EnumCommandType {
    //
    SSH_COMMAND("SSH连接远程服务器执行命令", CommandExecutorSshCommand::new),
    GIT_CLONE_PULL("Git执行克隆并拉取最新代码", CommandExecutorMavenClonePull::new),
    MAVEN_INSTALL("Maven执行安装", CommandExecutorSshMavenInstall::new),
    ;
    final String label;
    final Supplier<ICommandExecutor> iCommandExecutor;
}
