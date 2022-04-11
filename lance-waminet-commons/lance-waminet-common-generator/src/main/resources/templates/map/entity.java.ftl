package ${package.Entity};

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.waminet.common.tools.constant.PageForm;
/**
 * ${table.comment}
 *
 * @author ${author}
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("${table.name}")
public class ${table.entityName}  extends PageForm implements Serializable {

    private static final long serialVersionUID = 1L;

    <#list table.fields as field>
    /**
     * ${field.comment}
     */
    private ${field.propertyType} ${field.propertyName};

    </#list>

}
