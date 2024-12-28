package generator;

import com.pro.framework.generator.main.generator.main.GeneratorUtil_class_platform;
import com.pro.snowball.api.model.db.ExecuteParam;
import com.pro.snowball.api.model.db.ExecuteParamField;
import com.pro.snowball.api.model.db.ExecuteParamModel;
import com.pro.snowball.api.model.db.Module;

import java.util.Arrays;
import java.util.List;

/**
 * 新的实体类 生成代码
 */
public class GeneratorUtilMain extends GeneratorUtil_class_platform {

    /**
     * 新的实体类 生成代码
     * 需要先配置好 system-dev.yml
     */
    public static void main(String[] args) {
        List<Class<?>> classes = Arrays.asList(
                ExecuteParam.class,
                ExecuteParamField.class,
                ExecuteParamModel.class
        );
        /**
         * 生成 DemoDao DemoService (DemoController)
         */
        GeneratorUtil_class_platform_main.generate(classes);
        /**
         * 生成 EnumAuthRoute 菜单权限入库枚举的增量内容
         */
        GeneratorUtil_EnumAuthRoute_main.generate(classes);
        /**
         * 生成 ui-admin/demo.vue
         */
        GeneratorUtil_ui_admin_main.generate(classes);
        /**
         * 系统设置/字典 补充国际化翻译键值
         */
    }
}
