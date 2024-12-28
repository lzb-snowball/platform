package com.pro.snowball.common.service;

import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.api.util.CollUtils;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteParam;
import com.pro.snowball.api.model.db.MyExecuteTemplate;
import com.pro.snowball.common.dao.ExecuteParamDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 参数值服务
 */
@Service
@Slf4j
public class ExecuteParamService extends BaseService<ExecuteParamDao, ExecuteParam> {
    public List<ExecuteParam> getActiveList(ExecuteParam executeParam, Set<String> modelCodes) {
        return this.lambdaQuery().setEntity(executeParam)
                .orderByAsc(ExecuteParam::getSort)
                .in(null != modelCodes, ExecuteParam::getModelCode, modelCodes)
                .last(null != modelCodes && modelCodes.isEmpty(), "limit 0")
                .eq(ExecuteParam::getEnabled, CommonConst.Num.YES)
                .list();
    }

    public Map<String, ExecuteParam> getParamMap(MyExecuteTemplate myTemplate, Set<String> modelCodes) {
        ExecuteParam param = new ExecuteParam();
        param.setMyTemplateId(myTemplate.getId().toString());
        List<ExecuteParam> paramsMyTemplate = this.getActiveList(param, modelCodes);
        ExecuteParam param2 = new ExecuteParam();
        param2.setGroupId(myTemplate.getGroupId().toString());
        List<ExecuteParam> paramsGroup = this.getActiveList(param2, modelCodes);
        Map<String, ExecuteParam> paramMapMyTemplate = CollUtils.listToMap(paramsMyTemplate, ExecuteParam::getModelCode);
        Map<String, ExecuteParam> paramMapGroup = CollUtils.listToMap(paramsGroup, ExecuteParam::getModelCode);
        Map<String, ExecuteParam> paramMap = new HashMap<>();
        paramMap.putAll(paramMapGroup);
        paramMap.putAll(paramMapMyTemplate);
        return paramMap;
    }
}
