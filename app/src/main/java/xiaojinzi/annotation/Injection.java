package xiaojinzi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 运行时期有效,和针对的是字段
 *
 * @author xiaojinzi
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Injection {

    /**
     * 当针对字段的时候就是对应布局文件的id
     * 当针对类的时候,就是表示Activity的使用的视图
     *
     * @return
     */
    int value();

    /**
     * 点击事件
     *
     * @return
     */
    String click() default "";

    /**
     * 长按的点击事件
     *
     * @return
     */
    String longClick() default "";

}
