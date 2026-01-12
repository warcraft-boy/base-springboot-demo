package com.chen.test;

import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/7/3
 **/
@Controller
public class TestController {


    @PostMapping("/email/send")
    @ResponseBody
    public Object sendMail(MultipartFile multifile) throws Exception {
        //获取文件名称
        String fileName = multifile.getOriginalFilename();
        byte[] bytes=new byte[(int)multifile.getSize()];
        InputStream is = multifile.getInputStream();
        is.read(bytes);
        //通过Base64讲字节数组转换为字符串
        String fileContent = Base64.getEncoder().encodeToString(bytes);
        //EmailFile这个对象存储文件名称和文件
//        EmailFile ef= new EmailFile();
//        ef.setFileName(fileName);
//        ef.setFileContent(fileContent);
//        emailServiceTemplate.email(EmailSubject.newBuilder().template("1249577988099248130").email("alichen3116@aliyun.com").platform("base-springboot").file(ef).build());
        return null;
    }

    /**
     * 生成PDF到页面预览和下载
     * @param response
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @GetMapping("/create/pdf")
    public HttpServletResponse createPdf(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        // 3.打开文档
        document.open();
        document.addTitle("Title@PDF-Java");// 标题
        document.addAuthor("Author@umiz");// 作者
        document.addSubject("Subject@iText pdf sample");// 主题
        document.addKeywords("Keywords@iTextpdf");// 关键字
        document.addCreator("Creator@umiz`s");// 创建者

        // 超链接
        Anchor anchor = new Anchor("baidu");
        anchor.setReference("www.baidu.com");
        Anchor gotoP = new Anchor("goto");
        gotoP.setReference("#top");
        document.add(anchor);
        document.add(gotoP);
        document.close();

        return response;
    }

    /**
     * 生成PDF到页面预览和下载
     * @param response
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @GetMapping("/create/pdf2")
    public HttpServletResponse createPdf2(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        //建立文档
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        //1.设置页边距
        document.setMargins(30, 30, 100, 100);
        // 3.打开文档
        document.open();
        //设置字体
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font titlefont = new Font(bfChinese, 25, Font.BOLD);
        Font sideTitlefont = new Font(bfChinese, 10, Font.NORMAL);
        Font textfont = new Font(bfChinese, 12, Font.NORMAL);

        //设置段落
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph.add(new Chunk("香港创一控股有限公司", titlefont));
        paragraph.add(new Chunk("   "));
        paragraph.add(new Chunk("电子回单", sideTitlefont));
        paragraph.setSpacingAfter(50f);// 设置段落下空白
        document.add(paragraph);

        Paragraph p1 = new Paragraph();
        p1.setIndentationLeft(76);// 左缩进
        p1.add(new Chunk("付款人户名：", textfont));
        p1.add(new Chunk("      "));
        p1.add(new Chunk("我是付款人", textfont));
        p1.setSpacingAfter(18f);// 设置段落下空白
        document.add(p1);

        Paragraph p2 = new Paragraph();
        p2.setIndentationLeft(76);// 左缩进
        p2.add(new Chunk("收款人户名：", textfont));
        p2.add(new Chunk("      "));
        p2.add(new Chunk("我是收款人", textfont));
        p2.setSpacingAfter(18f);// 设置段落下空白
        document.add(p2);

        Paragraph p3 = new Paragraph();
        p3.setIndentationLeft(76);// 左缩进
        p3.add(new Chunk("收款人账号：", textfont));
        p3.add(new Chunk("      "));
        p3.add(new Chunk("3480293850983402394820394802", textfont));
        p3.setSpacingAfter(18f);// 设置段落下空白
        document.add(p3);

        Paragraph p4 = new Paragraph();
        p4.setIndentationLeft(50);// 左缩进
        p4.add(new Chunk("收款人开户银行：", textfont));
        p4.add(new Chunk("      "));
        p4.add(new Chunk("中国工商银行下沙支行", textfont));
        p4.setSpacingAfter(18f);// 设置段落下空白
        document.add(p4);

        Paragraph p5 = new Paragraph();
        p5.setIndentationLeft(110);// 左缩进
        p5.add(new Chunk("金额：", textfont));
        p5.add(new Chunk("      "));
        p5.add(new Chunk("24232323.38", textfont));
        p5.setSpacingAfter(18f);// 设置段落下空白
        document.add(p5);

        Paragraph p6 = new Paragraph();
        p6.setIndentationLeft(63);// 左缩进
        p6.add(new Chunk("金额（大写）:", textfont));
        p6.add(new Chunk("      "));
        p6.add(new Chunk("贰仟肆佰贰拾叁万贰仟叁佰贰拾叁点叁捌", textfont));
        p6.setSpacingAfter(18f);// 设置段落下空白
        document.add(p6);

        Paragraph p7 = new Paragraph();
        p7.setIndentationLeft(110);// 左缩进
        p7.add(new Chunk("摘要：", textfont));
        p7.add(new Chunk("      "));
        p7.add(new Chunk("提现电子回单", textfont));
        p7.setSpacingAfter(18f);// 设置段落下空白
        document.add(p7);

        Paragraph p8 = new Paragraph();
        p8.setIndentationLeft(77);// 左缩进
        p8.add(new Chunk("交易流水号：", textfont));
        p8.add(new Chunk("      "));
        p8.add(new Chunk("482098402935736834095", textfont));
        p8.setSpacingAfter(18f);// 设置段落下空白
        document.add(p8);

        Paragraph p9 = new Paragraph();
        p9.setIndentationLeft(100);// 左缩进
        p9.add(new Chunk("时间戳：", textfont));
        p9.add(new Chunk("      "));
        p9.add(new Chunk("2021-01-21 12:12:12", textfont));
        p9.setSpacingAfter(50f);// 设置段落下空白
        document.add(p9);

        Paragraph p10 = new Paragraph();
        p10.setIndentationLeft(150);// 左缩进
        p10.add(new Chunk("附言", textfont));
        p10.add(new Chunk("      "));
        p10.add(new Chunk("敷衍", textfont));
        p10.setSpacingAfter(20f);// 设置段落下空白
        document.add(p10);

        //图片
        Image image1 = Image.getInstance("https://images.coralglobal.cn/picture/newuser/cy-seal.png");
        //设置图片位置的x轴和y周
        image1.setAbsolutePosition(400f, 170f);
        //设置图片的宽度和高度
        image1.scaleAbsolute(100, 130);
        //将图片1添加到pdf文件中
        document.add(image1);

        document.close();

        return response;
    }

    /**
     * 生成PDF到页面预览和下载
     * @param response
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @GetMapping("/create/pdf3")
    public HttpServletResponse createPdf3(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        //建立文档
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        //设置页边距
        document.setMargins(10, 10, 100, 100);
        // 打开文档
        document.open();
        //设置字体
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font titlefont = new Font(bfChinese, 25, Font.BOLD);
        Font sideTitlefont = new Font(bfChinese, 10, Font.NORMAL);
        Font textfont = new Font(bfChinese, 12, Font.NORMAL);

        //设置段落
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        paragraph.add(new Chunk("Hong Kong Techone Holdings Limited", titlefont));
        paragraph.add(new Chunk("   "));
        paragraph.add(new Chunk("Electronic receipt", sideTitlefont));
        paragraph.setSpacingAfter(50f);// 设置段落下空白
        document.add(paragraph);

        Paragraph p1 = new Paragraph();
        p1.setIndentationLeft(70);// 左缩进
        p1.add(new Chunk("Payer Account Name： ", textfont));
        p1.add(new Chunk("      "));
        p1.add(new Chunk("我是付款人", textfont));
        p1.setSpacingAfter(18f);// 设置段落下空白
        document.add(p1);

        Paragraph p2 = new Paragraph();
        p2.setIndentationLeft(70);// 左缩进
        p2.add(new Chunk("Payee Account Name： ", textfont));
        p2.add(new Chunk("      "));
        p2.add(new Chunk("我是收款人", textfont));
        p2.setSpacingAfter(18f);// 设置段落下空白
        document.add(p2);

        Paragraph p3 = new Paragraph();
        p3.setIndentationLeft(60);// 左缩进
        p3.add(new Chunk("Payee Account Number： ", textfont));
        p3.add(new Chunk("      "));
        p3.add(new Chunk("3480293850983402394820394802", textfont));
        p3.setSpacingAfter(18f);// 设置段落下空白
        document.add(p3);

        Paragraph p4 = new Paragraph();
        p4.setIndentationLeft(78);// 左缩进
        p4.add(new Chunk("Payee Deposit Bank： ", textfont));
        p4.add(new Chunk("      "));
        p4.add(new Chunk("中国工商银行下沙支行", textfont));
        p4.setSpacingAfter(18f);// 设置段落下空白
        document.add(p4);

        Paragraph p5 = new Paragraph();
        p5.setIndentationLeft(130);// 左缩进
        p5.add(new Chunk("Amount：", textfont));
        p5.add(new Chunk("      "));
        p5.add(new Chunk("24232323.38", textfont));
        p5.setSpacingAfter(18f);// 设置段落下空白
        document.add(p5);

        Paragraph p6 = new Paragraph();
        p6.setIndentationLeft(130);// 左缩进
        p6.add(new Chunk("Abstract：", textfont));
        p6.add(new Chunk("      "));
        p6.add(new Chunk("提现电子回单", textfont));
        p6.setSpacingAfter(18f);// 设置段落下空白
        document.add(p6);

        Paragraph p7 = new Paragraph();
        p7.setIndentationLeft(45);// 左缩进
        p7.add(new Chunk("Transaction Serial Number：", textfont));
        p7.add(new Chunk("      "));
        p7.add(new Chunk("482098402935736834095", textfont));
        p7.setSpacingAfter(18f);// 设置段落下空白
        document.add(p7);

        Paragraph p8 = new Paragraph();
        p8.setIndentationLeft(50);// 左缩进
        p8.add(new Chunk("Transaction Cut-off Time：", textfont));
        p8.add(new Chunk("      "));
        p8.add(new Chunk("2021-01-21 12:12:12", textfont));
        p8.setSpacingAfter(50f);// 设置段落下空白
        document.add(p8);

        Paragraph p9 = new Paragraph();
        p9.setIndentationLeft(150);// 左缩进
        p9.add(new Chunk("The Postscript", textfont));
        p9.add(new Chunk("      "));
        p9.add(new Chunk("敷衍", textfont));
        p9.setSpacingAfter(20f);// 设置段落下空白
        document.add(p9);

        //添加图片
        Image image1 = Image.getInstance("https://images.coralglobal.cn/picture/newuser/cy-seal.png");
        //设置图片位置的x轴和y周
        image1.setAbsolutePosition(400f, 170f);
        //设置图片的宽度和高度
        image1.scaleAbsolute(100, 130);
        //将图片1添加到pdf文件中
        document.add(image1);

        document.close();

        return response;
    }

    @GetMapping("/create/excels")
    @ResponseBody
    public String excelz(){
        T t = new T();
        t.setT("tt");
        t.setV("vv");
        T t1 = new T();
        t1.setT("tt1");
        t1.setV("vv1");
        T t2 = new T();
        t2.setT("tt2");
        t2.setV("vv2");
        List<T> list = new ArrayList<>();
        list.add(t);
        list.add(t1);
        list.add(t2);
        String newFileName = System.nanoTime() + ".xlsx";
        String fileDir = "/" + new SimpleDateFormat("yyyyMMdd").format(new Date());
        File dir = new File("/Users/chenjianwen/myDisk" + fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = fileDir + "/" + newFileName;
        BigExcelWriter writer = ExcelUtil.getBigWriter(new File("/Users/chenjianwen/myDisk" + fileDir + "/" + newFileName));
        writer.setColumnWidth(0, 10);
        writer.merge(1, "记录");
        if(list != null && list.size() > 0){
            writer.addHeaderAlias("t", "流水号");
            writer.addHeaderAlias("v", "流水号2");
            writer.write(list, true);
        }else{
            writer.writeCellValue(0, 1, "流水号");
            writer.writeCellValue(1, 1, "流水号");
        }
        //设置字体
        org.apache.poi.ss.usermodel.Font font = writer.createFont();
        font.setFontHeight((short) 240);
        writer.getStyleSet().setFont(font, true);
        writer.setRowHeight(0, 27);
        writer.setRowHeight(1, 27);
        writer.close();
        return "/Users/chenjianwen/myDisk" + filePath;
    }

    private static final String privateKey = "74d375e7-9f0d-4169-acec-204516e73527";      // 商户私钥
    @RequestMapping(value = "/gateway/callback")
    public String callback(HttpServletRequest request) {
        JSONObject notifyJson = getRequestParams(request);
        Boolean  pass = Sha256SignUtil.verifySign(notifyJson, privateKey, notifyJson.getString("sign"));  // 签名验证
        if (pass) {
            // 支付订单成功
            if ("SUCCESS".equals(notifyJson.getString("status"))) {
                // 成功操作，需要判断该订单状态是否已更改成功，避免订单重复操作
            }
        } else {
            return "verify sign failed";
        }
        return "SUCCESS";
    }

    /**
     * 将请求值装换成JSONObject
     * @param request
     * @return
     */
    public static JSONObject getRequestParams(HttpServletRequest request) {
        Map map = new HashMap();
        Enumeration<String> it = request.getParameterNames();
        while (it.hasMoreElements())
        {
            String name = it.nextElement();
            String value = request.getParameter(name);
            value = (StringUtils.isBlank(value)) || (value.equals("null")) ? "" : value;
            map.put(name, value);
        }
        return JSONObject.parseObject(JSON.toJSONString(map));
    }

    @PostMapping("/getHeader")
    @ResponseBody
    public String getHeader(@RequestHeader("Authorization") String Authorization){
        String s1 = Authorization;
        return null;
    }

    @GetMapping("/tt")
    public void testfff(){
        System.out.println(23);
    }
}
