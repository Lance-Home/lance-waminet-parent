package ${package.Mapper};

import com.waminet.common.mybatis.dao.BaseDao;
import ${package.Entity?replace("entity", "dto")}.${table.entityName?replace("Entity", "DTO")};
import org.apache.ibatis.annotations.Mapper;
import ${package.Entity}.${table.entityName};

import java.util.List;

/**
 *
 * ${table.comment}
 *
 * @author ${author}
 * @since 1.0.0
 */
@Mapper
public interface ${table.mapperName} extends BaseDao<${table.entityName}> {

    List<${table.entityName}> findList(${table.entityName?replace("Entity", "DTO")} dto);

}
