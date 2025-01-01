package generator;

import com.pro.framework.generator.main.generator.main.GeneratorUtil_class_platform;
import com.pro.snowball.api.model.db.*;

import java.util.Arrays;

/**
 * 生成 DemoDao.java DemoService.java DemoController.java
 */
public class GeneratorUtil_class_platform_main extends GeneratorUtil_class_platform {
    public static void main(String[] args) {
        generate(
                Arrays.asList(
                        ExecuteOrder.class,
//                        ExecuteOrderStep.class,
                        ExecuteOrderStepCommand.class,
                        ExecuteStep.class,
                        ExecuteStepCommand.class,
                        ExecuteTemplate.class,
                        ExecuteTemplateAndStep.class,
                        ExecuteGroup.class,
                        MyExecuteTemplate.class
                )
        );
    }
}
