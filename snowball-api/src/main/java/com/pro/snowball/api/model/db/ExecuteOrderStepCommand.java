package com.pro.snowball.api.model.db;

import com.pro.common.modules.api.dependencies.model.BaseModel;
import com.pro.common.modules.api.dependencies.model.classes.IConfigClass;
import com.pro.common.modules.api.dependencies.model.classes.IUserRecordClass;
import com.pro.framework.javatodb.annotation.JTDField;
import com.pro.framework.javatodb.annotation.JTDTable;
import com.pro.framework.javatodb.constant.JTDConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(description = "执行订单的步骤命令行")
@JTDTable(entityId = 10010, module = "snowball")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteOrderStepCommand extends BaseModel implements IUserRecordClass {
    @ApiModelProperty(value = "命令序号")
    private Integer no;
    @ApiModelProperty(value = "订单id")
    //    @JTDField(entityClass = ExecuteOrder.class, entityClassKey = "id", entityClassTargetProp = "id") // 数据量太大了,暂时不做显性关联
    private Long orderId;
    @ApiModelProperty(value = "步骤id")
    @JTDField(entityClass = ExecuteStep.class, entityClassKey = "id", entityClassTargetProp = "id")
    private Long stepId;
    @ApiModelProperty(value = "步骤名称")
//    @JTDField(entityClass = ExecuteStep.class, entityClassKey = "id", entityClassTargetProp = "id", entityClassLabel = "name")
    private String stepName;
    @ApiModelProperty(value = "步骤排序")
    private Integer stepSort;
    @ApiModelProperty(value = "步骤序号1到n")
    private Integer stepNo;
    @ApiModelProperty(value = "解析前执行内容")
    @JTDField(type = JTDConst.EnumFieldType.text, uiType = JTDConst.EnumFieldUiType.xml)
    private String content;
    @ApiModelProperty(value = "需要参数")
    @JTDField(type = JTDConst.EnumFieldType.text, uiType = JTDConst.EnumFieldUiType.json)
    private String contentParamRequired;
    @ApiModelProperty(value = "解析参数")
    @JTDField(type = JTDConst.EnumFieldType.text, uiType = JTDConst.EnumFieldUiType.json)
    private String contentParamJson;
    @ApiModelProperty(value = "解析后执行内容")
    @JTDField(type = JTDConst.EnumFieldType.text, uiType = JTDConst.EnumFieldUiType.textarea)
    private String contentAfter;
    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "备注")
    private String remark;
//    @ApiModelProperty(value = "在远程服务器执行", notes = "注:scp一般在本地执行-虽然传输到远程服务器")
//    private Boolean remoteServerFlag;

    @ApiModelProperty(value = "需要参数")
    transient List<String> contentParamRequireds;

    @ApiModelProperty(value = "需要参数未配置的")
    transient private List<String> contentParamRequiredsLack;
    @ApiModelProperty(value = "最终参数")
    transient private Map<String, Object> inputParamMapResult;


    @Override
    public Long getUserId() {
        return 0L;
    }

    @Override
    public void setUserId(Long userId) {

    }
}
