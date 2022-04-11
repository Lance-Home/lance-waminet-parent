

package com.waminet.common.tools.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
@ApiModel(value = "分页数据")
public class PageData<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总记录数")
    private int total;

    @ApiModelProperty(value = "列表数据")
    private List<T> list;

    /**
     * 分页
     * @param list   列表数据
     * @param total  总记录数
     */
    public PageData(List<T> list, long total) {
        this.list = list;
        this.total = (int) total;
    }

    /**
     * 分页
     * @param list   列表数据
     * @param total  总记录数
     */
    public PageData(T list, long total) {
        this.list = (List<T>) list;
        this.total = (int) total;
    }
}
