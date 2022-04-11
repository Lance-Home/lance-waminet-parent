

package com.waminet.common.tools.utils;

/**
 * 数字处理工具类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class NumberUtils {

    /**
     * 数字格式化 保留2位小数
     *
     * @param num
     * @return
     */
    public static String format2f(Double num) {
        if (num == null) {
            return "0.00";
        }

        return String.format("%1.2f", num);
    }

    public static void main(String[] args) {
        Double num = Double.parseDouble("999.99");
        String numx = NumberUtils.format2f(num);
        System.out.println(numx);
    }

}
