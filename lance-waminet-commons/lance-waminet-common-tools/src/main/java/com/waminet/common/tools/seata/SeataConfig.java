

package com.waminet.common.tools.seata;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册HandlerInterceptor，拦截所有请求
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Configuration
public class SeataConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册HandlerInterceptor，拦截所有请求
        registry.addInterceptor(new SeataHandlerInterceptor()).addPathPatterns(new String[] { "/**" });
    }

}
