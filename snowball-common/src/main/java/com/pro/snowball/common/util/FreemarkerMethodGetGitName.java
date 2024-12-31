package com.pro.snowball.common.util;

import cn.hutool.json.JSONUtil;
import freemarker.template.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 从 git@github.com:lzb-framework/framework.git 中解析出 framework 这个根目录
 */
public class FreemarkerMethodGetGitName implements TemplateMethodModelEx {
    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.size() != 1) {
            throw new TemplateModelException("Expected one argument");
        }

        Object arg = arguments.get(0);

        if (arg instanceof SimpleScalar) {
            String value = ((SimpleScalar) arg).getAsString();
            String file_git = value.substring(value.lastIndexOf("/") + 1);
            return file_git.substring(0, file_git.length() - 4);
        }

        // 使用 Jackson 将对象转换为 JSON 字符串
        return arg;
    }
}
