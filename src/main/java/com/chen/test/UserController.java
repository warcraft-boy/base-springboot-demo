package com.chen.test;


import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.csv.CsvWriteConfig;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/5/18
 **/
@RestController
public class UserController {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void bbq(String name){
        System.out.println(name + "eat bbq");
    }

    @RequestMapping("/index")
    public HttpServletResponse export(HttpServletResponse response) throws Exception {
        //设置分隔符
        CsvWriteConfig csvWriteConfig = new CsvWriteConfig();
        csvWriteConfig.setFieldSeparator('|');
        //创建csv"写对象"
        CsvWriter writer = new CsvWriter(new OutputStreamWriter(response.getOutputStream(), CharsetUtil.CHARSET_GBK), csvWriteConfig);
        //设置response
        response.setContentType("application/csv;charset=gbk");
        response.setHeader("Content-Disposition", "attachment; filename=exported.csv");
        //将数据写入到csv"写对象"中
        writer.write(
                new String[] {"a1", "b1", "c1"},
                new String[] {"a2", "b2", "c2"},
                new String[] {"a3", "b3", "c3"}
        );
        return response;
    }

    @RequestMapping("/index1")
    public HttpServletResponse export1(HttpServletResponse response) throws Exception {
        ExcelWriter writer = ExcelUtil.getWriter();
        //头信息
        writer.writeCellValue(0, 0, "管家协议编号");
//        writer.writeCellValue(1, 1, "10011923000000002");
        writer.merge(0,0, 1, 3, "10011923000000002", false);
        writer.writeCellValue(0, 1, "结算账户户名");
//        writer.writeCellValue(1, 2, "香港创一控股有限公司");
        writer.merge(1,1, 1, 3, "香港创一控股有限公司", false);
        writer.writeCellValue(0, 2, "结算账户账号");
//        writer.writeCellValue(1, 3, "NRA1001192309148601642");
        writer.merge(2,2, 1, 3, "NRA1001192309148601642", false);
        writer.writeCellValue(0, 3, "币种");
//        writer.writeCellValue(1, 4, "美元");
        writer.merge(3,3, 1, 3, "美元", false);
        //设置excel单元格标题
//        writer.merge(3, "大陆美金账户开户信息登记表");
        writer.merge(4,4, 0, 3, "大陆美金账户开户信息登记表", false);
        //设置字体
        Font font = writer.createFont();
        font.setFontHeight((short) 240);
        writer.getStyleSet().setFont(font, true);
        writer.setRowHeight(0, 27);
        writer.setRowHeight(1, 27);
        writer.setRowHeight(2, 27);
        writer.setRowHeight(3, 27);
        writer.setRowHeight(3, 27);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=AmericaAccount.xls");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(out);
        return response;
    }

    @PostMapping("/test/http")
    public String httpTest(@RequestParam HashMap<String, Object> paramMap){
        String s = (String) paramMap.get("t");
        return s;
    }

    @PostMapping("/test/http2")
    public String httpTest2(@RequestBody T t){
//        JSONObject jo = JSON.parseObject(json);
//        String s = jo.getString("t");
        String s = t.getT();
        return s;
    }

    @GetMapping("/test/http3")
    public String httpTest3(){
        System.out.println("121");
        return "121";
    }

    /**
     * 上传文件到本地服务器
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename(); //原始文件名称
        String suffix = originalFilename.contains(".") ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : null; //文件后缀
        String newFileName = System.nanoTime() + "." + suffix;
        String fileDir = "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        File dir = new File("/Users/chenjianwen/myDisk" + fileDir); ///Users/chenjianwen/myDisk这是我本地目录地址，LINUX服务器地址具体使用/data/...
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file.transferTo(new File("/Users/chenjianwen/myDisk" + fileDir + "/" + newFileName));
        return fileDir + "/" + newFileName;
    }
}
