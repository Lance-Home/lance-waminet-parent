package com.waminet.common.tools.utils;

import com.waminet.common.tools.redis.RedisKeys;
import com.waminet.common.tools.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsUtils {

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 短信验证码校验
     *
     * @param uuid
     * @param code
     * @return
     */
    public boolean validate(String uuid, String code) {

        // 获取缓存验证码
        Object captchaObj = redisUtils.get(RedisKeys.getSmsCaptchaKey(uuid));

        // 缓存验证码不存在的场合
        if (captchaObj == null) {
            return false;
        }

        String captcha = (String) captchaObj;

        // 验证码是否正确
        if (code.equalsIgnoreCase(captcha)) {
            return true;
        }

        return false;
    }
}
