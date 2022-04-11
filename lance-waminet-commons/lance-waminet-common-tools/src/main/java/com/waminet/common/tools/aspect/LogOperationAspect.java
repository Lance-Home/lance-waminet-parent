

package com.waminet.common.tools.aspect;

import com.alibaba.fastjson.JSON;
import com.waminet.common.tools.annotation.LogOperation;
import com.waminet.common.tools.config.ModuleConfig;
import com.waminet.common.tools.log.SysLogOperation;
import com.waminet.common.tools.log.enums.LogTypeEnum;
import com.waminet.common.tools.log.enums.OperationStatusEnum;
import com.waminet.common.tools.log.producer.LogProducer;
import com.waminet.common.tools.security.user.SecurityUser;
import com.waminet.common.tools.security.user.UserDetail;
import com.waminet.common.tools.utils.HttpContextUtils;
import com.waminet.common.tools.utils.IpUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 操作日志，切面处理类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Aspect
@Component
public class LogOperationAspect {

    @Autowired
    private ModuleConfig moduleConfig;
    @Autowired
    private LogProducer logProducer;

    @Pointcut("@annotation(com.waminet.common.tools.annotation.LogOperation)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        try {
            // 执行方法
            Object result = point.proceed();

            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            // 保存日志
            saveLog(point, time, OperationStatusEnum.SUCCESS.value());

            return result;
        } catch (Exception e) {
            // 执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;
            // 保存日志
            saveLog(point, time, OperationStatusEnum.FAIL.value());

            throw e;
        }
    }


    private void saveLog(ProceedingJoinPoint joinPoint, long time, Integer status) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogOperation log = new SysLogOperation();
        LogOperation annotation = method.getAnnotation(LogOperation.class);
        if (annotation != null) {
            // 注解上的描述
            log.setOperation(annotation.value());
        }

        // 登录用户信息
        UserDetail user = SecurityUser.getUser();
        if (user != null) {
            log.setCreator(user.getId());
            log.setCreatorName(user.getAccount());
        }

        log.setType(LogTypeEnum.OPERATION.value());
        log.setModule(moduleConfig.getName());
        log.setStatus(status);
        log.setRequestTime((int) time);
        log.setCreateDate(new Date());

        // 请求相关信息
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.setIp(IpUtils.getIpAddr(request));
        log.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        log.setRequestUri(request.getRequestURI());
        log.setRequestMethod(request.getMethod());

        // 请求参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSON.toJSONString(args[0]);
            log.setRequestParams(params);
        } catch (Exception e) {

        }

        // 保存到Redis队列里
        logProducer.saveLog(log);
    }
}
