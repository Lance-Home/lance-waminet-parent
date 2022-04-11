

package com.waminet.common.tools.log;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 登录日志
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysLogLogin extends BaseLog {

    private static final long serialVersionUID = 1L;

    /**
     * 用户操作   0：用户登录   1：用户退出
     */
    private Integer operation;

    /**
     * 状态  0：失败    1：成功    2：账号已锁定
     */
    private Integer status;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 用户名
     */
    private String creatorName;

    /**
     * 创建者
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 用户绑定手机号码
     */
    private String mobile;

}
