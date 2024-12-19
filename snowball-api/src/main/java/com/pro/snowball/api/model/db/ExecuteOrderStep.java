package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.common.modules.api.dependencies.model.classes.IUserRecordClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.framework.javatodb.constant.JTDConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "执行订单的步骤")
@JTDTable(entityId = 10011, module = "snowball")
public class ExecuteOrderStep extends BaseModel implements IUserRecordClass {
    @ApiModelProperty(value = "序号")
    private Integer no;
    @ApiModelProperty(value = "对应订单id")
//    @JTDField(entityClass = ExecuteOrder.class, entityClassKey = "id", entityClassTargetProp = "id") // 数据量太大了,暂时不做显性关联
    private Long orderId;
    @ApiModelProperty(value = "最后一行_运行日志")
    @JTDField(mainLength = 2048)
    private String infoContent;
    @ApiModelProperty(value = "最后一行_错误日志")
    @JTDField(mainLength = 2048)
    private String errorContent;
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "总共几行")
    private Integer commandNoAll;
    @ApiModelProperty(value = "备注")
    private String remark;


    @ApiModelProperty(value = "当前第几行")
    transient private Integer commandNoCurrent;

    @Override
    public Long getUserId() {
        return 0L;
    }

    @Override
    public void setUserId(Long userId) {

    }
}
