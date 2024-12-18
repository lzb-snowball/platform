package generator;

import com.pro.framework.generator.main.generator.main.GeneratorUtil_ui_admin;
import com.pro.snowball.api.model.db.*;

import java.util.Arrays;


/**
 * 生成 ui-admin/../demo.vue
 */
public class GeneratorUtil_ui_admin_main extends GeneratorUtil_ui_admin {
    public static void main(String[] args) {
        generate(
                Arrays.asList(
                        ExecuteOrder.class,
                        ExecuteOrderStep.class,
                        ExecuteOrderStepCommand.class,
                        ExecuteStep.class,
                        ExecuteStepCommand.class,
                        ExecuteStepParamRequired.class,
                        ExecuteTemplate.class,
                        ExecuteTemplateAndStep.class,
                        MyExecuteGroup.class,
                        MyExecuteTemplate.class,
                        MyExecuteTemplateParam.class,
                        RemoteServer.class
                )
        );
    }
}
