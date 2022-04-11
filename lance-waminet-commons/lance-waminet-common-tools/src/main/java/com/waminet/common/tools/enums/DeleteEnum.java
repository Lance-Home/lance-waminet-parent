

package com.waminet.common.tools.enums;

/**
 * 删除标记枚举
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public enum DeleteEnum {

    /**
     * 是（删除）
     */
    YES(1),
    /**
     * 否（未删除）
     */
    NO(0);

    private int value;

    DeleteEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }
}
