package com.waminet.common.tools.form;

import lombok.Data;

import java.util.List;

/**
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
@Data
public class PageDTO<T> {
    private int current = 1;
    private int size = 10;
    private int totalCount;
    private int totalPage;
    private List<T> list;

}
