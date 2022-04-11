

package com.waminet.common.mybatis.aspect;

import cn.hutool.core.util.ObjectUtil;
import com.waminet.common.mybatis.annotation.DataFilter;
import com.waminet.common.tools.exception.ErrorCode;
import com.waminet.common.tools.exception.RenException;
import com.waminet.common.tools.security.user.SecurityUser;
import com.waminet.common.tools.security.user.UserDetail;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 数据过滤，切面处理类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Aspect
@Component
public class DataFilterAspect {

    @Pointcut("@annotation(com.waminet.common.mybatis.annotation.DataFilter)")
    public void dataFilterCut() {

    }

    @Before("dataFilterCut()")
    public void dataFilter(JoinPoint point) {
        Object params = point.getArgs()[0];
        if (ObjectUtil.isNotEmpty(params) && params instanceof Map) {
            UserDetail user = SecurityUser.getUser();

            // 用户不存在的场合，则不进行数据过滤
            if (user == null) {
                return;
            }

            try {
                // 否则进行数据过滤
                Map map = (Map) params;
                String sqlFilter = getSqlFilter(user, point);
                // map.put(Constant.SQL_FILTER, new DataScope(sqlFilter));
            } catch (Exception e) {

            }

            return;
        }

        throw new RenException(ErrorCode.DATA_SCOPE_PARAMS_ERROR);
    }

    /**
     * 获取数据过滤的SQL
     */
    private String getSqlFilter(UserDetail user, JoinPoint point) throws Exception {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = point.getTarget().getClass().getDeclaredMethod(signature.getName(), signature.getParameterTypes());
        DataFilter dataFilter = method.getAnnotation(DataFilter.class);

        // 获取表的别名
        String tableAlias = dataFilter.tableAlias();
        if (StringUtils.isNotBlank(tableAlias)) {
            tableAlias += ".";
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");
        sqlFilter.append(" 1 = 1");
        sqlFilter.append(")");

        return sqlFilter.toString();
    }
}
