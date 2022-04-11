package ${package.Controller};

import com.waminet.common.tools.annotation.LogOperation;
import com.waminet.common.tools.page.PageData;
import com.waminet.common.tools.utils.Result;
import com.waminet.common.tools.validator.AssertUtils;
import com.waminet.common.tools.validator.ValidatorUtils;
import com.waminet.common.tools.validator.group.AddGroup;
import com.waminet.common.tools.validator.group.DefaultGroup;
import com.waminet.common.tools.validator.group.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import ${package.Entity?replace("entity", "dto")}.${table.entityName?replace("Entity", "DTO")};
import ${package.Service}.${table.serviceName};

import java.util.List;

/**
 *
 * ${table.comment}
 *
 * @author ${author}
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("<#if package.moduleName??>${package.moduleName}/${table.name}<#else>${table.name}</#if>")
@Api(tags = "${table.comment}")
@RequiredArgsConstructor(onConstructor =@_(@Autowired))
public class ${table.controllerName} {

    private final ${table.serviceName} ${table.serviceName?uncap_first};

    @GetMapping("page")
    @ApiOperation("分页")
    public Result<PageData<${table.entityName?replace("Entity", "DTO")}>> page(@RequestBody ${table.entityName?replace("Entity", "DTO")} dto) {
        PageData<${table.entityName?replace("Entity", "DTO")}> page = ${table.serviceName?uncap_first}.page(dto);

        return new Result<PageData<${table.entityName?replace("Entity", "DTO")}>>().ok(page);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public Result<List<${table.entityName?replace("Entity", "DTO")}>> findAll(@RequestBody ${table.entityName?replace("Entity", "DTO")} dto) {
        List<${table.entityName?replace("Entity", "DTO")}> list = ${table.serviceName?uncap_first}.findAll(dto);

        return new Result<List<${table.entityName?replace("Entity", "DTO")}>>().ok(list);
    }

    @GetMapping("{id}")
    @ApiOperation("信息")
    public Result<${table.entityName?replace("Entity", "DTO")}> get(@PathVariable("id") Long id) {
        ${table.entityName?replace("Entity", "DTO")} data = ${table.serviceName?uncap_first}.get(id);

        return new Result<${table.entityName?replace("Entity", "DTO")}>().ok(data);
    }

    @PostMapping
    @ApiOperation("保存")
    @LogOperation("保存")
    public Result save(@RequestBody ${table.entityName?replace("Entity", "DTO")} dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, AddGroup.class, DefaultGroup.class);

        ${table.serviceName?uncap_first}.save(dto);

        return new Result();
    }

    @PutMapping
    @ApiOperation("修改")
    @LogOperation("修改")
    public Result update(@RequestBody ${table.entityName?replace("Entity", "DTO")} dto) {
        // 效验数据
        ValidatorUtils.validateEntity(dto, UpdateGroup.class, DefaultGroup.class);

        ${table.serviceName?uncap_first}.update(dto);

        return new Result();
    }

    @DeleteMapping
    @ApiOperation("删除")
    @LogOperation("删除")
    public Result delete(@RequestBody Long[] ids) {
        // 效验数据
        AssertUtils.isArrayEmpty(ids, "id");

        ${table.serviceName?uncap_first}.delete(ids);

        return new Result();
    }

}
