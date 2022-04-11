

package com.waminet.common.mybatis.service.impl;

import com.waminet.common.mybatis.service.CrudService;
import com.waminet.common.tools.page.PageData;
import com.waminet.common.tools.utils.ConvertUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * CRUD基础服务类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public abstract class CrudServiceImpl<M extends BaseMapper<T>, T, D> extends BaseServiceImpl<M, T> implements CrudService<T, D> {

    protected Class<D> currentDtoClass() {
        return (Class<D>) ReflectionKit.getSuperClassGenericType(getClass(), 2);
    }

    @Override
    public PageData<D> page(Map<String, Object> params) {
        IPage<T> page = baseDao.selectPage(
                getPage(params, null, false),
                getWrapper(params)
        );

        return getPageData(page, currentDtoClass());
    }

    @Override
    public List<D> list(Map<String, Object> params) {
        List<T> entityList = baseDao.selectList(getWrapper(params));

        return ConvertUtils.sourceToTarget(entityList, currentDtoClass());
    }

    public abstract QueryWrapper<T> getWrapper(Map<String, Object> params);

    @Override
    public D get(Long id) {
        T entity = baseDao.selectById(id);

        return ConvertUtils.sourceToTarget(entity, currentDtoClass());
    }

    @Override
    public void save(D dto) {
        T entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        insert(entity);

        // copy主键值到dto
        BeanUtils.copyProperties(entity, dto);
    }

    @Override
    public void update(D dto) {
        T entity = ConvertUtils.sourceToTarget(dto, currentModelClass());
        updateById(entity);
    }

    @Override
    public void delete(Long[] ids) {
        baseDao.deleteBatchIds(Arrays.asList(ids));
    }
}
