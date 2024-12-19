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
@ApiModel(description = "执行订单")
@JTDTable(entityId = 10012, module = "snowball")
public class ExecuteOrder extends BaseModel implements IUserRecordClass {
    @ApiModelProperty(value = "对应我的模板Id")
    @JTDField(entityClass = MyExecuteTemplate.class, entityClassKey = "id", entityClassTargetProp = "id") // 数据量太大了,暂时不做显性关联
    private Long myTemplateId;
    @ApiModelProperty(value = "最后一行_运行日志")
    @JTDField(mainLength = 2048)
    private String infoContent;
    @ApiModelProperty(value = "最后一行_错误日志")
    @JTDField(mainLength = 2048)
    private String errorContent;
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "总共几步")
    private Integer stepNoAll;
    @ApiModelProperty(value = "备注")
    private String remark;

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
