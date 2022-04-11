package com.waminet.common.tools.security.user;

import com.waminet.common.tools.security.bo.MallBO;
import com.waminet.common.tools.security.bo.ResourceBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录用户信息
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 头像
     */
    private String headUrl;

    /**
     * 性别   0：男   1：女    2：保密
     */
    private Integer gender;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 推广渠道
     */
    private String source;

    /**
     * 版本标识
     */
    private String bundle;

    /**
     * 超级管理员  0：否    1：是
     */
    private Integer superAdmin;

    /**
     * 用户角色   0：系统授权   1：店铺管理员   2：店铺子账户
     */
    private Integer role;

    /**
     * 三方授权id
     */
    private String openId;

    /**
     * 状态  0：正常    1：停用
     */
    private Integer status;

    /**
     * 是否被管理员踢出   0：正常   1：被踢出，无权调用接口
     */
    private int kill;

    /**
     * 用户资源列表
     */
    private List<ResourceBO> resourceList;

    /**
     * 用户店铺列表
     */
    private List<MallBO> mallList;

}
