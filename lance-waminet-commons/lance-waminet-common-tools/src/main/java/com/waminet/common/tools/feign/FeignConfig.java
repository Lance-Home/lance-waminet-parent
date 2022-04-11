

package com.waminet.common.tools.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign 熔断器开启之后 传递 token验证header 头使用
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Configuration
public class FeignConfig {

    @Bean("feignRequestInterceptor")
    public RequestInterceptor getRequestInterceptor() {
        return new FeignRequestInterceptor();
    }

}
