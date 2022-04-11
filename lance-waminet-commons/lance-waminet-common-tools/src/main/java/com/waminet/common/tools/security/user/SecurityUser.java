package com.waminet.common.tools.security.user;

import com.waminet.common.tools.constant.Constant;
import com.waminet.common.tools.redis.UserDetailRedis;
import com.waminet.common.tools.utils.HttpContextUtils;
import com.waminet.common.tools.utils.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class SecurityUser {

    private static final UserDetailRedis detailRedis;

    static {
        detailRedis = SpringContextUtils.getBean(UserDetailRedis.class);
    }

    /**
     * 获取用户信息
     */
    public static UserDetail getUser() {
        Long userId = getUserId();
        if (userId == null) {
            return null;
        }

        UserDetail user = detailRedis.get(userId);
        return user;
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (request == null) {
            return null;
        }

        String userId = request.getHeader(Constant.USER_KEY);
        if (StringUtils.isBlank(userId)) {
            return null;
        }

        return Long.parseLong(userId);
    }

}
