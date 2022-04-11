

package com.waminet.common.tools.security.mall;

import com.waminet.common.tools.constant.Constant;
import com.waminet.common.tools.redis.MallDetailRedis;
import com.waminet.common.tools.security.bo.MallBO;
import com.waminet.common.tools.security.user.SecurityUser;
import com.waminet.common.tools.security.user.UserDetail;
import com.waminet.common.tools.utils.HttpContextUtils;
import com.waminet.common.tools.utils.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 店铺信息
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class SecurityMall {

    private static MallDetailRedis mallDetailRedis;

    static {
        mallDetailRedis = SpringContextUtils.getBean(MallDetailRedis.class);
    }

    /**
     * 获取店铺信息
     */
    public static MallDetail getMall() {
        Long id = getId();
        if (id == null) {
            return null;
        }

        MallDetail mall = mallDetailRedis.get(id);
        return mall;
    }

    /**
     * 获取店铺授权信息
     */
    public static TokenDetail getToken() {
        MallDetail mall = getMall();
        if (mall == null) {
            return null;
        }

        TokenDetail token = mallDetailRedis.getToken(mall.getMallId(), mall.getPlatform());
        return token;
    }

    /**
     * 获取店铺id
     */
    private static Long getId() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        if (request == null) {
            return null;
        }

        String mallId = request.getHeader(Constant.CURRENT_MALL_ID);
        if (StringUtils.isBlank(mallId)) {
            mallId = request.getParameter(Constant.CURRENT_MALL_ID);
            if (StringUtils.isBlank(mallId)) {
                return null;
            }
        }

        UserDetail user = SecurityUser.getUser();
        if (user == null) {
            return null;
        }

        List<MallBO> mallList = user.getMallList();

        if (mallList == null || mallList.isEmpty()) {
            return null;
        }

        for (MallBO mall : mallList) {
            if (mallId.equals(mall.getMallId().toString())) {
                return mall.getId();
            }
        }

        return null;
    }

}
