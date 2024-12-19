package com.pro.snowball.common.service.xmlparse.condition;

import java.util.Map;

public interface ConditionEvaluator {
    boolean evaluate(String condition, Map<String, Object> params);
}