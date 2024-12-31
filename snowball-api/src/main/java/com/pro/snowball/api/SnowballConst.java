package com.pro.snowball.api;

/**
 * 滑雪场固有常量
 */
public class SnowballConst {

    public static class Str {
        /**
         * 目录-工作空间
         */
        public static final String folder_snowballWorkspace = "snowballWorkspace";
        /**
         * 目录-日志
         */
        public static final String folder_snowballLog = "snowballLog";

        /**
         * 参数统一编号字段
         */
        public static final String KEY_PROP_NAME_CODE = "code";
        /**
         * 线程前缀
         */
        public static final String THREAD_HEAD = "executeOrder_";
        /**
         * 参数-服务器参数-默认取值模型编号
         */
        public static final String PARAM_MODEL_CODE_SERVERS = "servers";
    }

    public static class EntityClass {
        public static final String ExecuteOrder = "ExecuteOrder";
        public static final String MyExecuteTemplate = "MyExecuteTemplate";
    }
}
