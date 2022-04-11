

package com.waminet.common.tools.redisson;


import java.lang.annotation.*;

/**
 * Redisson 分布式锁 注解类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissonLock {

    /**
     * 自定义加锁名称 ： waybill:redisson:prefix:args
     */
    String prefix() default "";

    /**
     * 自定义索引分组 ： #arguments...
     */
    String group() default "";

    /**
     * 锁最大等待时间（默认5秒）
     */
    long maxSleepTime() default 5L;

}
