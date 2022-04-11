

package com.waminet.common.mybatis.service.impl;

import com.waminet.common.mybatis.service.BaseService;
import com.waminet.common.mybatis.utils.EntityUtils;
import com.waminet.common.tools.constant.Constant;
import com.waminet.common.tools.exception.ErrorCode;
import com.waminet.common.tools.exception.RenException;
import com.waminet.common.tools.page.PageData;
import com.waminet.common.tools.security.mall.MallDetail;
import com.waminet.common.tools.security.mall.SecurityMall;
import com.waminet.common.tools.security.user.SecurityUser;
import com.waminet.common.tools.security.user.UserDetail;
import com.waminet.common.tools.utils.ConvertUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础服务类，所有Service都要继承
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {

    @Autowired
    protected M baseDao;

    /**
     * 获取分页对象
     *
     * @param params            分页查询参数
     * @param defaultOrderField 默认排序字段
     * @param isAsc             排序方式
     */
    protected IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        // 分页参数
        long curPage = 1;
        long limit = 10;

        if (params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong((String) params.get(Constant.PAGE));
        }
        if (params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(Constant.LIMIT));
        }

        // 分页对象
        Page<T> page = new Page<>(curPage, limit);

        // 分页参数
        params.put(Constant.PAGE, page);

        // 排序字段
        String orderField = (String) params.get(Constant.ORDER_FIELD);
        String order = (String) params.get(Constant.ORDER);

        // 前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            } else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        // 没有排序字段，则不排序
        if (StringUtils.isEmpty(defaultOrderField)) {
            return page;
        }

        // 默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }

    /**
     * 获取分页对象
     *
     * @param dto               分页查询参数
     * @param defaultOrderField 默认排序字段
     * @param isAsc             排序方式
     */
    protected IPage<T> getPage(Object dto, String defaultOrderField, boolean isAsc) {
        // 分页参数
        long curPage = 1;
        long limit = 10;

        Map<String, Object> params = objectToMap(dto);

        if (params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong((String) params.get(Constant.PAGE));
        }
        if (params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(Constant.LIMIT));
        }

        // 分页对象
        Page<T> page = new Page<>(curPage, limit);

        // 分页参数
        params.put(Constant.PAGE, page);

        // 排序字段
        String orderField = (String) params.get(Constant.ORDER_FIELD);
        String order = (String) params.get(Constant.ORDER);

        // 前端字段排序
        if (StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)) {
            if (Constant.ASC.equalsIgnoreCase(order)) {
                return page.addOrder(OrderItem.asc(orderField));
            } else {
                return page.addOrder(OrderItem.desc(orderField));
            }
        }

        // 没有排序字段，则不排序
        if (StringUtils.isEmpty(defaultOrderField)) {
            return page;
        }

        // 默认排序
        if (isAsc) {
            page.addOrder(OrderItem.asc(defaultOrderField));
        } else {
            page.addOrder(OrderItem.desc(defaultOrderField));
        }

        return page;
    }

    protected <T> PageData<T> getPageData(List<?> list, long total, Class<T> target) {
        List<T> targetList = ConvertUtils.sourceToTarget(list, target);

        return new PageData<>(targetList, total);
    }

    protected <T> PageData<T> getPageData(IPage page, Class<T> target) {
        return getPageData(page.getRecords(), page.getTotal(), target);
    }

    protected void paramsToLike(Map<String, Object> params, String... likes) {
        for (String like : likes) {
            String val = (String) params.get(like);
            if (StringUtils.isNotEmpty(val)) {
                params.put(like, "%" + val + "%");
            } else {
                params.put(like, null);
            }
        }
    }

    /**
     * 权限限制
     *
     * @param params
     * @return
     */
    protected void paramsToPermission(Map<String, Object> params) {
        // 普通用户，只能查询所属店铺的数据
        UserDetail user = SecurityUser.getUser();

        // 未登录的场合
        if (user == null) {
            return;
        }

        // 版本标识
        if (user.getBundle().equals(Constant.BUNDLE_ADMIN)) {
            return;
        }

        // 店铺信息
        MallDetail mall = SecurityMall.getMall();

        // 未授权的场合
        if (mall == null) {
            throw new RenException(ErrorCode.ACCOUNT_DENIED_ACCESS);
        }

        // 默认追加店铺id
        params.put("mallId", mall.getMallId());
    }

    /**
     * 权限限制
     *
     * @param dto
     * @return
     */
    protected void dtoToPermission(Object dto) {

        Map<String, Object> params = objectToMap(dto);

        // 普通用户，只能查询所属店铺的数据
        UserDetail user = SecurityUser.getUser();

        // 未登录的场合
        if (user == null) {
            return;
        }

        // 版本标识
        if (user.getBundle().equals(Constant.BUNDLE_ADMIN)) {
            return;
        }

        // 店铺信息
        MallDetail mall = SecurityMall.getMall();

        // 未授权的场合
        if (mall == null) {
            throw new RenException(ErrorCode.ACCOUNT_DENIED_ACCESS);
        }

        // 默认追加店铺id
        params.put("mallId", mall.getMallId());
    }

    /**
     * 获取利用反射获取类里面的值和名称
     *
     * @param obj
     * @return Map<String, Object>
     */
    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 逻辑删除
     *
     * @param ids    ids
     * @param entity 实体
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logicDelete(Long[] ids, Class<T> entity) {
        List<T> entityList = EntityUtils.deletedBy(ids, entity);

        return updateBatchById(entityList);
    }

    /**
     * 判断数据库操作是否成功
     * 注意！！ 该方法为 Integer 判断，不可传入 int 基本类型
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected static boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 批量操作 SqlSession
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * 获取SqlStatement
     *
     * @param sqlMethod
     * @return
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Override
    public boolean insert(T entity) {
        return BaseServiceImpl.retBool(baseDao.insert(entity));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(Collection<T> entityList) {
        return insertBatch(entityList, 100);
    }

    /**
     * 批量插入
     *
     * @param entityList
     * @param batchSize
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(Collection<T> entityList, int batchSize) {
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try {
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        } finally {
            closeSqlSession(batchSqlSession);
        }
        return true;
    }

    @Override
    public boolean updateById(T entity) {
        return BaseServiceImpl.retBool(baseDao.updateById(entity));
    }

    @Override
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        return BaseServiceImpl.retBool(baseDao.update(entity, updateWrapper));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> entityList) {
        return updateBatchById(entityList, 30);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        SqlSession batchSqlSession = sqlSessionBatch();
        int i = 0;
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        try {
            for (T anEntityList : entityList) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, anEntityList);
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        } finally {
            closeSqlSession(batchSqlSession);
        }
        return true;
    }

    @Override
    public T selectById(Serializable id) {
        return baseDao.selectById(id);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return SqlHelper.retBool(baseDao.deleteById(id));
    }

    @Override
    public boolean deleteBatchIds(Collection<? extends Serializable> idList) {
        return SqlHelper.retBool(baseDao.deleteBatchIds(idList));
    }
}
