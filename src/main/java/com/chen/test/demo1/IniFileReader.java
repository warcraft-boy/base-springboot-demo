package com.chen.test.demo1;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2020/5/21
 **/
public class IniFileReader {
    public static String FILE_NAME = "yootk.ini";
    public static String BASE_DIR = System.getProperty("user.dir"); //该程序工作区，为/Users/chenjianwen/myDisk/workspace/base-springboot-demo
    public static String SUB_PATH = "src/main/resources/" + FILE_NAME;
    public Map<String, Object> iocMap = new LinkedHashMap<>();
    private String flag = null;
    public IniFileReader(){
        readIni();
    }
    public void readIni(){
//        System.getProperties().list(System.out); //得到该计算机信息
//        System.out.println(BASE_DIR);
        File file = new File(BASE_DIR, SUB_PATH);
//        System.out.println(file);
//        System.out.println(file.isFile());
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                String config = scanner.next();
                if(config.matches("\\[.+\\]")){
                    System.out.println(config);
                    if("[ioc]".equalsIgnoreCase(config)){
                        flag = "[ioc]";
                    }
                }else {
                    this.readMapObject(config);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readMapObject(String config){
        switch (this.flag){
            case "[ioc]":{
                String result[] = config.split("="); //按等号进行拆分
                try {
                    this.iocMap.put(result[0], Class.forName(result[1]).newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public Map<String, Object> getIocMap(){
        return iocMap;
    }
}
