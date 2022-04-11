package com.waminet.common.tools.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
/**
 * @description: 统一分页响应报文
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "统一分页响应消息报文")
public class PageVo<T> implements Serializable {

    private static final long serialVersionUID = -8827554154457692610L;

    @ApiModelProperty("当前页")
    private Long current;

    @ApiModelProperty("页码大小")
    private Long size;

    @ApiModelProperty("总页数")
    private Long totalPage;

    @ApiModelProperty("总数")
    private Long total;

    @ApiModelProperty("结果集合")
    private List<T> list;

    public PageVo(Long current, Long size, Long total, List<T> list) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.list = list;
    }

    public PageVo(Long current, Long size, Long total) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.list = Collections.emptyList();
    }

    public Long getTotalPage() {
        if (total == null || size == null || total == 0 || size == 0) {
            return 0L;
        }
        return total % size == 0 ? total / size : total / size + 1;
    }


}
