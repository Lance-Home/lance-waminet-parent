package com.waminet.common.mybatis.enums;

/**
 * 状态标识枚举类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public enum StatusEnum {

    //正常
    NORMAL(1),

    //停用
    DEACTIVATE(0);

    private int value;

    StatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
