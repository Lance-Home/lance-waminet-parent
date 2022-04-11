

package com.waminet.common.tools.security.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 店铺信息
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
public class MallBO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    private Long id;

    /**
     * 店铺id（三方）
     */
    private Long mallId;

    /**
     * 店铺logo
     */
    private String logo;

    /**
     * 店铺名称
     */
    private String mallName;

    /**
     * 店铺描述
     */
    private String mallDesc;

    /**
     * 店铺类型
     */
    private Integer merchantType;

    /**
     * 店铺身份
     */
    private Integer mallCharacter;

    /**
     * 电商平台
     */
    private String platform;

    /**
     * 三方授权标识
     */
    private String openId;

    /**
     * 店铺分组标识
     */
    private String groupId;

    /**
     * 店铺授权开始时间
     */
    private Long startAt;

    /**
     * 店铺授权结束时间
     */
    private Long endAt;

}
