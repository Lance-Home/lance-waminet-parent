package com.waminet.common.tools.security.enums;

/**
 * 用户被踢出枚举
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public enum UserKillEnum {

    YES(1),
    NO(0);

    private int value;

    UserKillEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
