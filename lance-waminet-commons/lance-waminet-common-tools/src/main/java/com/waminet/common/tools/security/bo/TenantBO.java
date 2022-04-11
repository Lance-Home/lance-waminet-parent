

package com.waminet.common.tools.security.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 租户信息
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
public class TenantBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    private Long id;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 租户类型 0：企业 1：个人
     */
    private Integer tenantType;

    /**
     * 联系人
     */
    private String username;

    /**
     * 手机号码
     */
    private String mobile;


}
