package com.waminet.common.tools.utils;

import com.waminet.common.tools.exception.ErrorCode;
import com.waminet.common.tools.exception.RenException;
import com.waminet.common.tools.security.mall.MallDetail;
import com.waminet.common.tools.security.mall.SecurityMall;
import org.springframework.stereotype.Component;

/**
 * @description: 权限限制
 * @author Lance
 * @version 1.0.0
 * @data: 2022-03-08
 */
@Component
public class ToPermission {

    /**
     * 权限限制
     *
     * @return
     */
    public Long paramsToPermission(){
        // 店铺信息
        MallDetail mall = SecurityMall.getMall();
         //未授权的场合
        if (mall == null) {
            throw new RenException(ErrorCode.ACCOUNT_DENIED_ACCESS);
        }
         //默认追加店铺id
       return  mall.getMallId();
    }
}