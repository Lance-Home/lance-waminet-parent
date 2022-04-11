

package com.waminet.common.tools.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waminet.common.tools.exception.RenException;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

/**
 * DES加密和解密工具类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class DesUtils {

    /**
     * 字符串默认键值
     */
    private static String strDefaultKey = "badger-waybill";

    /**
     * 加密工具
     */
    private Cipher encryptCipher;

    /**
     * 解密工具
     */
    private Cipher decryptCipher;

    /**
     * 无参数构造方法：默认构造方法，使用默认密钥
     */
    public DesUtils() {
        this(strDefaultKey);
    }

    /**
     * 含参数构造方法：指定密钥构造方法
     *
     * @param strKey 指定的密钥
     */
    public DesUtils(String strKey) {
        try {
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            Key key = getKey(strKey.getBytes());

            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);

            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception ex) {
            throw new RenException("DES加密和解密工具类构造失败");
        }
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813，
     * 和hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组，
     * 和byteArr2HexStr(byte[] arrB)互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 加密byte数组
     *
     * @param arrB 需加密的byte数组
     * @return 加密后的字节数组
     */
    public byte[] encrypt(byte[] arrB) {
        try {
            return encryptCipher.doFinal(arrB);
        } catch (Exception ex) {
            throw new RenException("加密byte数组失败");
        }
    }

    /**
     * 解密byte数组
     *
     * @param arrB 需解密的byte数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public byte[] decrypt(byte[] arrB) {
        try {
            return decryptCipher.doFinal(arrB);
        } catch (Exception ex) {
            throw new RenException("解密byte数组失败");
        }
    }

    /**
     * 加密字符串
     *
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public String encrypt(String strIn) {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }

    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public String decrypt(String strIn) {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    private Key getKey(byte[] arrBTmp) {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];
        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        // 生成密钥
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }

    /**
     * 多店铺绑定信息
     *
     * @param userId
     * @return
     */
    public String mallBound(Long userId) {
        Map<String, Object> result = new HashMap<>();
        // 多店铺绑定标识
        result.put("option", "mall_bound");
        // 用户id
        result.put("user_id", userId);

        return JSON.toJSONString(result);
    }

    /**
     * RPC绑定信息
     *
     * @param clientId
     * @param clientName
     * @param brandId
     * @param brandName
     * @param deptId
     * @param deptName
     * @param userId
     * @param userName
     * @return
     */
    public String rpcBound(String clientId, String clientName, Long brandId, String brandName, Long deptId, String deptName, Long userId, String userName) {
        Map<String, Object> result = new HashMap<>();
        // RPC绑定标识
        result.put("option", "rpc_bound");
        // 应用id和应用名称
        result.put("client_id", clientId);
        result.put("client_name", clientName);
        // 品牌方id和品牌方名称
        result.put("brand_id", brandId);
        result.put("brand_name", brandName);
        // 部门id和部门名称
        result.put("dept_id", deptId);
        result.put("dept_name", deptName);
        // 用户id和用户名称
        result.put("user_id", userId);
        result.put("user_name", userName);

        return JSON.toJSONString(result);
    }

    /**
     * main方法。
     */
    public static void main(String[] args) {
        // DES加密和解密工具类
        DesUtils desUtils = new DesUtils();

        String clientId = "df1d5193-505c-421b-bf70-e7f3631fc478";
        String clientName = "八诀品牌协同平台";
        Long brandId = 1067246875800002145L;
        String brandName = "南方生活";
        Long deptId = 1067246875800002145L;
        String deptName = "信息技术部";
        Long userId = 1067246875800002145L;
        String userName = "万仕海";

        // 获取RPC绑定信息
        String rpcBound = desUtils.rpcBound(clientId, clientName, brandId, brandName, deptId, deptName, userId, userName);

        System.out.println("加密前的字符：" + rpcBound);
        System.out.println("加密后的字符：" + desUtils.encrypt(rpcBound));
        System.out.println("解密后的字符：" + desUtils.decrypt(desUtils.encrypt(rpcBound)));

        // 品牌授权绑定的场合
        if (StringUtils.isNotBlank(rpcBound)) {
            // 数据转换
            Map<String, Object> result = JSONObject.parseObject(rpcBound, Map.class);
            // RPC绑定标识
            String option2 = result.get("option").toString();
            System.out.println("RPC绑定标识");
            System.out.println(option2);
            // 应用id和应用名称
            String clientId2 = result.get("client_id").toString();
            String clientName2 = result.get("client_name").toString();
            System.out.println("应用id和应用名称");
            System.out.println(clientId2);
            System.out.println(clientName2);
            // 品牌方id和品牌方名称
            String brandId2 = result.get("brand_id").toString();
            String brandName2 = result.get("brand_name").toString();
            System.out.println("品牌方id和品牌方名称");
            System.out.println(brandId2);
            System.out.println(brandName2);
            // 部门id和部门名称
            String deptId2 = result.get("dept_id").toString();
            String deptName2 = result.get("dept_name").toString();
            System.out.println("部门id和部门名称");
            System.out.println(deptId2);
            System.out.println(deptName2);
            // 用户id和用户名称
            String userId2 = result.get("user_id").toString();
            String userName2 = result.get("user_name").toString();
            System.out.println("用户id和用户名称");
            System.out.println(userId2);
            System.out.println(userName2);
        }

    }

}