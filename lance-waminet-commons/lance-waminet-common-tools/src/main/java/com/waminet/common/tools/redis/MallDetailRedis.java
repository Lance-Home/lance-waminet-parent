

package com.waminet.common.tools.redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.waminet.common.tools.security.mall.MallDetail;
import com.waminet.common.tools.security.mall.TokenDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 店铺Redis
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Component
public class MallDetailRedis {

    @Autowired
    private RedisUtils redisUtils;

    public void set(MallDetail mall, long expire) {
        if (mall == null) {
            return;
        }
        String key = RedisKeys.getSecurityMallKey(mall.getId());
        Map<String, Object> map = BeanUtil.beanToMap(mall, false, true);
        redisUtils.hMSet(key, map, expire);
    }

    public MallDetail get(Long id) {
        String key = RedisKeys.getSecurityMallKey(id);

        Map<String, Object> map = redisUtils.hGetAll(key);
        if (MapUtil.isEmpty(map)) {
            return null;
        }

        // map to bean
        MallDetail mall = BeanUtil.mapToBean(map, MallDetail.class, true);
        return mall;
    }

    public void setToken(TokenDetail token, long expire) {
        if (token == null) {
            return;
        }
        String key = RedisKeys.getSecurityMallTokenKey(token.getMallId(), token.getPlatform());
        Map<String, Object> map = BeanUtil.beanToMap(token, false, true);
        redisUtils.hMSet(key, map, expire);
    }

    public TokenDetail getToken(Long mallId, String platform) {
        String key = RedisKeys.getSecurityMallTokenKey(mallId, platform);

        Map<String, Object> map = redisUtils.hGetAll(key);
        if (MapUtil.isEmpty(map)) {
            return null;
        }

        // map to bean
        TokenDetail token = BeanUtil.mapToBean(map, TokenDetail.class, true);
        return token;
    }

    public TokenDetail getToken(String key) {
        Map<String, Object> map = redisUtils.hGetAll(key);
        if (MapUtil.isEmpty(map)) {
            return null;
        }

        // map to bean
        TokenDetail token = BeanUtil.mapToBean(map, TokenDetail.class, true);
        return token;
    }

}
