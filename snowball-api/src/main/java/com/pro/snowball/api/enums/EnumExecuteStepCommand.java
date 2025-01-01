package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.constant.JTDConst;
import com.pro.snowball.api.model.db.ExecuteStep;
import com.pro.snowball.api.model.db.ExecuteStepCommand;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumExecuteStepCommand implements  IEnumToDbEnum<ExecuteStepCommand> {

    _1("拉取代码-git", 1L, "# 拉取代码\ncd @/\n<#list repositories as repository>\n    rm -rf ${getGitName(repository)}\n    git clone ${repository}\n</#list>\n", "", 100, true, null),
    _2("编译打包-maven", 2L, "cd @/parent\nmvn clean install -DskipTests=true\n", "", 100, true, null),
    _3("连接服务器-复制新软件包ssh-jar", 5L, "<#list servers as server>\n    <#list modules as module>\n        <#if module.code != \"libs\">\n            scp -i ${server.privateKeyLocalPath} @/platform/${platform}-${module.code}/target/${platform}-${module.code}.jar ${server.username}@${server.host}:/project/${platform}/new/${platform}-${module.code}.jar\n        </#if>\n    </#list>\n</#list>", "", 100, false, null),
    _4("连接服务器-重启-ssh-jar", 3L, "<#list servers as server>\n  <#list modules as module>\n    <#if module.code != \"libs\">\n      # 拷贝main-jar包\n      scp -i ${server.privateKeyLocalPath} @/platform/${platform}-${module.code}/target/${platform}-${module.code}.jar ${server.username}@${server.host}:/project/${platform}/new/${platform}-${module.code}.jar\n\n      <remote server=\"${json(server)}\">\n        # 结束旧进程\n        pid=$(ps -ef | grep \"/project/${platform}/${platform}-${module.code}.jar\" | grep -v grep | awk '{print $2}')\n        if [ -n \"$pid\" ]; then\n        kill -9 $pid\n        echo \"Previous process stopped.\"\n        fi\n\n        # 备份旧的 JAR 文件和日志文件\n        BACKUP_DIR=\"/project/${platform}/backup/$(date +%Y%m%d%H%M%S)\"\n        mkdir -p \"$BACKUP_DIR\"\n        cp /project/${platform}/${platform}-${module.code}.jar \"$BACKUP_DIR/${platform}-${module.code}.jar\"\n        cp /project/${platform}/${module.code}.log \"$BACKUP_DIR/${module.code}.log\"\n        echo \"Backup completed to $BACKUP_DIR.\"\n\n        # 启动新进程\n        cp -f /project/${platform}/new/${platform}-${module.code}.jar /project/${platform}/${platform}-${module.code}.jar\n        export LC_ALL=en_US.UTF-8\n        cd /project/${platform}/\n        nohup java ${module.javaEnv} -Dloader.path=/project/${platform}/lib -jar -Dfile.encoding=UTF-8 -Dspring.profiles.active=prod,platform /project/${platform}/${platform}-${module.code}.jar > /project/${platform}/${module.code}.log 2>&1 & disown\n        sleep 3\n\n        # 检查启动成功\n        tail -f /project/${platform}/${module.code}.log | while read line; do\n        echo \"$line\"\n        if [[ \"$line\" == *Started* ]]; then\n        echo \"Application started successfully!\"\n        break\n        fi\n        done\n      </remote>\n    </#if>\n  </#list>\n</#list>", "", 100, true, null),
    _5("压缩lib-复制lib", 6L, "<#list servers as server>\n    <#list modules as module>\n        <#if module.code == \"libs\">\n            cd @/platform/${platform}-admin/target/lib\n            tar --no-xattr -czf ../libs.tar.gz *.jar\n        </#if>\n    </#list>\n</#list>", "", 100, true, null),
    _6("连接服务器-解压", 6L, "<#list servers as server>\n    <#list modules as module>\n        <#if module.code == \"libs\">\n            scp -i ${server.privateKeyLocalPath} @/platform/${platform}-admin/target/libs.tar.gz ${server.username}@${server.host}:/project/${platform}/\n            <remote server=\"${json(server)}\">\n                cd /project/${platform}/\n                tar -xzf libs.tar.gz -C lib\n            </remote>\n        </#if>\n    </#list>\n</#list>", "", 100, true, null),
    _8("拉取ui代码-git-yarnInstall", 9L, "# 拉取ui代码 并install\n<#list repositories as repository>\n  <#list servers as server>\n    <#list modules as module>\n        <#if ('ui-'+module.code) == getGitName(repository)>\n            cd @/\n            rm -rf ${getGitName(repository)}\n            git clone ${repository}\n            cd ${getGitName(repository)}\n            cd src\n            <#list repositories_parent_ui as repository_parent_ui>\n                rm -rf ${getGitName(repository_parent_ui)}\n                git clone ${repository_parent_ui}\n            </#list>\n            cd ..\n            yarn install --silent\n            npm run 'build prod'\n        </#if>\n    </#list>\n  </#list>\n</#list>\n", "", 100, true, null),
    _9("部署ui代码", 11L, "#压缩 复制到服务器 备份 解压(完成部署)\n<#list servers as server>\n  <#list modules as module>\n  cd @/ui-${module.code}\n  tar --no-xattr -czf ui-${module.code}.tar.gz ./dist\n  scp -i ${server.privateKeyLocalPath} @/ui-${module.code}/ui-${module.code}.tar.gz ${server.username}@${server.host}:/project/${platform}/new/\n  <remote server=\"${json(server)}\">\n      BACKUP_DIR=\"/project/${platform}/backup/$(date +%Y%m%d%H%M%S)\"\n      mkdir -p \"$BACKUP_DIR\"\n      cp /project/${platform}/new/ui-${module.code}.tar.gz \"$BACKUP_DIR/\"\n      echo \"Backup completed to $BACKUP_DIR.\"\n\n      cd /project/${platform}/\n      tar -xzf ui-${module.code}.tar.gz -C /project/${platform}/ui${module.code}\n  </remote>\n  </#list>\n</#list>", "", 100, true, null);


    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "步骤配置")
    @JTDField(entityClass = ExecuteStep.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long stepId;
    @ApiModelProperty(value = "内容")
    @JTDField(type = JTDConst.EnumFieldType.text, uiType = JTDConst.EnumFieldUiType.xml)
    private String content;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
    private final String forceChangeTime;
}
