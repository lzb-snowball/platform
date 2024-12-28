package com.pro.snowball.api.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "模块")
@JTDTable(entityId = 10013, module = "snowball")
public class Module extends BaseModel implements IConfigClass {
    @ApiModelProperty(value = "我的模板", notes = "多选 不选默认是全局模块")
    @JTDField(entityClass = MyExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id", javaTypeEnumClassMultiple = true) // 数据量太大了,暂时不做显性关联
    private String myTemplateIds;
    @ApiModelProperty(value = "远程服务器Id", notes = "多选 不选默认是全局模块")
    @JTDField(entityClass = RemoteServer.class, entityClassKey = "id", entityClassTargetProp = "id", javaTypeEnumClassMultiple = true)
    private String remoteServerIds;
    @ApiModelProperty(value = "编号")
    private String code;
    @ApiModelProperty(value = "别名")
    private String name;
    @ApiModelProperty(value = "java环境信息", notes = "例如 -Xms128m -Xmx1024m")
    private String javaEnv;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "开关")
    private Boolean enabled;
}
