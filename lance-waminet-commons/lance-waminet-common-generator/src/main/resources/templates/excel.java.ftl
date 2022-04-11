package ${package.Entity};

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * ${table.comment}
 *
 * @author ${author}
 * @since 1.0.0
 */
@Data
public class ${table.entityName} implements Serializable {

    private static final long serialVersionUID = 1L;

    <#list table.fields as field>
        @Excel(name = "${field.comment}", width = 20)
        private ${field.propertyType} ${field.propertyName};

    </#list>

}
