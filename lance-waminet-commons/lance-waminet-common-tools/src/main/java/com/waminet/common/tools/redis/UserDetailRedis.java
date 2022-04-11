

package com.waminet.common.tools.redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import com.waminet.common.tools.security.enums.UserKillEnum;
import com.waminet.common.tools.security.user.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户Redis
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Component
public class UserDetailRedis {

    @Autowired
    private RedisUtils redisUtils;

    public void set(UserDetail user, long expire) {
        if (user == null) {
            return;
        }
        String key = RedisKeys.getSecurityUserKey(user.getId());
        // bean to map
        user.setKill(UserKillEnum.NO.value());
        Map<String, Object> map = BeanUtil.beanToMap(user, false, true);
        redisUtils.hMSet(key, map, expire);

        // 用户登录时，清空菜单导航、权限标识
        redisUtils.delete(RedisKeys.getUserMenuNavKey(user.getId()));
        redisUtils.delete(RedisKeys.getUserPermissionsKey(user.getId()));
    }

    public UserDetail get(Long id) {
        String key = RedisKeys.getSecurityUserKey(id);

        Map<String, Object> map = redisUtils.hGetAll(key);
        if (MapUtil.isEmpty(map)) {
            return null;
        }

        // map to bean
        UserDetail user = BeanUtil.mapToBean(map, UserDetail.class, true);

        return user;
    }

    /**
     * 用户退出
     *
     * @param id  用户id
     */
    public void logout(Long id) {
        String key = RedisKeys.getSecurityUserKey(id);
        redisUtils.hSet(key, "kill", UserKillEnum.YES.value());

        // 清空菜单导航、权限标识
        redisUtils.deleteByPattern(RedisKeys.getUserMenuNavKey(id));
        redisUtils.delete(RedisKeys.getUserPermissionsKey(id));
    }
}
