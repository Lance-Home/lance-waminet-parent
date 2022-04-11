package ${package.Mapper};

import com.waminet.common.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import ${package.Entity}.${table.entityName};

import java.util.List;
import java.util.Map;

/**
 * ${table.comment}
 *
 * @author ${author}
 * @since 1.0.0
 */
@Mapper
public interface ${table.mapperName} extends BaseDao<${table.entityName}> {

    List<${table.entityName}> getList(Map<String, Object> params);

}
