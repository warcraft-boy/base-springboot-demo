package com.chen.test;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriteConfig;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.chen.constant.UrlConstant;
import com.chen.mysql.master.model.Student;
import com.chen.proxy.jdk.Maotai;
import com.chen.proxy.jdk.Wine;
import com.chen.redis.RedisUtil;
import com.chen.test.demo2.UpperNumberUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.poi.ss.usermodel.Font;
import org.junit.Test;
import org.nustaq.offheap.structs.Templated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description: <br>
 * @Date: Created in 2019/12/12 <br>
 * @Author: chenjianwen
 */
public class TestDemo {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test01() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = "2020-03-26 15:40:30";
        Date date = sdf.parse(strDate);
        System.out.println(date.getTime());
        System.out.println(date);
    }

    @Test
    public void test02(){
        Date date =  new Date(Long.valueOf("1570773484000"));
        System.out.println(date);
    }

    @Test
    public void test03(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssE");
        Date date = new Date();
        String str = sdf.format(date);

        String year = str.substring(0,4);
        String month = str.substring(4,6);
        String day = str.substring(6,8);
        String hour = str.substring(8,10);
        String minute = str.substring(10,12);
        String second = str.substring(12,14);
        String week = str.substring(14);
        System.out.println(str);
        System.out.println(year+"年"+month+"月"+day+"日"+week+hour+"时"+minute+"分"+second+"秒");
    }

    @Test
    public void test04(){
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //jedis.set("lunch","rice");
        System.out.println(jedis.get("dinner"));
        jedis.set("dinner", "nuddle", new SetParams().nx().ex(5));
        //System.out.println(jedis.get("lunch"));
        System.out.println(jedis.get("dinner"));
    }

    @Test
    public void test05(){
//        redisUtil.set("soil", "cloud");
//        System.out.println(redisUtil.get("soil"));
    }

    @Test
    public void test06(){
        Date date = new Date();
        System.out.println(date);
        date.setTime(date.getTime() + 6000);
        System.out.println(date);
    }

    @Test
    public void test07(){
        String strdate = "2020-12-23";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(strdate);
        } catch (ParseException e) {
            System.out.println("日期格式不正确");
        }

    }

    @Test
    public void test08(){
        String regex = "123456";
        System.out.println(regex.matches("123456"));
    }


    /**
     * 正则替换，将指定内容替换到字符串的正则表达式中
     */
    @Test
    public void test10(){
        String text = "dear {...} user,your account {...} has been selected,your balance left {...},congratulations!";
        String[] replaceWord = {"13023459039", "Jevin", "123.33"};
        String regex = "\\{...}";
        for(String s : replaceWord){
            text = text.replaceFirst(regex, s);
        }
        System.out.println(text);
    }

    /**
     * 使用DateTimeFormatter获取时间
     */
    @Test
    public void test11(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("GMT+8")); //GMT+8指的是北京时间
        System.out.println(ldt);
        String time = dtf.format(ldt);
        System.out.println(time);
    }

    @Test
    public void test12(){
        String text = "dear {...} user,your account {...} has been selected,your balance left {...},congratulations!";
        int val = 0;
        int count = 0;
        while(true){
            val = text.indexOf("{...}", val);
            if(val == -1){
                break;
            }else{
                count++;
                val++;
            }

        }
        System.out.println(count);
    }

    @Test
    public void test13(){
        String content = "您的验证码为：{}，请尽快完成操作。";
        content = content.replace("{}", "123");
        System.out.println(content);
    }

    @Test
    public void test14(){
        List<String> list= new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        //Iterator iterator = list.iterator();

        ListIterator<String> iterator = list.listIterator();
        List<String> list2 = new ArrayList<>();
        list2.addAll(list);
        go:while(iterator.hasNext()){
            String value = iterator.next();
            if("c".equals(value)){
                iterator.remove();
                iterator.add("cc");
                break go;
            }
        }
        System.out.println(list);
    }

    @Test
    public void test15(){
        List<String> list= new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        for(String s : list){
            if("c".equals(s)){
                list.remove(s);

            }
        }
    }

    @Test
    public void test16(){
        Map<String,Object> map=new HashMap<>();
        map.put("name","zhangsan");
        map.put("age",14);
        String jsonStr= JSON.toJSONString(map);
        System.out.println(jsonStr);
    }

    @Test
    public void test17(){
        String str = "玖仟肆佰零肆元玖角叁分  ￥9,404.93元";
        int moneyPreIndex = str.indexOf("¥");
        int moneySuffIndex = str.indexOf("元");
        String solvedMoney = str.substring(moneyPreIndex + 1, moneySuffIndex);
        System.out.println(solvedMoney);
        System.out.println(str);
    }

    @Test
    public void test18(){
        String str = "玖仟肆佰零肆元玖角叁分  ￥404.93元";
        int value = str.indexOf(" ");
        String s = str.substring(value + 3, str.length() - 1);
        String ss = s.replaceAll(",", "");
        System.out.println(ss);
    }

    @Test
    public void test19(){
        String idNo = "342923199303193116";
        String yearStr = idNo.substring(6, 10);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String currentStr = sdf.format(new Date());
        System.out.println(yearStr + "====" + currentStr);
        Integer year = Integer.valueOf(yearStr);
        Integer current = Integer.valueOf(currentStr);
        Integer value = current - year;
        System.out.println(value);
    }

    @Test
    public void test20() throws Exception {
        UserController userController = new UserController();
        UserService userService = new UserService();
        Class<? extends UserController> clazz = userController.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        Arrays.asList(fields).stream().forEach(System.out::println);

        Field userServiceField = clazz.getDeclaredField("userService");
        userServiceField.setAccessible(true);

        String name = userServiceField.getName();
        System.out.println(name);

        name = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
        System.out.println(name);

        String setMethodName = "set" + name;
        System.out.println(setMethodName);


        Method method = clazz.getMethod(setMethodName, UserService.class);
        method.invoke(userController, userService);

        userServiceField.set(userController, userService);
        System.out.println(userController.getUserService());
    }

    @Test
    public void test21() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class clazz = UserController.class;
        UserController userController = (UserController) clazz.newInstance();

        Method method = clazz.getMethod("bbq", String.class);
        method.invoke(userController, new String());
    }

    @Test
    public void test22(){
        UserController userController = new UserController();
        UserService userService = new UserService();
        //userController.setUserService(userService);
        System.out.println(userController.getUserService());
    }

    @Test
    public void test(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(FishConfig.class);
        FishConfig fishConfig = ac.getBean("fishConfig", FishConfig.class);
        System.out.println(fishConfig);
        Fish fish = ac.getBean("fish", Fish.class);
        System.out.println(fish);
        fish.swim("黄鱼");
    }

    @Test
    public void test23(){
        String str = "37948735947359-mark:admin";
        String[] strs = str.split("-");
        System.out.println(strs[0]);
        for(int i = 0; i< strs.length; i++){
            System.out.println(strs[i]);
        }
    }

    @Test
    public void test24(){
        boolean b = Validator.isEmail("ChenJevin@163.com");
        System.out.println(b);
    }

    @Test
    public void test25(){
        FileInputStream fin = null;
        try {
            //文件的完整路径有三部分组成：路径名称+分隔符+文件名称；
            //如果指定文件不存在，或者他是一个目录，而不是一个常规文件，抑或因为其他原因无法打开进行读取，则抛出FileNotFoundException;
            //注意：FileInputStream只能读取并显示纯文本文件的内容（也就是能用记事本打开的文件）
            fin = new FileInputStream("/Users/chenjianwen/myDisk/file/craft.txt");

            int i = 0;
            //从文件中读取一个字节以int值返回，当读到文件末尾没有数据时返回-1;
            while((i=fin.read()) != -1){
                //byte->int->char
                System.out.print((char)i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            //流打开之后，必须进行关闭
            try {
                if(fin != null){
                    fin.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test26(){
        AtomicInteger atomicInteger = new AtomicInteger(0);
        int current = atomicInteger.get();
        int next = current >= Integer.MAX_VALUE ? 0 :current + 1;
        boolean b = atomicInteger.compareAndSet(current, next);
        System.out.println(b);
    }

    @Test
    public void test27(){
        BigDecimal b = BigDecimal.ONE.divide(new BigDecimal("4.0145"), 6, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(b);
    }

    @Test
    public void test28(){
        List<Integer> list = Arrays.asList(1, 2, 1, 1, 3);
        Set<Integer> set = new HashSet<>();
        set.addAll(list);
        System.out.println(set);
        list.clear();
        list.addAll(set);
        System.out.println(list);
    }

    @Test
    public void test29(){
        List<Student> list = new ArrayList<>();
        List<String[]> list1 = new ArrayList<>();
        Student s1 = new Student();
        s1.setId(1);
        s1.setName("s1");
        s1.setClassId(1);
        s1.setClassName("c1");
        Student s2 = new Student();
        s2.setId(2);
        s2.setName("s2");
        s2.setClassId(2);
        s2.setClassName("c2");
        list.add(s1);
        list.add(s2);
        for(Student s : list){
            list1.add(new String[]{String.valueOf(s.getId()), String.valueOf(s.getName()), String.valueOf(s.getClassId()), String.valueOf(s.getClassName())});
        }
        CsvWriteConfig csvWriteConfig = new CsvWriteConfig();
        csvWriteConfig.setFieldSeparator('|');
        CsvWriter cw = CsvUtil.getWriter(new File("/Users/chenjianwen/myDisk/file/test.csv"), CharsetUtil.CHARSET_GBK, false, csvWriteConfig);
        cw.write(list1);
    }

    @Test
    public void test30(){
        System.out.println(IdWorker.getIdStr());
    }

    @Test
    public void test31(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2020);
        c.set(Calendar.MONTH, 9);
        int day = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println(day);
    }

    @Test
    public void test32(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        System.out.println(c.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void test33(){
        int year = 2020;
        int month = 9;
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0);
        System.out.println(c.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void test34(){
        Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.YEAR));
    }

    @Test
    public void test35(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, 3);
        System.out.println(c.getTime());
    }

    @Test
    public void test36(){
        BigDecimal b1 = new BigDecimal("123");
        System.out.println(b1.multiply(new BigDecimal("0.25333")));
    }

    /**
     * 生成PDF到本地硬盘
     * @throws FileNotFoundException
     * @throws DocumentException
     */
    @Test
    public void test37() throws FileNotFoundException, DocumentException {
        Document document = new Document(PageSize.A4);
        File file = new File("/Users/chenjianwen/myDisk/PDFDemo.pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

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
        document.add(anchor);

        document.close();

    }

    @Test
    public void test38(){
//        System.out.println(IdWorker.getIdStr());
        System.out.println(UpperNumberUtil.convert("24232320.38"));
//        System.out.println(ChineseYuanUtil.convert("24232323.00"));
    }

    @Test
    public void test39(){
        System.out.println(new Date());
        System.out.println(String.valueOf(new Date()));
    }

    @Test
    public void test40() throws Exception {
        Document document = new Document(PageSize.A4);
        File file = new File("/Users/chenjianwen/myDisk/PDFDemo.pdf");
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));

        // 3.打开文档
        document.open();

        document.add(new Paragraph("Paragraph page"));
        Paragraph info = new Paragraph();
        info.add(new Chunk("China "));
        info.add(new Chunk("chinese"));
        info.add(Chunk.NEWLINE); // 好用的
        info.add(new Phrase("Japan "));
        info.add(new Phrase("japanese"));
        info.setSpacingAfter(10f);// 设置段落下空白
        document.add(info);

        // 段落是比较好用的
        Paragraph tParagraph = new Paragraph("段落是文章中最基本的单位。内容上它具有一个相对完整的意思；在文章中，段具有换行的标。段是由句子或句群组成的，在文章中用于体现作者的思路发展或全篇文章的层次。有的段落只有一个句子，称为独句段，独句段一般是文章的开头段、结尾段、"
                + "过渡段强调段等特殊的段落。多数段落包括不止一个句子或句群，叫多句段。中文段落开头前一般空两个格。");
        tParagraph.setAlignment(Element.ALIGN_JUSTIFIED);// 对齐方式

        tParagraph.setIndentationLeft(12);// 左缩进
        tParagraph.setIndentationRight(12);// 右缩进
        tParagraph.setFirstLineIndent(24);// 首行缩进

        tParagraph.setLeading(20f);// 行间距
        tParagraph.setSpacingBefore(5f);// 设置上空白
        tParagraph.setSpacingAfter(10f);// 设置段落下空白
        document.add(tParagraph);

        // 每个新的段落会另起一行
        tParagraph = new Paragraph("新的段落");
        tParagraph.setAlignment(Element.ALIGN_CENTER);// 居中
        document.add(tParagraph);

        document.close();
    }

    @Test
    public void test41(){
        Lb lb = new Lb();
        lb.getAndIncrement();
    }

    @Test
    public void test42(){
        Pattern AMOUNT_PATTERN1 = Pattern.compile("^(0|[1-9]\\d{0,11})\\.(\\d\\d)$");
        Pattern AMOUNT_PATTERN2 = Pattern.compile("^(0|[1-9]\\d{0,11})$");
        System.out.println(AMOUNT_PATTERN2.matcher("34").find());
    }

    @Test
    public void test43(){
//        List<T> list = new ArrayList<>();
//        list.add(new T("t1"));
//        list.add(new T("t2"));
//        list.add(new T("t3"));
//        String s = list.stream().map(T::getT).collect(Collectors.joining("、"));
//        System.out.println(s); // t1、t2、t3
    }

    @Test
    public void test44(){
        System.out.println(System.getProperty("user.dir")); //能获取到当前项目路径：/Users/chenjianwen/myDisk/workspace/base-springboot-demo
    }

    @Test
    public void test45(){
        String fileName = "adflakj3342.jpg";
        System.out.println(fileName.substring(fileName.lastIndexOf(".") + 1));
    }

    @Test
    public void test46(){
        String a = "aaaa";
        final String b = "aaaa";
        int c = 11;
        int d = 11;
        System.out.println(a + c);
        System.out.println(b + d);
        System.out.println(a == b);
        System.out.println(a + c == b + d);
    }

    @Test
    public void test47() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse("20220601");
        System.out.println(date);
    }

    @Test
    public void test48(){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        String phone = "16166661111";
        Matcher m = p.matcher(phone);
        System.out.println(m.matches());
    }

    @Test
    public void test49(){
        System.out.println(T.class.equals(Object.class));
    }

    @Test
    public void test50(){
        T t = new T();
        t.setT("tt");
        List<T> list = new ArrayList<>();
        list.add(t);
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
            writer.write(list, true);
        }else{
            writer.writeCellValue(0, 1, "流水号");
        }
        //设置字体
        Font font = writer.createFont();
        font.setFontHeight((short) 240);
        writer.getStyleSet().setFont(font, true);
        writer.setRowHeight(0, 27);
        writer.setRowHeight(1, 27);
        writer.close();
        System.out.println("/Users/chenjianwen/myDisk" + fileDir + "/" + newFileName);
    }

    @Test
    public void test51(){
        String data = "https://images1.coralglobal.cn/auth_images/20210622/99205320227127.pdf";
        String suffix = data.substring(data.lastIndexOf(".") + 1);
        System.out.println(suffix);
    }

    @Test
    public void test52(){
        String pinyin = PinyinUtil.getPinyin("成吉思汗");
        System.out.println(pinyin);
        String firstName = pinyin.substring(0, pinyin.indexOf(" "));
        System.out.println(firstName);
        String firstAlphabet =  firstName.substring(0,1);
        String leftAlphabet = firstName.substring(1);
        String surName = firstAlphabet.toUpperCase() + leftAlphabet;
        System.out.println(surName);
        String leftName = pinyin.substring(pinyin.indexOf(" ") + 1).replaceAll(" ", "");
        System.out.println(leftName);
        String firstAlphabet1 =  leftName.substring(0,1);
        String leftAlphabet1 = leftName.substring(1);
        String name = firstAlphabet1.toUpperCase() + leftAlphabet1;
        System.out.println(name);
        String eventualEnName = surName + " " + name;
        System.out.println(eventualEnName);
    }

    @Test
    public void test53() throws BadHanyuPinyinOutputFormatCombination {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        System.out.println(PinyinHelper.toHanYuPinyinString("我是中国人", format, "", true));
    }

    @Test
    public void test54(){
        String addr = "汉东省京州市温泉县南坊镇祁家村四组";
        if(addr.contains("省") && addr.contains("市")){
            String province = addr.substring(0, addr.indexOf("省") + 1);
            String city = addr.substring(addr.indexOf("省") + 1, addr.indexOf("市") + 1);
            System.out.println(province + "+" + city);
            System.out.println(true);
        }else{
            System.out.println(false);
        }
    }

    @Test
    public void test55(){
        String idNo = "342923199303193116";
        String year = idNo.substring(6, 10);
        String month = idNo.substring(10, 12);
        String day = idNo.substring(12, 14);
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        String bornDate = year + "年" + month + "月" + day + "日";
        System.out.println(bornDate);
    }

    @Test
    public void test56(){
        String s = "0000004.12";
        System.out.println(new BigDecimal(s));
        String id = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        System.out.println(id);
    }

    @Test
    public void test57() throws ParseException {
        String s = "20210812";
        Date d = new SimpleDateFormat("yyyyMMdd").parse(s);
        System.out.println(d);
    }

    @Test
    public void test58(){
        Map<String, List<Fish>> map = new HashMap<>();
        List<Fish> list = new ArrayList<>();
        Fish f1 = new Fish();
        f1.setName("机务");
        list.add(f1);
        map.put("f", list);
        System.out.println(map);
        Fish f2 = new Fish();
        f2.setName("发发");
        map.get("f").add(f2);
        System.out.println(map);
    }

    @Test
    public void test59(){
        List<Fish> list = new ArrayList<>();
        Fish f1 = new Fish();
        f1.setName("a");
        Fish f2 = new Fish();
        f2.setName("a");
        Fish f3 = new Fish();
        f3.setName("b");
        list.add(f1);
        list.add(f2);
        list.add(f3);
        Set<String> collect = list.stream().map(Fish::getName).collect(Collectors.toSet());
        System.out.println(collect);
        for(String s : collect){
            List<Fish> collect1 = list.stream().filter(x -> s.equals(x.getName())).collect(Collectors.toList());
            System.out.println(collect1);
        }
    }

//    @Test
//    public void test60(){
//        //读取本地文件
//        cn.hutool.core.io.file.FileReader fileReader = new FileReader("/Users/chenjianwen/myDisk/file/company_file/picture/18be220e05fbb93f3458773e26584be4.jpg");
//        //将文件转换成字节数组
//        byte[] result = fileReader.readBytes();
//        //通过Base64将字节数组转换成字符串
//        String fileContent = Base64.getEncoder().encodeToString(result);
//        System.out.println(fileContent);
//    }

    @Test
    public void test61(){
        Document document = new Document();
        //设置文档页边距
        document.setMargins(0,0,0,0);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("/Users/chenjianwen/myDisk/1.pdf");
            PdfWriter.getInstance(document, fos);
            //打开文档
            document.open();
            //获取图片的宽高
            Image image = Image.getInstance("/Users/chenjianwen/Pictures/emoji/diao.jpg");
            float imageHeight=image.getScaledHeight();
            float imageWidth=image.getScaledWidth();
            //设置页面宽高与图片一致
            Rectangle rectangle = new Rectangle(imageWidth, imageHeight);
            document.setPageSize(rectangle);
            //图片居中
            image.setAlignment(Image.ALIGN_CENTER);
            //新建一页添加图片
            document.newPage();
            document.add(image);
        } catch (Exception ioe) {
            System.out.println(ioe.getMessage());
        } finally {
            //关闭文档
            document.close();
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Test
    public void test62(){
        String chars = "0123456789";
        String code = "";
        for (int i = 0; i < 6; i++) {
            int rand = (int) (Math.random() * 10);
            code += String.valueOf(chars.charAt(rand));
        }
        System.out.println(code);
    }

    @Test
    public void test63(){
        Wine wine = new Maotai();
        System.out.println(wine.getClass());
        System.out.println(wine.getClass().getInterfaces()[0]);
    }

    @Test
    public void test64() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<Maotai> maotaiClass = Maotai.class;
        System.out.println(maotaiClass);
        Constructor<Maotai> declaredConstructor = maotaiClass.getDeclaredConstructor();
        System.out.println(declaredConstructor);
        Maotai maotai = declaredConstructor.newInstance();
        maotai.wine();
        System.out.println(maotai);
    }

    @Test
    public void test65(){
        System.out.println(IdWorker.getIdStr());
    }

//    @Test
//    public void test66(){
//        String str = "51103950-5fd7-401f-9f3c-d6a5a393e239.{\"hello\":\"world\"}.1633971802";
//        byte[] bytes = str.getBytes();
//        String s = Base64.getEncoder().encodeToString(bytes);
//        System.out.println(s);
//    }

    @Test
    public void test67(){
        JSONObject reqJson = new JSONObject();
        reqJson.put("external_id", "3049803ajfa");
        JSONObject jo = new JSONObject();
        jo.put("registered_name", "cg");
        reqJson.put("business", jo);
        reqJson.put("country_iso_code", "IDN");
        System.out.println(reqJson);
    }

    @Test
    public void test68(){
        String f = "/Users/chenjianwen/myDisk/file/useless/打铁弄应急方案.docx";
        System.out.println(f.substring(f.lastIndexOf(".")));
    }

    @Test
    public void test69(){
        String uuid = UUID.fastUUID().toString();
        JSONObject reqJson = new JSONObject();
        reqJson.put("id", UUID.fastUUID().toString());
        System.out.println(reqJson);
        System.out.println(UUID.fastUUID());
        System.out.println(uuid);
    }

    @Test
    public void test70(){
        JSONObject reqJson = new JSONObject();


        JSONObject jo = new JSONObject();
        jo.put("registered_name", "mahuate1ng");
        reqJson.put("business", jo);

        reqJson.put("country_iso_code", "IDN");
        reqJson.put("city", "city");
        reqJson.put("address", "address");
        reqJson.put("postal_code", "postal");
        reqJson.put("external_id", IdWorker.getIdStr());
        System.out.println(reqJson);
    }

    /**
     * 转换标准时间格式
     * @throws Exception
     */
    @Test
    public void test71() throws Exception {
        String date = "2022-05-30T02:05:03.683484Z";
        String tempTime = date.replace("Z", " UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = sdf.parse(tempTime);
        System.out.println(d);
        System.out.println(new Date());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str= sdf1.format(d);
        System.out.println(str);
    }

    @Test
    public void test72(){
        FileReader fileReader = new FileReader("/Users/chenjianwen/myDisk/WechatIMG168.png");
        //将文件转换成字节数组
        byte[] result = fileReader.readBytes();
        System.out.println(result);
    }

    @Test
    public void test73(){
        String s = "https://images1.coralglobal.cn/auth_images/20220622/3272672690099612.docx";
        String substring = s.substring(s.lastIndexOf("/") + 1);
        System.out.println(substring);
    }

    @Test
    public void test74() throws ParseException {
        String s = "2004-07-08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = sdf.parse(s);
        long time1 = parse.getTime();
        System.out.println(time1);
        long time = new Date().getTime();
        System.out.println(time);
        long e = 18*365*24*3600*1000L;
        System.out.println(e);
        long interval = time - time1;
        System.out.println(interval);
        if(interval < e){
            System.out.println("小于18岁");
        }else{
            System.out.println("大于18岁");
        }
    }

    @Test
    public void test75() throws ParseException {
        String str = "342923199303193116";
        String substring = str.substring(6, str.length() - 4);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date parse = sdf.parse(substring);
        System.out.println(parse);
    }

    @Test
    public void test76(){
        String s = "http://test;http://my/kaka;https://sofa";
        String[] split = s.split(";");
        long count = Arrays.stream(split).count();
        System.out.println(count);
    }

    @Test
    public void test77(){
        String s = " fa ff fff ";
//        boolean matches = s.matches("^[a-zA-Z]*");
        boolean matches = s.matches("^[a-zA-Z\\s]*$");
        System.out.println(matches);
    }

    @Test
    public void test78(){
        String s = "94bb16f4-9931-4f10-9c1b-f36b003df01f:2b065b49-bf51-4749-a64b-4d6782e5efa3";
        String s1 = Base64.getEncoder().encodeToString(s.getBytes());
        System.out.println(s1);
    }

    @Test
    public void test79(){
        String s = "7659874730054151 SHOPEE_000000006 1660023928911210901Neighbor Market_SKN_AIRPAY INTERNAT_008301";
        if(s.contains("SHOPEE")){
            System.out.println("shopee");
        }
    }


    @Test
    public void test80(){
        String json = "{\"id\":\"6ad9138b-8d10-4831-b519-5116425f4db9\",\"type\":\"payment\",\"data\":{\"payment_id\":\"c2e80102-060b-412f-9799-51b7b822c0e4\",\"partner_id\":\"b5a7cce6-bff5-41e7-8669-24a2d70d558b\",\"wallet_id\":\"676a972a-0b11-4c9e-a775-b8aa7ad100e4\",\"payment_method_id\":\"051924d1-07b9-4c1e-bb39-fd6c0cf3d00e\",\"payment_method_code\":\"danamon-id\",\"payment_method_provider_code\":\"danamon-id\",\"payment_party_identifier\":{\"bank_account_number\":\"7659929112341107\"},\"external_id\":\"SKN0_20220815000925069361-20220815\",\"payment_reference\":\"7659929112341107 SHOPEE_000000006     1660464010741207001ndqkwryk0_SKN_AIRPAY INTERNAT_0083010\",\"amount\":332200,\"fees\":0,\"status\":\"SUPPORTING-DOCUMENTS-NEEDED\",\"source_country\":\"IDN\",\"source_currency\":\"IDR\",\"has_scheduled_settlement\":false,\"version\":5,\"metadata\":{\"event_id\":\"831259d3-5eb9-468d-8b9f-c4693a049472\",\"request_trace_id\":\"56gf-2dl5p7m\"},\"notification_date\":\"2022-08-15T03:30:02.772139Z\",\"creation_date\":\"2022-08-15T03:30:02.772139Z\",\"update_date\":\"2022-08-15T03:30:05.239674Z\"}}\n";
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject sender = data.getJSONObject("sender");
        if(sender != null){
            String name = sender.getString("name");
            System.out.println(name);
        }
    }


    @Test
    public void test81(){
        String s = "PNG";
        System.out.println(s.toLowerCase());
    }

    @Test
    public void test82(){
        String json =  "{\"id\":\"5c518a40-238a-4f7b-92d1-f6aba75382a9\",\"type\":\"payment\",\"data\":{\"payment_id\":\"cf2967d8-18dc-4169-b38f-29f61b808309\",\"partner_id\":\"b5a7cce6-bff5-41e7-8669-24a2d70d558b\",\"wallet_id\":\"af60623a-2268-48f3-b07d-7ec93fcc64fe\",\"payment_method_id\":\"716c799a-e3ad-4b68-8e1a-66cba2af583e\",\"payment_method_code\":\"virtual-accounts-id\",\"payment_method_provider_code\":\"xfers-id\",\"payment_party_identifier\":{\"bank_account_number\":\"8848095720824116\"},\"external_id\":\"contract_4e783e05aa1d4667b19db671a6660831\",\"payment_reference\":\"SAFECASH0102202208311421059519\",\"amount\":10766,\"fees\":2775,\"status\":\"SUPPORTING-DOCUMENTS-NEEDED\",\"source_country\":\"IDN\",\"source_currency\":\"IDR\",\"has_scheduled_settlement\":true,\"version\":5,\"metadata\":{\"event_id\":\"dfb34a22-e113-46ce-b6a4-41e05f55ac3a\",\"request_trace_id\":\"56gf-2dh8fde\"},\"notification_date\":\"2022-09-13T14:49:03.905977Z\",\"creation_date\":\"2022-09-07T03:39:17Z\",\"update_date\":\"2022-09-13T14:49:06.543043Z\"}}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        JSONObject data = jsonObject.getJSONObject("data");
        String notificationDate = data.getString("notification_date");
        String creationDate = data.getString("creation_date");
        String updateDate = data.getString("update_date");
        System.out.println(notificationDate);
        System.out.println(creationDate);
        System.out.println(updateDate);
    }

    @Test
    public void test83() throws ParseException {
        String time = "2022-09-07T03:39:17Z";
//        String time = "2022-09-13T14:49:06.543043Z";
        String tempTime = time.replace("Z", " UTC");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        Date d = sdf.parse(tempTime);
        System.out.println(d);
    }

    @Test
    public void test84() throws ParseException {
        String s = formatTimeZoneToCST("2022-09-13T14:49:06.543043Z", "");
        System.out.println(s);
        Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
        System.out.println(parse);
    }
    public static String formatTimeZoneToCST(String utcStr, String format) {
        if (StringUtils.isEmpty(utcStr)) {
            return "";
        }
        if (StringUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        String result;
        ZonedDateTime parse = ZonedDateTime.parse(utcStr);
        ZonedDateTime zonedDateTime = parse.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
        result = zonedDateTime.format(DateTimeFormatter.ofPattern(format));
        return result;
    }

    @Test
    public void test85() throws Exception {
        System.out.println(transDate("2022-09-13T14:49:06.543043Z"));
    }
    public Date transDate(String time) throws Exception {
        String format = "yyyy-MM-dd HH:mm:ss";
        ZonedDateTime parse = ZonedDateTime.parse(time);
        ZonedDateTime zonedDateTime = parse.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
        String result = zonedDateTime.format(DateTimeFormatter.ofPattern(format));
        Date d = new SimpleDateFormat(format).parse(result);
        return d;
    }

    @Test
    public void test86(){
        String s = "The Hongkong and Shanghai Banking Corporation Limited|HKG";
        String b = s.substring(0, s.lastIndexOf("|"));
        String c = s.substring(s.lastIndexOf("|")+1);
        System.out.println(b);
        System.out.println(c);
    }

    @Test
    public void test87(){
        String json = "{\"id\":\"1c5a8073-ffae-467e-865d-ffcfd10da212\",\"type\":\"payment\",\"data\":{\"payment_id\":\"b7a21864-3dd4-41d6-a459-9d2ec8d67da2\",\"partner_id\":\"b5a7cce6-bff5-41e7-8669-24a2d70d558b\",\"wallet_id\":\"f2f9da20-ffaf-435a-bed1-f11b80d85a15\",\"payment_method_id\":\"716c799a-e3ad-4b68-8e1a-66cba2af583e\",\"payment_method_code\":\"virtual-accounts-id\",\"payment_method_provider_code\":\"xfers-id\",\"payment_party_identifier\":{\"bank_account_number\":\"8848095751645420\"},\"external_id\":\"contract_4e741835ba544d779e4f2f39bb1290cd\",\"payment_reference\":\"SAFECASH0102202302031005085240\",\"amount\":50000000,\"fees\":2775,\"status\":\"SUPPORTING-DOCUMENTS-NEEDED\",\"source_country\":\"IDN\",\"source_currency\":\"IDR\",\"has_scheduled_settlement\":true,\"version\":5,\"metadata\":{\"event_id\":\"abf3ca58-f742-4c9d-bc84-344f5fe6a989\",\"request_trace_id\":\"56gf-2ddvj09\"},\"notification_date\":\"2023-02-07T07:37:10.659109Z\",\"creation_date\":\"2023-02-06T03:14:05Z\",\"update_date\":\"2023-02-07T07:37:15.140226Z\"}}";
        JSONObject jsonObject = JSONObject.parseObject(json);
        String id = jsonObject.getString("id");
        String type = jsonObject.getString("type");
        String name = null;
        String hasScheduledSettlement = null;
        String version = null;
        JSONObject data = jsonObject.getJSONObject("data");
        String paymentId = data.getString("payment_id");
        String partnerId = data.getString("partner_id");
        String walletId = data.getString("wallet_id");
        String paymentMethodId = data.getString("payment_method_id");
        String paymentMethodCode = data.getString("payment_method_code");
        JSONObject paymentPartyIdentifier = data.getJSONObject("payment_party_identifier");
        String bankAccountNumber = paymentPartyIdentifier.getString("bank_account_number");
        String externalId = data.getString("external_id");
        String paymentReference = data.getString("payment_reference");
        BigDecimal amount = data.getBigDecimal("amount");
        BigDecimal fees = data.getBigDecimal("fees");
        String status = data.getString("status");
        String sourceCountry = data.getString("source_country");
        String sourceCurrency = data.getString("source_currency");
        JSONObject sender = data.getJSONObject("sender");
        if(sender != null){
            name = sender.getString("name");
        }
        String settlementDate = data.getString("settlement_date");
        Boolean hasScheduledSettlementBoolean = data.getBoolean("has_scheduled_settlement");
        if(hasScheduledSettlementBoolean != null){
            hasScheduledSettlement = hasScheduledSettlementBoolean.toString();
        }
        Integer versionInteger = data.getInteger("version");
        if(versionInteger != null){
            version = versionInteger.toString();
        }
        JSONObject metadata = data.getJSONObject("metadata");
        String eventId = metadata.getString("event_id");
        String requestTraceId = metadata.getString("request_trace_id");
        String notificationDate = data.getString("notification_date");
        String creationDate = data.getString("creation_date");
        String updateDate = data.getString("update_date");
        System.out.println("123");
    }
}

