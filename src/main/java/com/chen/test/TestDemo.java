package com.chen.test;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.chen.rabbitmq.Book;
import com.chen.redis.RedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.mapping.TextScore;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
import sun.security.x509.NameConstraintsExtension;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Test
    public void test09(){
        Book book = new Book();
        if(!StringUtils.isEmpty(book.getName())){
            System.out.println(book);
        }else{
            System.out.println(3);
        }
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
        Fish fish = ac.getBean("fish", Fish.class);
        fish.swim("黄鱼");
    }
}
