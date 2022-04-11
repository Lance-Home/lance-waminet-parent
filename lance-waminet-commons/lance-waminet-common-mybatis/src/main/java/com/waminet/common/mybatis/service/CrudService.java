

package com.waminet.common.mybatis.service;

import com.waminet.common.tools.page.PageData;

import java.util.List;
import java.util.Map;

/**
 * CRUD基础服务接口
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public interface CrudService<T, D> extends BaseService<T> {

    PageData<D> page(Map<String, Object> params);

    List<D> list(Map<String, Object> params);

    D get(Long id);

    void save(D dto);

    void update(D dto);

    void delete(Long[] ids);

}
