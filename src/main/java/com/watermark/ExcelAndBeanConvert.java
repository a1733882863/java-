package com.watermark;

import com.watermark.domain.ExportShopInfoVo;
import com.watermark.utils.ExcelUtil;
import com.watermark.utils.OssFileUtil;
import com.watermark.utils.SimpleImageUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.util.List;

public class ExcelAndBeanConvert {

    public static void main(String[] args) throws Exception {
        //参数里的5表示有效行数从第5行开始
        List<ExportShopInfoVo> studentInfos = ExcelUtil.parseFromExcel("C:\\Users\\xz\\Desktop\\shop.xls", 2,
                ExportShopInfoVo.class);
        for (int i = 0; i < studentInfos.size(); i++) {
            ExportShopInfoVo exportShopInfoVo = studentInfos.get(i);
            //根据bean赋值操作
            try {
                assignBeanFileValue(exportShopInfoVo);
            }catch(Exception e) {

            }finally {

            }


        }
        ExcelUtil.writeExcelWithTitle(studentInfos,"C:\\Users\\xz\\Desktop\\shopnew.xls");
    }

    private static void assignBeanFileValue(ExportShopInfoVo exportShopInfoVo) throws Exception {

        //System.err.println(studentInfos.get(i).toString());
        //开始查看大小并赋值
        //assignValue(studentInfos.get(i));
        InputStream inputStreamByUrl1 = OssFileUtil.getInputStreamByUrl(exportShopInfoVo.getFood());
        //Image srcImg = ImageIO.read(inputStreamByUrl);
        getFile(inputStreamByUrl1,"C:\\Users\\xz\\Desktop\\shop1.xls");
        File picture = new File("C:\\Users\\xz\\Desktop\\shop1.xls");
        BigDecimal len = new BigDecimal(Long.valueOf(picture.length()));
        BigDecimal divide = len.divide(new BigDecimal("1000000.0"));//1024*1024
        BigDecimal setScale = divide.setScale(6,BigDecimal.ROUND_HALF_DOWN);
        exportShopInfoVo.setSizeFood1(setScale.toString());
        System.out.println(setScale + "MB");


        InputStream inputStreamByUrl2 = OssFileUtil.getInputStreamByUrl(exportShopInfoVo.getFoodMark());
        //Image srcImg = ImageIO.read(inputStreamByUrl);
        getFile(inputStreamByUrl2,"C:\\Users\\xz\\Desktop\\shop1.xls");
        File picture2 = new File("C:\\Users\\xz\\Desktop\\shop1.xls");
        BigDecimal len2 = new BigDecimal(Long.valueOf(picture2.length()));
        BigDecimal divide2 = len2.divide(new BigDecimal("1000000.0"));//1024*1024
        BigDecimal setScale2 = divide2.setScale(6,BigDecimal.ROUND_HALF_DOWN);
        exportShopInfoVo.setSizeFood2(setScale2.toString());
        System.out.println(setScale2 + "MB");


        InputStream inputStreamByUrl3 = OssFileUtil.getInputStreamByUrl(exportShopInfoVo.getLicense());
        //Image srcImg = ImageIO.read(inputStreamByUrl);
        getFile(inputStreamByUrl3,"C:\\Users\\xz\\Desktop\\shop1.xls");
        File picture3 = new File("C:\\Users\\xz\\Desktop\\shop1.xls");
        BigDecimal len3 = new BigDecimal(Long.valueOf(picture3.length()));
        BigDecimal divide3 = len3.divide(new BigDecimal("1000000.0"));//1024*1024
        BigDecimal setScale3 = divide3.setScale(6,BigDecimal.ROUND_HALF_DOWN);
        exportShopInfoVo.setSizeLicense1(setScale3.toString());
        System.out.println(setScale3 + "MB");


        InputStream inputStreamByUrl4 = OssFileUtil.getInputStreamByUrl(exportShopInfoVo.getLicenseMark());
        //Image srcImg = ImageIO.read(inputStreamByUrl);
        getFile(inputStreamByUrl4,"C:\\Users\\xz\\Desktop\\shop1.xls");
        File picture4 = new File("C:\\Users\\xz\\Desktop\\shop1.xls");
        BigDecimal len4 = new BigDecimal(Long.valueOf(picture4.length()));
        BigDecimal divide4 = len4.divide(new BigDecimal("1000000.0"));//1024*1024
        BigDecimal setScale4 = divide4.setScale(6,BigDecimal.ROUND_HALF_DOWN);
        exportShopInfoVo.setSizeLicense2(setScale4.toString());
        System.out.println(setScale4 + "MB");


    }

    private static void assignValue(ExportShopInfoVo exportShopInfoVo) throws Exception {
        //目标文件
        InputStream inputStreamByUrl = OssFileUtil.getInputStreamByUrl("https://rzico.oss-cn-shenzhen.aliyuncs.com/dinnerBar/min/yyzz11.png");
        //Image srcImg = ImageIO.read(inputStreamByUrl);
        //SimpleImageUtil imageInfo = new SimpleImageUtil(inputStreamByUrl);
        //System.out.println(imageInfo);
    }

    public static void getFile(InputStream is,String fileName) throws IOException{
        BufferedInputStream in=null;
        BufferedOutputStream out=null;
        in=new BufferedInputStream(is);
        out=new BufferedOutputStream(new FileOutputStream(fileName));
        int len=-1;
        byte[] b=new byte[1024];
        while((len=in.read(b))!=-1){
            out.write(b,0,len);
        }
        in.close();
        out.close();
    }

}


