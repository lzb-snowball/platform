package com.pro.snowball.common.service;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.pro.common.modules.api.dependencies.CommonConst;
import com.pro.framework.api.util.BeanUtils;
import com.pro.framework.api.util.CollUtils;
import com.pro.framework.api.util.StrUtils;
import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.ExecuteParam;
import com.pro.snowball.api.model.db.MyExecuteTemplate;
import com.pro.snowball.common.dao.ExecuteParamDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * 参数值服务
 */
@Service
@Slf4j
public class ExecuteParamService extends BaseService<ExecuteParamDao, ExecuteParam> {
    public List<ExecuteParam> getActiveList(ExecuteParam executeParam, Set<String> modelCodes) {
        return this.lambdaQuery()
                .setEntity(executeParam)
                .orderByAsc(ExecuteParam::getSort)
                .in(null != modelCodes, ExecuteParam::getModelCode, modelCodes)
                .last(null != modelCodes && modelCodes.isEmpty(), "limit 0")
                .eq(ExecuteParam::getEnabled, CommonConst.Num.YES)
                .list();
    }

//    public List<ExecuteParam> getActiveListOrEmpty(ExecuteParam executeParam, Set<String> modelCodes) {
//        QueryWrapper<ExecuteParam> qw = this.qw();
//        Field[] fields = ReflectUtil.getFields(ExecuteParam.class);
//        for (Field field : fields) {
//            int modifiers = field.getModifiers();
//            if (Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers) && !Modifier.isTransient(modifiers)) {
//                Object value = ReflectUtil.getFieldValue(executeParam, field);
//                if (value instanceof String && !StrUtils.EMPTY.equals(value)) {
//                    qw.in(StrUtil.toUnderlineCase(field.getName()), Arrays.asList(value, StrUtils.EMPTY));
//                }
//            }
//        }
//        LambdaQueryWrapper<ExecuteParam> qw2 = qw.lambda()
//                .orderByAsc(ExecuteParam::getSort)
//                .in(null != modelCodes, ExecuteParam::getModelCode, modelCodes)
//                .last(null != modelCodes && modelCodes.isEmpty(), "limit 0")
//                .eq(ExecuteParam::getEnabled, CommonConst.Num.YES);
//        return this.list(qw);
//    }

    public Map<String, ExecuteParam> getParamMap(MyExecuteTemplate myTemplate, Set<String> modelCodes) {
//        ExecuteParam executeParam = new ExecuteParam();
////        executeParam.setTemplateId(myTemplate.getTemplateId().toString());
//        executeParam.setGroupId(myTemplate.getGroupId().toString());
//        executeParam.setMyTemplateId(myTemplate.getId().toString());
//        Map<String, ExecuteParam> paramMapGroup = this.getParamMapBy(modelCodes, ExecuteParam::setGroupId,
//                executeParam.getGroupId());
//        Map<String, ExecuteParam> paramMapTemplate = this.getParamMapBy(executeParam, modelCodes,
//                ExecuteParam::setTemplateId,
//                executeParam.getTemplateId());
//        Map<String, ExecuteParam> paramMapMyTemplate = this.getParamMapBy(modelCodes, ExecuteParam::setMyTemplateId, executeParam.getMyTemplateId());
//        Map<String, ExecuteParam> paramMapStep = getParamMapBy(modelCodes, ExecuteParam::setTemplateId, myTemplate.getTemplateId().toString());
        String myTemplateId = myTemplate.getId().toString();
        String groupId = myTemplate.getGroupId().toString();
        List<ExecuteParam> paramsMyTemplate = this.getActiveList(null, modelCodes);
        List<ExecuteParam> paramAll = paramsMyTemplate.stream()
                .filter(p -> Objects.equals(p.getGroupId(), StrUtils.EMPTY) && Objects.equals(p.getMyTemplateId(), StrUtils.EMPTY))
                .toList();
        List<ExecuteParam> paramGroup = paramsMyTemplate.stream()
                .filter(p -> !Objects.equals(p.getGroupId(), StrUtils.EMPTY) && Objects.equals(p.getMyTemplateId(), StrUtils.EMPTY))
                .filter(p -> p.getGroupId().equals(groupId))
                .toList();
        List<ExecuteParam> paraMyTemplate = paramsMyTemplate.stream()
                .filter(p -> Objects.equals(p.getGroupId(), StrUtils.EMPTY) && !Objects.equals(p.getMyTemplateId(), StrUtils.EMPTY))
                .filter(p -> p.getMyTemplateId().equals(myTemplateId))
                .toList();
        List<ExecuteParam> paraMyGroupTemplate = paramsMyTemplate.stream()
                .filter(p -> !Objects.equals(p.getGroupId(), StrUtils.EMPTY) && !Objects.equals(p.getMyTemplateId(), StrUtils.EMPTY))
                .filter(p -> p.getGroupId().equals(groupId) && p.getMyTemplateId().equals(myTemplateId))
                .toList();
        Map<String, ExecuteParam> paramMap = new HashMap<>();
        paramAll.forEach(param -> paramMap.put(param.getModelCode(), param));
        paramGroup.forEach(param -> paramMap.put(param.getModelCode(), param));
        paraMyTemplate.forEach(param -> paramMap.put(param.getModelCode(), param));
        paraMyGroupTemplate.forEach(param -> paramMap.put(param.getModelCode(), param));
        return paramMap;
    }

    private Map<String, ExecuteParam> getParamMapBy(Set<String> modelCodes, BiConsumer<ExecuteParam, String> keyPropFun, String keyPropVal) {
        ExecuteParam param = new ExecuteParam();
        param.setGroupId(StrUtils.EMPTY);
        param.setMyTemplateId(StrUtils.EMPTY);
//        param.setTemplateId(StrUtils.EMPTY);
//        param.setStepId(StrUtils.EMPTY);
//        param.setStepCommandId(StrUtils.EMPTY);
        param.setEnabled(true);
        // setMyTemplateId(StrUtils.EMPTY)
        keyPropFun.accept(param, keyPropVal);
        List<ExecuteParam> paramsMyTemplate = this.getActiveList(param, modelCodes);
        return CollUtils.listToMap(paramsMyTemplate, ExecuteParam::getModelCode);
    }
}
