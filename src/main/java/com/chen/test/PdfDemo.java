package com.chen.test;

import com.itextpdf.text.Document;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

/**
 * @Description:
 * @Author chenjianwen
 * @Date 2021/2/24
 **/
public class PdfDemo {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String path = "/Users/chenjianwen/myDisk/PDFDemo.pdf";
        String outPath = path.replaceAll(".pdf", "_ok.pdf");
        one(path, outPath);
        path = outPath;
        outPath = path.replaceAll(".pdf", "_ok.pdf");
        two(path, outPath);
        //连续拼再一个版里面 先手动算算有几拼
        path = outPath;
        outPath = path.replaceAll(".pdf", "_ok.pdf");
        three(path, outPath);
        long end = System.currentTimeMillis();
        System.out.println("运行完毕用时："+(end-start)+"毫秒");
    }

    //连续拼版
    public static void three(String path, String outPath) {
        try {
            PdfReader reader = new PdfReader(path);
            float width = reader.getCropBox(1).getWidth();
            float height = reader.getCropBox(1).getHeight();

            float col = 4;
            float row = 4;
            float pt = 72f/25.4f;
            float jiaoxian = 3*pt;
            float chuxue = 2*pt;

            float pageWidth = width * col + jiaoxian + chuxue + jiaoxian + chuxue;
            float pageHeight = height * row + jiaoxian + chuxue + jiaoxian + chuxue;

            Document document = new Document(new Rectangle(pageWidth, pageHeight));//新建一个文档并且设置页面大小
            FileOutputStream outputStream = new FileOutputStream(outPath);//新建一个pdf文档
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);//把新建的pdf 赋值给 document
            writer.setPdfVersion(PdfWriter.VERSION_1_5);
            document.open();//打开 document文档
            PdfContentByte cb = writer.getDirectContent();
            //拼完后的总页数
            int impositionPageCount = (int)(reader.getNumberOfPages()/(col*row))+1;
            int currIndex = 1;
            for (int i = 0; i < impositionPageCount; i++) {
                for (int c = 0; c < col; c++) {
                    for (int r = 0; r < row; r++) {
                        //超过总页数就不运行了
                        if(currIndex>reader.getNumberOfPages()){
                            break;
                        }
                        //每16P 新建一个页面
                        if (currIndex % (col * row) == 1) {
                            document.newPage();
//                            脚线添加一下
                            //上边和下面
                            for(int m=0;m<col+1;m++){
                                showLine(cb,0.1f,jiaoxian+chuxue+m*width,0,jiaoxian+chuxue+m*width,jiaoxian);
                                showLine(cb,0.1f,jiaoxian+chuxue+m*width,pageHeight,jiaoxian+chuxue+m*width,pageHeight-jiaoxian);
                            }
                            for(int n=0;n<row+1;n++){
                                showLine(cb,0.1f,0,jiaoxian+chuxue+n*height,jiaoxian,jiaoxian+chuxue+n*height);
                                showLine(cb,0.1f,pageWidth,jiaoxian+chuxue+n*height,pageWidth-jiaoxian,jiaoxian+chuxue+n*height);
                            }
                        }
                        PdfImportedPage importedPage = writer.getImportedPage(reader, currIndex);
                        cb.addTemplate(importedPage,jiaoxian+chuxue+c*width,jiaoxian+chuxue+r*height);
                        currIndex++;
                    }
                }


            }
            outputStream.flush();//关闭文件
            document.close();//关闭文件
            outputStream.close();//关闭文件
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    //调整文件大小
    public static void two(String path, String outPath) {

        try {
            float pt = 72.0f / 25.4f;
            PdfReader reader = new PdfReader(path);
            float pageWidth = 70 * pt;
            float pageHeight = 105 * pt;
            Document document = new Document(new Rectangle(pageWidth, pageHeight));//新建一个文档并且设置页面大小
            FileOutputStream outputStream = new FileOutputStream(outPath);//新建一个pdf文档
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);//把新建的pdf 赋值给 document
            writer.setPdfVersion(PdfWriter.VERSION_1_5);
            document.open();//打开 document文档
            PdfContentByte cb = writer.getDirectContent();
            int k = 0;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                PdfImportedPage importedPage = writer.getImportedPage(reader, i);
                float width = importedPage.getWidth();
                float height = importedPage.getHeight();
                float suoxiao = pageWidth / width;
                float realHeight = height * suoxiao;
                cb.addTemplate(importedPage, suoxiao, 0, 0, suoxiao, 0, (pageHeight - realHeight) / 2);
            }
            outputStream.flush();//关闭文件
            document.close();//关闭文件
            outputStream.close();//关闭文件
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //把文件 放在正确的位置
    public static void one(String path, String outPath) {
        try {
            float pt = 72.0f / 25.4f;
            PdfReader reader = new PdfReader(path);
            Document document = new Document(new Rectangle(105 * pt, 144.215f * pt));//新建一个文档并且设置页面大小
            FileOutputStream outputStream = new FileOutputStream(outPath);//新建一个pdf文档
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);//把新建的pdf 赋值给 document
            writer.setPdfVersion(PdfWriter.VERSION_1_5);
            document.open();//打开 document文档
            PdfContentByte cb = writer.getDirectContent();
            int k = 0;
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                PdfImportedPage importedPage = writer.getImportedPage(reader, i);
                cb.addTemplate(importedPage, -4.67f * pt, -130.12f * pt);
            }
            outputStream.flush();//关闭文件
            document.close();//关闭文件
            outputStream.close();//关闭文件
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    //添加脚线
    public static void showLine(PdfContentByte cb, float lineWidth, float fromX, float fromY, float toX, float toY) {
        float pt = 72f/25.4f;
        cb.setLineWidth(lineWidth * pt);
        cb.setLineDash(3, 0, 0);
        cb.moveTo(fromX, fromY);
        cb.lineTo(toX, toY);
        cb.stroke();
        cb.closePath();
    }
}
