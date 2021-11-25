package com.chen.test;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

/**
 * @description: 参数排序加密工具类
 * @author: oliver
 * @create: 2019-08-09 09:59
 **/
public class Sha256SignUtil {
    private static Logger logger = LoggerFactory.getLogger(Sha256SignUtil.class);

    public static String buildSign(JSONObject param, String privateKey) {
        StringBuffer sb = new StringBuffer();
        List<String> keyList = new ArrayList<>(param.keySet());
        Collections.sort(keyList);
        for (String key : keyList) {
            String paramKey = param.getString(key);
            if (!"sign".equals(key) && paramKey != null) {
                sb.append(key).append("=").append(paramKey).append("&");
            }
        }
        // 除了"sign"其他的拼接到一起签名
        String verifyString = sb.substring(0, sb.length() - 1);
        logger.info("sha256 验签明文：{}", verifyString);
        String buildSign;
        try {
            buildSign = HMACSHA256(verifyString, privateKey);
        } catch (Exception e) {
            logger.error("sha256 加签异常：" + e);
            throw new RuntimeException("sha256 加密异常");
        }
        String base64Sign = Base64.getEncoder().encodeToString(buildSign.getBytes(StandardCharsets.UTF_8));
        return base64Sign;
    }

    public static Boolean verifySign(JSONObject param, String privateKey, String sign) {
        StringBuffer sb = new StringBuffer();
        List<String> keyList = new ArrayList<>(param.keySet());
        Collections.sort(keyList);
        for (String key : keyList) {
            String paramKey = param.getString(key);
            if (!"sign".equals(key) && paramKey != null) {
                sb.append(key).append("=").append(paramKey).append("&");
            }
        }
        // 除了"sign"其他的拼接到一起签名
        String verifyString = sb.substring(0, sb.length() - 1);
        logger.info("sha256 验签明文：{}", verifyString);
        String buildSign;
        try {
            buildSign = HMACSHA256(verifyString, privateKey);
        } catch (Exception e) {
            logger.error("sha256 验签异常：" + e);
            return false;
        }
        String base64Sign = Base64.getEncoder().encodeToString(buildSign.getBytes(StandardCharsets.UTF_8));
        if (base64Sign.equals(sign)) {
            return true;
        }
        return false;
    }

    /**
     * HMACSHA256 加密
     *
     * @param data 加密数据
     * @param key  加密秘钥
     * @return
     */
    public static String HMACSHA256(String data, String key) throws Exception {

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (byte item : array) {

            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));

        }
        return sb.toString().toUpperCase();
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


}
    