package com.chen.test;

import com.alibaba.fastjson.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/8/20
 **/
public class FixedVirtualAccountDemoTest {

    // 测试环境测试账号
    private static final String merchantId = "S820210728101537000000";                         // 商户ID
    private static final String privateKey = "5c214677-6667-4a0c-8cc2-de338d1346ad";           // 商户私钥
    private static final String payUrl = "https://openapi.afinpay.com/gateway/createVA";   // 请求地址
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        JSONObject reqJson = new JSONObject();
        reqJson.put("merchantId", merchantId);                           // 商户ID
        reqJson.put("method",  "BNI");                                   // 支付方式，指定 创建的固定VA 所属银行
        reqJson.put("outOrderNo", "P" + System.currentTimeMillis());     // 商户请求流水，每次请求必须唯一
        reqJson.put("customerName", "Tom");                              // 客户名称
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTime = localDateTime.format(dateTimeFormatter);
        reqJson.put("dateTime", dateTime);                               // 请求创建时间，格式：yyyy-MM-dd HH:mm:ss
        String sign = Sha256SignUtil.buildSign(reqJson, privateKey);
        reqJson.put("sign", sign);                                       // 签名字符串
        System.out.println("请求参数:" + reqJson);
        Long start = System.currentTimeMillis();
        JSONObject respJson = doPost(payUrl, reqJson);                   // 发送 post json请求
        Long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end-start));
        System.out.println("请求返回:" + respJson);
        // 验证签名
        Boolean isVerify = Sha256SignUtil.verifySign(respJson, privateKey, respJson.getString("sign"));
        if (isVerify) {
            System.out.println("验证签名成功");
        } else {
            System.out.println("验证签名失败");
        }
    }

    public static JSONObject doPost(String url, JSONObject json) {
        HttpClient client = HttpClientBuilder.create().build(); // 获取DefaultHttpClient请求
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = client.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
