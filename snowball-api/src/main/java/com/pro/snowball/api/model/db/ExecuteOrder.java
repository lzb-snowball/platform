package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IUserRecordClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.snowball.api.enums.EnumExecuteOrderState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "执行订单")
@JTDTable(entityId = 10012, module = "snowball")
public class ExecuteOrder extends BaseModel implements IUserRecordClass {
    @ApiModelProperty(value = "我的模板Id")
    @JTDField(entityClass = MyExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id") // 数据量太大了,暂时不做显性关联
    private Long myTemplateId;
    @ApiModelProperty(value = "最后运行日志")
    @JTDField(mainLength = 2048)
    private String infoContent;
    @ApiModelProperty(value = "最后错误日志")
    @JTDField(mainLength = 2048)
    private String errorContent;
    @ApiModelProperty(value = "状态")
    private EnumExecuteOrderState state;
    @ApiModelProperty(value = "状态更变时间")
    private LocalDateTime stateTime;
    @ApiModelProperty(value = "总共几步")
    private Integer stepNoAll;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "管理员Id")
    @JTDField(entityName = "admin", entityClassKey = "id", entityClassTargetProp = "id", entityClassLabel = "username") // 数据量太大了,暂时不做显性关联
    private Long adminId;

    @ApiModelProperty(value = "当前第几步")
    transient private Integer stepNoCurrent;

    @Override
    public Long getUserId() {
        return 0L;
    }

    @Override
    public void setUserId(Long userId) {

    }
}
