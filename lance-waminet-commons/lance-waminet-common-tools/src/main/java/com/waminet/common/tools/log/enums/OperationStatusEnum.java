

package com.waminet.common.tools.log.enums;

/**
 * 操作状态枚举
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public enum OperationStatusEnum {

    /**
     * 失败
     */
    FAIL(0),
    /**
     * 成功
     */
    SUCCESS(1);

    private int value;

    OperationStatusEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
