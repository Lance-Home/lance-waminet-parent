

package com.waminet.common.tools.log.enums;

/**
 * 日志类型枚举
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public enum LogTypeEnum {

    /**
     * 登录日志
     */
    LOGIN(0),
    /**
     * 操作日志
     */
    OPERATION(1),
    /**
     * 异常日志
     */
    ERROR(2);

    private int value;

    LogTypeEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
