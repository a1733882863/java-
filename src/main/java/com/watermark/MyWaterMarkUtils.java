package com.watermark;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MyWaterMarkUtils {



    /**
     * 获取文本长度。汉字为1:1，英文和数字为2:1
     */
    private static int getTextLength(String text) {
        int length = text.length();
        for (int i = 0; i < text.length(); i++) {
            String s = String.valueOf(text.charAt(i));
            if (s.getBytes().length > 1) {
                length++;
            }
        }
        length = length % 2 == 0 ? length / 2 : length / 2 + 1;
        return length;
    }


    public static void main(String[] args) throws Exception {
        //需要的参数 : 旋转角度
        Integer degree = -30;
        //水印大小,负数为缩小,正数为扩大
        Integer logoSize = -1;
        //文字的字体 : 字体 和颜色
        Font font = new Font("宋体", Font.PLAIN, 20);
        Color color = new Color(213, 52, 24);
        //logo间距
        Integer xDistance = 80;
        Integer yDistance = 80;
        // 水印透明度
        float alpha = 1f;
        //水印坐标
        Integer x = 55;
        Integer y = 300;
        // 源图片地址
        String srcImgPath = "E:\\images\\b1.jpg";
        // 待存储的地址
        String tarImgPath = "E:\\images\\b2.jpg";
        //Log 的地址
        String logImgPath = "E:\\images\\c.jpg";
        // 1.添加图标水印
        pressImage(alpha,srcImgPath,tarImgPath,logImgPath,x, y, degree, xDistance, yDistance, font, color, logoSize);

    }

    private static void pressImage(float alpha,String srcImgPath, String tarImgPath, String logImgPath, Integer x, Integer y, Integer degree, Integer xDistance, Integer yDistance, Font font, Color color,Integer logoSize) throws Exception {

        //目标文件
        InputStream inputStreamByUrl = OssFileUtil.getInputStreamByUrl("https://rzico.oss-cn-shenzhen.aliyuncs.com/dinnerBar/min/yyzz11.png");
        Image srcImg = ImageIO.read(inputStreamByUrl);
        int srcImgWidth = srcImg.getWidth(null);
        int srcImgHeight = srcImg.getHeight(null);
        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufImg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);// 设置对线段的锯齿状边缘处理
        g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

        //水印文件
        InputStream markInputStreamByUrl = OssFileUtil.getInputStreamByUrl("https://rzico.oss-cn-shenzhen.aliyuncs.com/dinnerBar/min/logo.png");
        Image markImg = ImageIO.read(markInputStreamByUrl);
        int logoImgWidthMark = markImg.getWidth(null);
        int logoImgHeightMark = markImg.getHeight(null);

        //画上水印
        if (null != degree) {
            g.rotate(Math.toRadians(degree), (double) bufImg.getWidth() / 2, (double) bufImg.getHeight() / 2);
        }
        //g.setColor(color); // 根据图片的背景设置水印颜色
        //g.setFont(font); // 设置字体
        //g.setFont(new Font("宋体", Font.PLAIN, 20));
        //g.setFont(new Font("宋体", Font.PLAIN, srcImg.getWidth(null)/300*15));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));  // 设置水印文字透明度
        //设置水印大小
        int markWidth =0;
        int markHeight = 0;
        if (logoSize < 0 ) {
            markWidth = logoImgWidthMark >> -logoSize;// logo长度
            markHeight = logoImgHeightMark >> -logoSize;// logo高度
        } else{
            markWidth = logoImgWidthMark << logoSize;// logo长度
            markHeight = logoImgHeightMark << logoSize;// logo高度
        }

        //循环添加水印
        while (x < srcImgWidth * 1.5) {
            y = -srcImgHeight / 2;
            while (y < srcImgHeight * 1.5) {
                g.drawImage(markImg, x, y, markWidth, markHeight, null);
                y += markHeight + yDistance;
            }
            x += markWidth + xDistance;
        }

        //关闭画笔
        g.dispose();

        // 输出图片
//        FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
//        String formatName = srcImgPath.substring(srcImgPath.indexOf(".") + 1, srcImgPath.length());
//        ImageIO.write(bufImg, formatName, outImgStream);
//        System.out.println("添加水印完成");
//        outImgStream.flush();
//        outImgStream.close();

        //上传到Oss


        String s = OssFileUtil.uploadAliyun2(null, "testmarkdir/markimg.png", OssFileUtil.getImageStream(bufImg), "utf-8");
        System.out.println(s);
    }
}
