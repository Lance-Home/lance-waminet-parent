package com.waminet.common.tools.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @description: 分页入参
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
public class PageForm {
    /**
     * 当前页
     */
    @NotNull(message = "[page]不能为空")
    @ApiModelProperty(value = "当前页", required = true, example = "1")
    protected Integer page = 1;

    /**
     * 每页显示条数
     */
    @NotNull(message = "[pageSize]不能为空")
    @Max(value = 1000L, message = "[pageSize]最大值不能超过1000")
    @ApiModelProperty(value = "页大小", required = true, example = "20")
    protected Integer pageSize = 20;
}