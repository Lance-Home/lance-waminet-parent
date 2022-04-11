

package com.waminet.common.tools.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.seata.core.context.RootContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * feign 熔断器开启之后 传递 token验证header 头使用
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }

        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    requestTemplate.header(name, value);
                }
            }
        }

        // 封装seata头信息
        String kid = (String) request.getAttribute(RootContext.KEY_XID);
        if (StringUtils.isNotEmpty(kid)) {
            // 封装请求头信息
            requestTemplate.header(RootContext.KEY_XID, kid);
        } else {
            // 重新获取kid
            kid = RootContext.getXID();
            if (StringUtils.isNotEmpty(kid)) {
                // 封装请求头信息
                requestTemplate.header(RootContext.KEY_XID, kid);
            }
        }

    }

}
