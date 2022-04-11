

package com.waminet.common.tools.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 国际化工具类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class MessageUtils {

    private static MessageSource messageSource;

    static {
        messageSource = (MessageSource) SpringContextUtils.getBean("messageSource");
    }

    public static String getMessage(int code) {
        return getMessage(code, new String[0]);
    }

    public static String getMessage(int code, String... params) {
        return messageSource.getMessage(code + "", params, LocaleContextHolder.getLocale());
    }
}
