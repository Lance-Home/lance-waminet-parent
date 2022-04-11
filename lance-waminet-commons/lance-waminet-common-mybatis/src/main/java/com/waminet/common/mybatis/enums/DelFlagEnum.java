package com.waminet.common.mybatis.enums;

/**
 * 删除标识枚举类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public enum DelFlagEnum {

    NORMAL(0),
    DEL(1);

    private int value;

    DelFlagEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
