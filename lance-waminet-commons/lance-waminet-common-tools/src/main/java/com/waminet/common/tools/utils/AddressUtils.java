

package com.waminet.common.tools.utils;

import com.waminet.common.tools.exception.RenException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 地址工具类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class AddressUtils {

    /**
     * 姓名 + 手机号码 + 地址，智能拆分
     *
     * @param str
     * @return
     */
    public static Map<String, String> split2(String str) {
        Map<String, String> result = new HashMap<>();
        // 去除空格、回车、制表符、换行符
        str = str.replaceAll("\\s*|\t|\r|\n", "");

        // 手机号码
        String mobile = AddressUtils.getMobile(str);
        // 姓名
        String userName = AddressUtils.getUserName(str, mobile);
        // 地址
        String address = AddressUtils.getAddress(str, mobile);
        // 地址拆分
        Map<String, String> addressMap = AddressUtils.split(address);
        // 省份
        String province = addressMap.get("province");
        // 城市
        String city = addressMap.get("city");
        // 区县
        String county = addressMap.get("county");
        // 街道
        String street = addressMap.get("street");

        // 姓名
        result.put("userName", userName);
        // 手机号码
        result.put("mobile", mobile);

        // 省份
        result.put("province", province);
        // 城市
        result.put("city", city);
        // 区县
        result.put("county", county);
        // 街道
        result.put("street", street);

        return result;
    }

    /**
     * 处理详细地址拆分省 市 区 地址的转换关系
     *
     * @param address
     * @return
     */
    public static Map<String, String> split(String address) {
        Map<String, String> result = new HashMap<>();
        try {
            // 临时地址，去除空格、回车、制表符、换行符
            String tempAddress = address.replaceAll("\\s*|\t|\r|\n", "");

            // 直辖市，省、市都为直辖市
            tempAddress = tempAddress.replaceAll("北京市北京市", "北京市");
            tempAddress = tempAddress.replaceAll("上海市上海市", "上海市");
            tempAddress = tempAddress.replaceAll("天津市天津市", "天津市");
            tempAddress = tempAddress.replaceAll("重庆市重庆市", "重庆市");

            // 省份
            String province = null;
            int provinceIdx = processProvince(tempAddress);
            if (provinceIdx > -1) {
                province = tempAddress.substring(0, provinceIdx + 1);
                // 直辖市，省、市都为直辖市
                if ("北京市、天津市、上海市、重庆市".contains(province) == false) {
                    tempAddress = tempAddress.substring(provinceIdx + 1);
                }
            }

            // 城市
            String city = null;
            int cityIdx = processCity(tempAddress);
            if (cityIdx > -1) {
                city = tempAddress.substring(0, cityIdx + 1);
                tempAddress = tempAddress.substring(cityIdx + 1);
            }

            // 区县
            String county = null;
            int countyIdx = processCounty(tempAddress);
            if (countyIdx > -1) {
                county = tempAddress.substring(0, countyIdx + 1);
                tempAddress = tempAddress.substring(countyIdx + 1);
            }

            // 街道
            String street = tempAddress;

            // 省份
            result.put("province", province);
            // 城市
            result.put("city", city);
            // 区县
            result.put("county", county);
            // 街道
            result.put("street", street);
        } catch (Exception e) {
            // 报错就直接返回r 为空即可。无法正常转义
        }

        return result;
    }

    /**
     * 省份分解
     *
     * @param address
     * @return
     */
    private static int processProvince(String address) {
        int[] idxs = new int[3];
        int provinceIdx = -1;
        if ((provinceIdx = address.indexOf("省")) > -1) {
            idxs[0] = provinceIdx;
        }
        provinceIdx = -1;
        if ((provinceIdx = address.indexOf("市")) > -1) {
            idxs[1] = provinceIdx;
        }
        provinceIdx = -1;
        if ((provinceIdx = address.indexOf("区")) > -1) {
            idxs[2] = provinceIdx;
        }

        Arrays.sort(idxs);

        for (int i = 0; i < idxs.length; i++) {
            int j = idxs[i];
            if (j > 0) {
                return j;
            }
        }

        return provinceIdx;
    }

    /**
     * 城市分解
     *
     * @param address
     * @return
     */
    private static int processCity(String address) {
        int[] idxs = new int[7];
        int cityIdx = -1;
        if ((cityIdx = address.indexOf("县")) > -1) {
            idxs[0] = cityIdx;
        }
        cityIdx = -1;
        if ((cityIdx = address.indexOf("自治州")) > -1) {
            idxs[1] = cityIdx + 2;
        }
        cityIdx = -1;
        if ((cityIdx = address.indexOf("市辖区")) > -1) {
            idxs[2] = cityIdx + 2;
        }
        cityIdx = -1;
        if ((cityIdx = address.indexOf("市")) > -1) {
            idxs[3] = cityIdx;
        }
        cityIdx = -1;
        if ((cityIdx = address.indexOf("区")) > -1) {
            idxs[4] = cityIdx;
        }
        cityIdx = -1;
        if ((cityIdx = address.indexOf("地区")) > -1) {
            idxs[5] = cityIdx + 1;
        }
        cityIdx = -1;
        if ((cityIdx = address.indexOf("盟")) > -1) {
            idxs[6] = cityIdx;
        }

        Arrays.sort(idxs);

        for (int i = 0; i < idxs.length; i++) {
            int j = idxs[i];
            if (j > 0) {
                return j;
            }
        }

        return cityIdx;
    }

    /**
     * 区县分解
     *
     * @param address
     * @return
     */
    private static int processCounty(String address) {
        int[] idxs = new int[6];
        int countyIdx = -1;
        if ((countyIdx = address.indexOf("县")) > -1) {
            idxs[0] = countyIdx;
        }
        countyIdx = -1;
        if ((countyIdx = address.indexOf("旗")) > -1) {
            idxs[1] = countyIdx;
        }
        countyIdx = -1;
        if ((countyIdx = address.indexOf("海域")) > -1) {
            idxs[2] = countyIdx + 1;
        }
        countyIdx = -1;
        if ((countyIdx = address.indexOf("市")) > -1) {
            idxs[3] = countyIdx;
        }
        countyIdx = -1;
        if ((countyIdx = address.indexOf("区")) > -1) {
            idxs[4] = countyIdx;
        }
        countyIdx = -1;
        if ((countyIdx = address.indexOf("岛")) > -1) {
            idxs[5] = countyIdx;
        }

        Arrays.sort(idxs);

        for (int i = 0; i < idxs.length; i++) {
            int j = idxs[i];
            if (j > 0) {
                return j;
            }
        }

        return countyIdx;
    }

    private static String getMobile(String str) {
        Pattern pattern = Pattern.compile("[1][3,4,5,7,8][0-9]{9}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find() == false) {
            throw new RenException("提取手机号码失败");
        }

        return matcher.group(0);
    }

    private static String getUserName(String str, String mobile) {
        String[] splits = str.split(mobile);
        if (splits == null || splits.length != 2) {
            throw new RenException("提取姓名失败");
        }

        return splits[0];
    }

    private static String getAddress(String str, String mobile) {
        String[] splits = str.split(mobile);
        if (splits == null || splits.length != 2) {
            throw new RenException("提取地址失败");
        }

        return splits[1];
    }

    public static void main(String[] args) {
//        Map<String, String> splits1 = split("湖南省邵阳市绥宁县内蒙古自治区巴彦淖尔盟乌拉特前旗");
//        System.out.println(Arrays.asList(splits1).toString());
//
//        Map<String, String> splits2 = split("新疆省阿勒泰地区吉木乃县");
//        System.out.println(Arrays.asList(splits2).toString());
//
//        Map<String, String> splits3 = split("宁夏回族自治区固原市隆德县");
//        System.out.println(Arrays.asList(splits3).toString());
//
//        Map<String, String> splits4 = split("内蒙古自治区锡林郭勒盟东乌珠穆沁旗乌里雅斯太镇");
//        System.out.println(Arrays.asList(splits4).toString());


        String str = "丁晓伟18785522347上海市观山湖区愚园路753号2号楼5楼Y座";

        Map<String, String> result = AddressUtils.split2(str);

        // 手机号码
        String mobile = result.get("mobile");
        // 姓名
        String userName = result.get("userName");
        // 省份
        String province = result.get("province");
        // 城市
        String city = result.get("city");
        // 区县
        String county = result.get("county");
        // 街道
        String street = result.get("street");

        System.out.println(mobile);
        System.out.println(userName);
        System.out.println(province);
        System.out.println(city);
        System.out.println(county);
        System.out.println(street);

    }
}
