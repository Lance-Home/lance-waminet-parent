package com.waminet.common.tools.lambda;

/**
 * 获取字段名
 *
 * @author Lance
 * @version 1.0
 * @data 2022/03/24 16:15
 */
public class LambdaUtils {

    public static <T, R> String getfieldName(TypeFunction<T, R> typeFunction) {
        return TypeFunction.getLambdaColumnName(typeFunction);
    }

}
