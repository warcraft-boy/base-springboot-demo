package com.chen.test.demo8;

import java.io.*;

/**
 * @ClassName IOTest1
 * @description:
 * @author: chenjianwen
 * @create: 2023/01/03
 **/
public class IOTest1 {
    public static void main(String[] args) throws Exception {
//        readFile1();
//        readFile2();
//        writeFile1();
//        writeFile2();
        copyFile1("/Users/chenjianwen/myDisk/file/CustomKeysSample.txt", "/Users/chenjianwen/myDisk/");
    }


    public static void readFile1() throws IOException {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream("/Users/chenjianwen/myDisk/file/CustomKeysSample.txt");
            int i = 0;
            while ((i = fin.read()) != -1){
                System.out.print((char)i);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fin.close();
        }
    }

    public static void readFile2() throws IOException {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream("/Users/chenjianwen/myDisk/file/CustomKeysSample.txt");
            int i = 0;
            byte[] b = new byte[1024];
            while ((i = fin.read(b)) != -1){
                String s = new String(b);
                System.out.print(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fin.close();
        }
    }


    public static void writeFile1() throws IOException {
        FileOutputStream fout = null;
        try {
            
            fout = new FileOutputStream("/Users/chenjianwen/myDisk/aa.txt", true);
            String s = "2.用FileOutputStream写String到文件";
            byte[] bytes = s.getBytes();
            System.out.println("写入完毕");
            fout.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            fout.close();
        }
    }

    public static void writeFile2() throws IOException {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream("/Users/chenjianwen/myDisk/aa.txt", true);
            for(int i = 0; i < 10; i++){
                String s = i + ".用FileOutputStream写String到文件";
                byte[] bytes = s.getBytes();
                fout.write(bytes);
            }
            System.out.println("写入完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            fout.close();
        }
    }

    public static void copyFile1(String srcFile, String destFolder){
        File file = new File(srcFile);
        String destFile = destFolder + file.getName();
        //输入流
        FileInputStream fin = null;
        BufferedInputStream bis = null;
        //输出流
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try {
            fin = new FileInputStream(file);
            bis = new BufferedInputStream(fin);
            fout = new FileOutputStream(destFile);
            bout = new BufferedOutputStream(fout);
            int i = 0;
            byte[] b = new byte[1024*1024];
            while((i = bis.read(b)) != -1){
                bout.write(b, 0, i);
            }
            System.out.println("拷贝完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
