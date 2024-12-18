package generator;

import com.pro.framework.generator.main.generator.main.GeneratorUtil_EnumAuthRoute;
import com.pro.framework.generator.main.generator.main.GeneratorUtil_ui_admin;
import com.pro.snowball.api.model.db.*;

import java.util.Arrays;

/**
 * 生成 EnumAuthRouteSnowball.java 中新的菜单配置枚举行
 */
public class GeneratorUtil_EnumAuthRoute_main extends GeneratorUtil_EnumAuthRoute {
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
