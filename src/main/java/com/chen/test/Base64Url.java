package com.chen.test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.hutool.core.io.IoUtil;
import sun.misc.BASE64Encoder;

/**
 * @Description: 将网页服务器图片转换为base64字符串
 * @Author chenjianwen
 * @Date 2021/5/10
 **/
public class Base64Url {

    /**
     * 在线图片转换成base64字符串
     *
     * @param imgURL 图片线上路径
     * @return
     * @author ZHANGJL
     * @dateTime 2018-02-23 14:43:18
     */
    public static String ImageToBase64ByOnline(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray());
    }

    public static void main(String[] args) {
        String url = "https://images1.coralglobal.cn/auth_images/20210510/1620611703832.jpeg";
        String ste = Base64Url.ImageToBase64ByOnline(url);
        System.out.println(ste);
    }

    /**
     *
     * @param fileUrl 网络文件，如 https://images1.coralglobal.cn/20180410/5763452865647252.jpg 这样
     * @param localPath 本地文件夹，如我的是mac系统 "/Users/chenjianwen/file"
     * @return
     */
    public static File urlToFile(String fileUrl, String localPath) throws Exception {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        //获取该链接的字节数组
        URL url =new URL(fileUrl);
        InputStream inputStream = url.openStream();
        byte[] res = IoUtil.readBytes(inputStream);
        //将文件字节数组写到一个临时文件中
        File dir = new File(localPath);
        if(!dir.exists()){
            dir.mkdir();
        }
        File file = new File(localPath + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bos.write(res);
        return file;
    }
}
