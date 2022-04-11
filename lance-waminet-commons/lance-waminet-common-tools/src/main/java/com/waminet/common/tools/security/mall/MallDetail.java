

package com.waminet.common.tools.security.mall;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录店铺信息
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
public class MallDetail implements Serializable {

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
     * 店铺logo
     */
    private String logo;

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
     * 状态  0：停用    1：正常
     */
    private Integer status;

    /**
     * 电商平台
     */
    private String platform;

    /**
     * 授权开始时间
     */
    private Long startAt;

    /**
     * 授权结束时间
     */
    private Long endAt;

    /**
     * 授权状态 0：授权中    1：授权即将过期    2：授权已过期
     */
    private Integer serviceStatus;

    /**
     * 服务市场版本    basic：基础版    advance：高级版
     */
    private String serviceVersion;

    /**
     * 删除标识  0：未删除    1：删除
     */
    private Integer delFlag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后同步时间
     */
    private Date lastSyncTime;

    /**
     * 绑定手机号码
     */
    private String bindMobile;

    /**
     * 绑定手机号码时间
     */
    private Date bindMobileTime;

}
