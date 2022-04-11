package ${package.Service};

import com.waminet.common.mybatis.service.BaseService;
import com.waminet.common.tools.page.PageData;
import ${package.Entity}.${table.entityName};
import ${package.Entity?replace("entity", "dto")}.${table.entityName?replace("Entity", "DTO")};

import java.util.List;

/**
 *
 * ${table.comment}
 *
 * @author ${author}
 * @since 1.0.0
 */
public interface ${table.serviceName} extends BaseService<${table.entityName}> {

    PageData<${table.entityName?replace("Entity", "DTO")}> page(${table.entityName?replace("Entity", "DTO")} dto);

    List<${table.entityName?replace("Entity", "DTO")}> findAll(${table.entityName?replace("Entity", "DTO")} dto);

    ${table.entityName?replace("Entity", "DTO")} get(Long id);

    void save(${table.entityName?replace("Entity", "DTO")} dto);

    void saveBatch(List<${table.entityName?replace("Entity", "DTO")}> list);

    void update(${table.entityName?replace("Entity", "DTO")} dto);

    void updateBatch(List<${table.entityName?replace("Entity", "DTO")}> list);

    void delete(Long[] ids);

}
