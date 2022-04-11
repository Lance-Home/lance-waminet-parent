

package com.waminet.common.tools.seata;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局事务XID拦截器
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Slf4j
public class SeataHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String xid = RootContext.getXID();
        String rpcXid = request.getHeader(RootContext.KEY_XID);
        // 获取全局事务编号
        if (log.isDebugEnabled()) {
            log.debug("xid in RootContext {} xid in RpcContext {}", xid, rpcXid);
        }

        if (xid == null && rpcXid != null) {
            // 设置全局事务编号
            RootContext.bind(rpcXid);
            if (log.isDebugEnabled()) {
                log.debug("bind {} to RootContext", rpcXid);
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        String rpcXid = request.getHeader(RootContext.KEY_XID);
        if (StringUtils.isNotEmpty(rpcXid)) {
            String unbindXid = RootContext.unbind();
            if (log.isDebugEnabled()) {
                log.debug("unbind {} from RootContext", unbindXid);
            }

            if (!rpcXid.equalsIgnoreCase(unbindXid)) {
                log.warn("xid in change during RPC from {} to {}", rpcXid, unbindXid);
                if (unbindXid != null) {
                    RootContext.bind(unbindXid);
                    log.warn("bind {} back to RootContext", unbindXid);
                }
            }

        }
    }

}