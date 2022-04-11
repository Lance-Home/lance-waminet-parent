

package com.waminet.common.tools.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class CommonUtils {

    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    /**
     * 获取短信内容
     *
     * @param template
     * @param params
     * @return
     */
    public static String parseToSms(String template, Map<String, String> params) {
        // 示例：尊敬的${name}，您的快递已在飞奔的路上，将在今天${time}送达您的手里，请留意查收。
        Set<Map.Entry<String, String>> sets = params.entrySet();
        for (Map.Entry<String, String> entry : sets) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(template);
            template = matcher.replaceAll(entry.getValue());
        }

        return template;
    }


    /**
     * 生成6位数字短信验证码
     *
     * @return
     */
    public static String createSmsCaptcha() {
        String str1 = "123456789";
        String str2 = "1234567890";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer("");

        int n = random.nextInt(str1.length() - 1);
        stringBuffer.append(str1.charAt(n));
        for (int i = 0; i < 5; i++) {
            n = random.nextInt(str2.length() - 1);
            stringBuffer.append(str2.charAt(n));
        }
        return stringBuffer.toString();
    }

    /**
     * 生成6位数字电子面单共享码
     *
     * @return
     */
    public static String createShareCaptcha() {
        String str1 = "123456789";
        String str2 = "1234567890";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer("");

        int n = random.nextInt(str1.length() - 1);
        stringBuffer.append(str1.charAt(n));
        for (int i = 0; i < 5; i++) {
            n = random.nextInt(str2.length() - 1);
            stringBuffer.append(str2.charAt(n));
        }
        return stringBuffer.toString();
    }

}
