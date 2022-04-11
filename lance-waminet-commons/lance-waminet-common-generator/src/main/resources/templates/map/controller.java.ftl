package ${package.Controller};

import com.waminet.common.tools.annotation.LogOperation;
import com.waminet.common.tools.constant.Constant;
import com.waminet.common.tools.page.PageData;
import com.waminet.common.tools.utils.ExcelUtils;
import com.waminet.common.tools.utils.Result;
import com.waminet.common.tools.validator.AssertUtils;
import com.waminet.common.tools.validator.ValidatorUtils;
import com.waminet.common.tools.validator.group.AddGroup;
import com.waminet.common.tools.validator.group.DefaultGroup;
import com.waminet.common.tools.validator.group.UpdateGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import ${package.Entity?replace("entity", "dto")}.${table.entityName?replace("Entity", "DTO")};
import ${package.Service}.${table.serviceName};

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * ${table.comment}
 *
 * @author ${author}
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("<#if package.moduleName??>${package.moduleName}/${table.name}<#else>${table.name}</#if>")
@Api(tags = "${table.comment}")
@RequiredArgsConstructor
public class ${table.controllerName} {


    private final ${table.serviceName} ${table.serviceName?uncap_first};

    @GetMapping("page")
    @ApiOperation("分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constant.PAGE, value = "当前页码，从1开始", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.LIMIT, value = "每页显示记录数", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = Constant.ORDER_FIELD, value = "排序字段", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = Constant.ORDER, value = "排序方式，可选值(asc、desc)", paramType = "query", dataType = "String")
    })
    public Result<PageData<${table.entityName?replace("Entity", "DTO")}>> page(@ApiIgnore @RequestParam Map<String, Object> params) {
        PageData<${table.entityName?replace("Entity", "DTO")}> page = ${table.serviceName?uncap_first}.page(params);

        return new Result<PageData<${table.entityName?replace("Entity", "DTO")}>>().ok(page);
    }

    @GetMapping("list")
    @ApiOperation("列表")
    public Result<List<${table.entityName?replace("Entity", "DTO")}>> list(@ApiIgnore @RequestParam Map<String, Object> params) {
        List<${table.entityName?replace("Entity", "DTO")}> list = ${table.serviceName?uncap_first}.list(params);

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
