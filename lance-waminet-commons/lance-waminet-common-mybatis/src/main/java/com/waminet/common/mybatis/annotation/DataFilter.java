

package com.waminet.common.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 数据过滤注解
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {

    /**
     * 表的别名
     */
    String tableAlias() default "";

    /**
     * 用户id
     */
    String userId() default "creator";

}
