

package com.waminet.common.tools.utils;


import com.alibaba.fastjson.JSON;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


/**
 * RSA加密工具类
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class RsaUtils {

    /**
     * 公钥Key
     */
    public static final String PUBLIC_KEY = "publicKey";

    /**
     * 私钥Key
     */
    public static final String PRIVATE_KEY = "privateKey";

    /**
     * 加密算法RSA
     */
    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 字符集
     */
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 密钥长度
     */
    private final static int KEY_SIZE = 1024;

    /**
     * 最大加密字节数，超出最大字节数需要分组加密
     */
    private final static int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 最大解密字节数，超出最大字节数需要分组加密
     */
    private final static int MAX_DECRYPT_BLOCK = 128;


    public static Map<String, String> generateKeyPair() throws Exception {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到私钥字符串
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        // 将公钥和私钥保存到Map
        Map<String, String> keyMap = new HashMap();
        // 0表示公钥
        keyMap.put(PUBLIC_KEY, publicKeyString);
        // 1表示私钥
        keyMap.put(PRIVATE_KEY, privateKeyString);

        return keyMap;
    }

    /**
     * RSA公钥加密
     *
     * @param source
     * @param publicKey
     * @return
     */
    public static String encrypt(String source, String publicKey) throws Exception {
        // base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        // 公钥
        RSAPublicKey rsaPublicKey = (RSAPublicKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
        // RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);

        // 分段处理
        byte[] resultBytes = {};
        byte[] cache = {};

        // 元数据
        byte[] sourceBytes = source.getBytes();
        int sourceLength = sourceBytes.length;

        // 标识索引
        int offSet = 0;
        // 分段处理
        while (sourceLength - offSet > 0) {
            if (sourceLength - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(sourceBytes, offSet, MAX_ENCRYPT_BLOCK);
                offSet += MAX_ENCRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(sourceBytes, offSet, sourceLength - offSet);
                offSet = sourceLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }

        return Base64.getEncoder().encodeToString(resultBytes);
    }

    /**
     * RSA私钥解密
     *
     * @param cryptograph
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String cryptograph, String privateKey) throws Exception {
        // base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) KeyFactory.getInstance(KEY_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);

        // 元数据
        byte[] cryptographBytes = Base64.getDecoder().decode(cryptograph.getBytes(CHARSET_NAME));
        int cryptographLength = cryptographBytes.length;

        // 分段处理
        byte[] resultBytes = {};
        byte[] cache = {};

        // 标识索引
        int offSet = 0;
        // 分段处理
        while (cryptographLength - offSet > 0) {
            if (cryptographLength - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(cryptographBytes, offSet, MAX_DECRYPT_BLOCK);
                offSet += MAX_DECRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(cryptographBytes, offSet, cryptographLength - offSet);
                offSet = cryptographLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }

        return new String(resultBytes);
    }


    public static void main(String[] args) throws Exception {
        // 生成秘钥
        Map<String, String> keyMap = RsaUtils.generateKeyPair();
        String publicKey = keyMap.get("publicKey");
        String privateKey = keyMap.get("privateKey");

        System.out.println("RSA公钥::::");
        System.out.println(publicKey);
        System.out.println("RSA私钥::::");
        System.out.println(privateKey);
        System.out.println("------------------");

        // 封装请求参数
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        // 店铺id集合（以英文“,”分割）
        params.add("clientId", "$2a$10$.0iL4BCssAtue4QUKEsOmeVBVl8U9MXsTgfHKuRFdoGcmlUzp2kj.");
        // 当前页码，从1开始
        params.add("page", 1);
        // 每页显示记录数
        params.add("limit", 10);
        // 排序字段
        params.add("orderField", "");
        // 排序方式，可选值(asc、desc)
        params.add("order", "");
        // 店铺id集合（以英文“,”分割）
        params.add("mallIds", "467781378");
        // 参数信息
        String source = JSON.toJSONString(params);

        String cryptograph = RsaUtils.encrypt(source, publicKey);
        System.out.println("RSA加密::::");
        System.out.println(cryptograph);
        System.out.println("------------------");

        String result = RsaUtils.decrypt(cryptograph, privateKey);
        System.out.println("RSA解密::::");
        System.out.println(result);
        System.out.println("------------------");
    }

}