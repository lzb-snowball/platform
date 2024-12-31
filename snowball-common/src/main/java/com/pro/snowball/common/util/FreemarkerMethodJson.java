package com.pro.snowball.common.util;

import cn.hutool.json.JSONUtil;
import freemarker.template.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreemarkerMethodJson implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.size() != 1) {
            throw new TemplateModelException("Expected one argument");
        }

        Object arg = arguments.get(0);

        // 如果是 TemplateHashModel（例如 DefaultMapAdapter），需要手动提取键值对
        if (arg instanceof DefaultMapAdapter) {
            arg = convertTemplateHashModelToMap((DefaultMapAdapter) arg);
        }

        // 使用 Jackson 将对象转换为 JSON 字符串
        return JSONUtil.toJsonStr(arg);
    }

    // 将 TemplateHashModel 转换为 Java Map
    private Map<String, Object> convertTemplateHashModelToMap(DefaultMapAdapter model) throws TemplateModelException {
        Map<String, Object> map = new HashMap<>();
        TemplateModelIterator keyIterator = model.keys()
                .iterator();
        while (keyIterator.hasNext()) {
            TemplateModel keyModel = keyIterator.next();  // 获取下一个键
            String key = keyModel.toString();  // 将键转换为字符串

            // 获取与该键对应的值
            TemplateModel valueModel = model.get(key);
            map.put(key, ((SimpleScalar)valueModel).getAsString());
        }
        return map;
    }
}
