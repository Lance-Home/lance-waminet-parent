package ${package.ServiceImpl};

import com.waminet.common.mybatis.service.impl.BaseServiceImpl;
import com.waminet.common.tools.constant.Constant;
import com.waminet.common.tools.page.PageData;
import com.waminet.common.tools.security.user.SecurityUser;
import com.waminet.common.tools.security.user.UserDetail;
import com.waminet.common.tools.utils.ConvertUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${table.entityName};
import ${package.Entity?replace("entity", "dto")}.${table.entityName?replace("Entity", "DTO")};

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ${table.comment}
 *
 * @author ${author}
 * @since 1.0.0
 */
@Service
public class ${table.serviceImplName} extends BaseServiceImpl<${table.mapperName}, ${table.entityName}> implements ${table.serviceName} {

    @Override
    public PageData<${table.entityName?replace("Entity", "DTO")}> page(Map<String, Object> params) {
        // 权限限制
        this.paramsToPermission(params);

        // 分页
        IPage<${table.entityName}> page = getPage(params, "", false);
        // 查询
        List<${table.entityName}> list = baseDao.getList(params);

        return getPageData(list, page.getTotal(), ${table.entityName?replace("Entity", "DTO")}.class);
    }

    @Override
    public List<${table.entityName?replace("Entity", "DTO")}> list(Map<String, Object> params) {
        // 权限限制
        this.paramsToPermission(params);

        // 查询
        List<${table.entityName}> list = baseDao.getList(params);

        return ConvertUtils.sourceToTarget(list, ${table.entityName?replace("Entity", "DTO")}.class);
    }

    @Override
    public ${table.entityName?replace("Entity", "DTO")} get(Long id) {
        QueryWrapper<${table.entityName}> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);

        ${table.entityName} entity = baseDao.selectOne(wrapper);
        return ConvertUtils.sourceToTarget(entity, ${table.entityName?replace("Entity", "DTO")}.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(${table.entityName?replace("Entity", "DTO")} dto) {
        ${table.entityName} entity = ConvertUtils.sourceToTarget(dto, ${table.entityName}.class);

        // 保存
        insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(List<${table.entityName?replace("Entity", "DTO")}> list) {
        List<${table.entityName}> entityList = ConvertUtils.sourceToTarget(list, ${table.entityName}.class);

        // 保存
        insertBatch(entityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(${table.entityName?replace("Entity", "DTO")} dto) {
        ${table.entityName} entity = ConvertUtils.sourceToTarget(dto, ${table.entityName}.class);

        // 更新
        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(List<${table.entityName?replace("Entity", "DTO")}> list) {
        List<${table.entityName}> entityList = ConvertUtils.sourceToTarget(list, ${table.entityName}.class);

        // 更新
        updateBatchById(entityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long[] ids) {
        // 删除
        deleteBatchIds(Arrays.asList(ids));
    }

}
