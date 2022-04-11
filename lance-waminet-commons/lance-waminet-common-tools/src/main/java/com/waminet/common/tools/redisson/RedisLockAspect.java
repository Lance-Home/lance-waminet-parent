

package com.waminet.common.tools.redisson;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redisson 分布式锁 注解切面
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Component
@Scope
@Aspect
@Order(1)
public class RedisLockAspect {

    private Logger logger = LoggerFactory.getLogger(RedisLockAspect.class);

    // waybill:redisson:prefix:args
    private static final String REDIS_PREFIX = "waybill:redisson";

    @Autowired
    private RedissonClient redissonClient;


    @Around("@annotation(com.waminet.common.tools.redisson.RedissonLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取注解方法相关信息
        String methodName = joinPoint.getSignature().getName();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Class<?>[] paraTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        Method method = targetClass.getMethod(methodName, paraTypes);
        Parameter[] parameters = method.getParameters();
        Object[] arguments = joinPoint.getArgs();

        // 获取注解参数
        RedissonLock annotation = method.getAnnotation(RedissonLock.class);
        String prefix = annotation.prefix();
        String group = annotation.group();
        long maxSleepTime = annotation.maxSleepTime();

        logger.info("Redisson Try Locking... -> Method:{} Arguments:{}", methodName, arguments);

        Object[] args = new Object[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof ServletRequest || arguments[i] instanceof ServletResponse || arguments[i] instanceof MultipartFile) {
                continue;
            }
            args[i] = arguments[i];
        }

        String lockKey = this.getMsiLockKey(prefix, group, targetClass.getSimpleName(), method.getName(), parameters, args);
        RLock rLock = redissonClient.getLock(lockKey);
        if (rLock.tryLock(maxSleepTime, TimeUnit.SECONDS)) {
            try {
                logger.info("Redisson Locked Successfully -> Lock Key:{}", lockKey);
                return joinPoint.proceed();
            } finally {
                rLock.unlock();
                logger.info("Redisson Released Successfully -> Lock Key:{}", lockKey);
            }
        } else {
            logger.info("Redisson Locking Failed -> Method:{} Arguments:{}", methodName, arguments);
        }

        return null;
    }

    /**
     * 获取分布式锁关键字
     *
     * @param prefix
     * @param group
     * @param className
     * @param invokedMethod
     * @param parameters
     * @param args
     * @return
     */
    private String getMsiLockKey(String prefix, String group, String className, String invokedMethod, Parameter[] parameters, Object[] args) {

        // 前缀不存在的场合，前缀 = 类名.方法名
        if (StringUtils.isEmpty(prefix)) {
            prefix = className + "." + invokedMethod;
        }

        // 分组为空的场合
        if (StringUtils.isEmpty(group)) {
            // 生成分布式锁关键字
            return REDIS_PREFIX + ":" + prefix;
        }

        // 分组参数
        List groupArgs = new ArrayList();

        // 拆解分组字段
        String[] indexs = group.split("#");
        // 遍历分组字段
        for (String index : indexs) {
            // 分组字段为空的场合
            if (StringUtils.isEmpty(index)) {
                continue;
            }

            // 索引参数对象
            Object indexArg = null;

            // 遍历所有参数列表
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                // 参数名称一致的场合
                if (parameter.getName().equals(index)) {
                    indexArg = args[i];
                    break;
                }
            }

            // 索引参数对象不存在的场合
            if (indexArg == null) {
                logger.error("Redisson Locking Failed -> Group:{} Exception:{}", index, "Index Not Found Or Not Support");
                throw new RuntimeException();
            }

            // 追加索引参数对象
            groupArgs.add(indexArg);
        }

        // 分组参数不存在的场合
        if (groupArgs == null || groupArgs.isEmpty()) {
            logger.error("Redisson Locking Failed -> Group:{} Exception:{}", group, "Index Not Found Or Not Support");
            throw new RuntimeException();
        }

        // 参数字符串
        String argsKey = StringUtils.arrayToDelimitedString(groupArgs.toArray(), "&");
        // 生成分布式锁关键字
        return REDIS_PREFIX + ":" + prefix + ":" + argsKey;
    }

}
