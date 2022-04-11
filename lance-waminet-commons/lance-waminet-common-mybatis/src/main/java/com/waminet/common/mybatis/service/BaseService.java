

package com.waminet.common.mybatis.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;

import java.io.Serializable;
import java.util.Collection;

/**
 * 基础服务接口，所有Service接口都要继承
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public interface BaseService<T> {

    /**
     * 逻辑删除
     *
     * @param ids    ids
     * @param entity 实体
     *
     * @return boolean
     */
    boolean logicDelete(Long[] ids, Class<T> entity);

    /**
     * 插入一条记录（选择字段，策略插入）
     *
     * @param entity 实体对象
     */
    boolean insert(T entity);

    /**
     * 插入（批量），该方法不支持 Oracle、SQL Server
     *
     * @param entityList 实体对象集合
     */
    boolean insertBatch(Collection<T> entityList);

    /**
     * 插入（批量）
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    boolean insertBatch(Collection<T> entityList, int batchSize);

    /**
     * 根据 id 选择修改
     *
     * @param entity 实体对象
     */
    boolean updateById(T entity);

    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    boolean update(T entity, Wrapper<T> updateWrapper);

    /**
     * 根据id 批量更新
     *
     * @param entityList 实体对象集合
     */
    boolean updateBatchById(Collection<T> entityList);

    /**
     * 根据id 批量更新
     *
     * @param entityList 实体对象集合
     * @param batchSize  更新批次数量
     */
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    /**
     * 根据 id 查询
     *
     * @param id 主键id
     */
    T selectById(Serializable id);

    /**
     * 根据 id 删除
     *
     * @param id 主键id
     */
    boolean deleteById(Serializable id);

    /**
     * 删除（根据id 批量删除）
     *
     * @param idList 主键id列表
     */
    boolean deleteBatchIds(Collection<? extends Serializable> idList);
}
