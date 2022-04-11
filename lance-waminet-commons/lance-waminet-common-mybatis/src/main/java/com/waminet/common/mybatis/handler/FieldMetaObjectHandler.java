

package com.waminet.common.mybatis.handler;

import com.waminet.common.mybatis.enums.DelFlagEnum;
import com.waminet.common.tools.security.user.SecurityUser;
import com.waminet.common.tools.security.user.UserDetail;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 公共字段，自动填充
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {

    private final static String CREATE_TIME = "createTime";
    private final static String CREATE_DATE = "createDate";
    private final static String CREATOR = "creator";
    private final static String UPDATE_TIME = "updateTime";
    private final static String UPDATE_DATE = "updateDate";
    private final static String UPDATER = "updater";
    private final static String DEL_FLAG = "delFlag";

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();

        // 创建时间
        setInsertFieldValByName(CREATE_TIME, date, metaObject);
        // 创建时间
        setInsertFieldValByName(CREATE_DATE, date, metaObject);
        // 更新时间
        setInsertFieldValByName(UPDATE_TIME, date, metaObject);
        // 更新时间
        setInsertFieldValByName(UPDATE_DATE, date, metaObject);
        // 删除标识
        setInsertFieldValByName(DEL_FLAG, DelFlagEnum.NORMAL.value(), metaObject);

        UserDetail user = SecurityUser.getUser();
        if (user == null) {
            return;
        }

        // 创建者
        setInsertFieldValByName(CREATOR, user.getId(), metaObject);
        // 更新者
        setInsertFieldValByName(UPDATER, user.getId(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date date = new Date();

        // 更新时间
        setUpdateFieldValByName(UPDATE_TIME, date, metaObject);
        // 更新时间
        setUpdateFieldValByName(UPDATE_DATE, date, metaObject);

        UserDetail user = SecurityUser.getUser();
        if (user == null) {
            return;
        }

        // 更新者
        setUpdateFieldValByName(UPDATER, SecurityUser.getUserId(), metaObject);
    }
}
