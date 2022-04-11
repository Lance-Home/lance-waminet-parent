

package com.waminet.common.tools.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogOperation {

    String value() default "";

}
