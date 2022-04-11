

package com.waminet.common.tools.utils;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * map转换数据格式
 */
public class MapUtils {

    /**
     * map转换string
     * @param map
     * @return
     */
    public static String toString(Map map){
        if(map == null && map.isEmpty()){
            return null;
        }
        return JSON.toJSONString(map);

    }

    /**
     * String转map
     * @param str
     */
    public static Map toMap(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        Gson gson = new Gson();
        Map data = new HashMap();
        return gson.fromJson(str, data.getClass());

    }


}
