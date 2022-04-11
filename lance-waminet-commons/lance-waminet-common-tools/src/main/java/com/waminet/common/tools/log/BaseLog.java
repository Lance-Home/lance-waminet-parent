

package com.waminet.common.tools.log;

import lombok.Data;

import java.io.Serializable;

/**
 * Log基类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
public abstract class BaseLog implements Serializable {

    /**
     * 日志类型
     */
    private Integer type;

}
