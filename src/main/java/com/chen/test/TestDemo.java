package com.chen.test;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.text.csv.CsvConfig;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriteConfig;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.chen.mysql.master.model.Student;
import com.chen.rabbitmq.Book;
import com.chen.redis.RedisUtil;
import com.chen.test.demo2.UpperNumberUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.val;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.mapping.TextScore;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import sun.security.x509.NameConstraintsExtension;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        System.out.println(IdWorker.getIdStr());
//        System.out.println(UpperNumberUtil.convert("24232320.34"));
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
}
