package com.pro.snowball.api.enums;

import com.pro.common.module.api.system.model.enums.IEnumAuthDict;
import com.pro.framework.api.enums.IEnumToDbEnum;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.constant.JTDConst;
import com.pro.snowball.api.model.db.ExecuteGroup;
import com.pro.snowball.api.model.db.ExecuteParam;
import com.pro.snowball.api.model.db.ExecuteParamModel;
import com.pro.snowball.api.model.db.MyExecuteTemplate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行参数字典枚举
 */
@Getter
@AllArgsConstructor
public enum EnumExecuteParam implements  IEnumToDbEnum<ExecuteParam> {

    _1("1", "", "platform", "snowball", null, null, 100, true, false, "snowball", null),
    _2("", "1", "repositories", "git@github.com:lzb-framework/framework.git\ngit@github.com:lzb-parent/parent.git\ngit@github.com:lzb-snowball/platform.git", null, null, 100, true, false, "git@github.com:lzb-framework/framework.git\ngit@github.com:lzb-parent/parent.git\ngit@github.com:lzb-snowball/platform.git", null),
    _4("", "3", "repositories", "git@github.com:lzb-framework/framework.git\ngit@github.com:lzb-parent/parent.git\ngit@github.com:lzb-snowball/platform.git", null, null, 100, true, false, "git@github.com:lzb-framework/framework.git\ngit@github.com:lzb-parent/parent.git\ngit@github.com:lzb-snowball/platform.git", null),
    _5("", "2", "repositories", "git@github.com:lzb-snowball/ui-admin.git", null, null, 100, true, false, "git@github.com:lzb-snowball/ui-admin.git", null),
    _6("", "2", "repositories_parent_ui", "git@github.com:lzb-parent/parent-ui.git", null, null, 100, true, false, "git@github.com:lzb-parent/parent-ui.git", null),
    _7("1", "", "modules", "[{\"code\":\"user\",\"name\":\"用户端\",\"javaEnv\":\"-Xms128m -Xmx1024m\"},{\"javaEnv\":\"-Xms128m -Xmx1024m\",\"code\":\"admin\",\"name\":\"管理端\"}]", "模块", "1", 100, true, true, "admin", null),
    _8("1", "", "servers", "[{\"name\":\"111.230.10.171服务器\",\"host\":\"111.230.10.171\",\"port\":\"22\",\"username\":\"root\",\"privateKeyLocalPath\":\"/Users/zubin/.ssh/id_rsa_github2\",\"code\":\"111.230.10.171\"}]", "服务器", "1", 100, true, true, "111.230.10.171", null),
    _11("1", "3", "modules", "[{\"code\":\"libs\",\"name\":\"lib包依赖\"}]", "模块", "1", 100, true, true, "libs", null);

    @ApiModelProperty(value = "我的我的分组")
    @JTDField(entityClass = ExecuteGroup.class, entityClassKey = "id", entityClassTargetProp = "id", notNull = JTDConst.EnumFieldNullType.can_null)
    private final String groupId;

    @ApiModelProperty(value = "我的模板")
    @JTDField(entityClass = MyExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id", notNull = JTDConst.EnumFieldNullType.can_null)
    private final String myTemplateId;

    @ApiModelProperty(value = "参数模型")
    @JTDField(entityClass = ExecuteParamModel.class, notEmpty = JTDConst.EnumFieldEmptyType.not_empty)
    private final String modelCode;

    @ApiModelProperty(value = "值")
    @JTDField(mainLength = 2048, notEmpty = JTDConst.EnumFieldEmptyType.not_empty)
    private final String value;

    @ApiModelProperty(value = "名称")
    private final String name;

    @ApiModelProperty(value = "备注")
    private final String remark;

    @ApiModelProperty(value = "排序")
    private final Integer sort;

    @ApiModelProperty(value = "开关")
    private final Boolean enabled;

    @ApiModelProperty(value = "执行前必须编辑")
    private final Boolean executeEdit;

    @ApiModelProperty(value = "默认值", notes = "取字符串 或者 值选项的编号")
    @JTDField(notNull = JTDConst.EnumFieldNullType.can_null, mainLength = 2048)
    private final String defaultValue;

    /**
     * 统一重置修改掉,这个配置时间以前的,旧的配置
     */
    private final String forceChangeTime;
}
