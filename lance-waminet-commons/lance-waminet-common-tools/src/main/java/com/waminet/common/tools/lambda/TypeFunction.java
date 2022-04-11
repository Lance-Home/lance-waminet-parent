package com.waminet.common.tools.lambda;

import java.beans.Introspector;
import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.function.Function;


/**
 * 函数式接口 -  反射 Lambda获取属性值
 *
 * @param <T>
 * @param <R>
 * @author Lance
 * @version 1.0
 * @data 2022/03/24 15:11
 */
@FunctionalInterface
public interface TypeFunction<T, R> extends Serializable, Function<T, R> {

    /**
     * 获取列名称
     *
     * @param lambda
     * @return String
     */
    static String getLambdaColumnName(Serializable lambda) {
        try {
            Method method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);
            String getter = serializedLambda.getImplMethodName();
            String fieldName = Introspector.decapitalize(getter.replace("get", ""));
            return fieldName;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }


}