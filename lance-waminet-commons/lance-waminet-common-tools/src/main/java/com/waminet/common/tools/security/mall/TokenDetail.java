

package com.waminet.common.tools.security.mall;

import lombok.Data;

import java.io.Serializable;

/**
 * 店铺认证授权信息
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
public class TokenDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 店铺id
     */
    private Long mallId;

    /**
     * 店铺名称
     */
    private String mallName;

    /**
     * 电商平台
     */
    private String platform;

    /**
     * access_token
     */
    private String accessToken;

    /**
     * access_token过期时间点
     */
    private Long expiresAt;

    /**
     * access_token过期时间段，10（表示10秒后过期）
     */
    private Integer expiresIn;

    /**
     * refresh token，可用来刷新access_token
     */
    private String refreshToken;

    /**
     * refresh token过期时间点
     */
    private Long refreshTokenExpiresAt;

    /**
     * refresh_token过期时间段，10表示10秒后过期
     */
    private Integer refreshTokenExpiresIn;

}
