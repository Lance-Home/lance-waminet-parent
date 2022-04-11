

package com.waminet.common.tools.security.sign;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * RPC签名信息
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
public class RpcSignDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 应用id
     */
    private String clientId;

    /**
     * 应用名称
     */
    private String clientName;

    /**
     * 应用描述
     */
    private String clientDesc;

    /**
     * RSA公钥
     */
    private String publicKey;

    /**
     * RSA私钥
     */
    private String privateKey;

    /**
     * 删除标识  0：未删除    1：删除
     */
    private Integer delFlag;

    /**
     * 创建者
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新者
     */
    private Long updater;

    /**
     * 更新时间
     */
    private Date updateDate;

}
