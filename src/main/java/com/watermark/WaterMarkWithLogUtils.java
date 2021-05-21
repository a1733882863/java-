package com.watermark;

import com.mysql.jdbc.TimeUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WaterMarkWithLogUtils {

    /**
     * 将指定图标(png图片)印刷到指定图片上
     * <p>
     * 1.图标图片格式:png
     * 2.坐标轴:
     *    x轴决定左右位置
     *    y轴决定上下位置
     * 3.坐标位置
     *    x值越大距离右越近，反之,x值越小距离左越近;
     *    y值越大距离越往下，反之,y值越小距离越往上
     * </p>
     *
     * @param pressImg  水印图片
     * @param targetImg 源图片路径的目标文件
     * @param x         x坐标
     * @param y         y坐标
     */
    public final static void pressImage(String pressImg, String targetImg, int x, int y) {
        try {
            // 目标文件
            File imageFile = new File(targetImg);
            Image src = ImageIO.read(imageFile);

            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            // 水印文件
            File waterMarkImage = new File(pressImg);
            Image markImage = ImageIO.read(waterMarkImage);
            int weightMarkImage= markImage.getWidth(null);
            int heightMarkImage = markImage.getHeight(null);

            g.drawImage(markImage, x, y, weightMarkImage, heightMarkImage, null);

            // 水印结束
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印文字水印图片
     * <p>
     * 1.图标图片格式:png
     * 2.坐标轴:
     *    x轴决定左右位置
     *    y轴决定上下位置
     * 3.坐标位置
     *    x值越大距离右越近，反之,x值越小距离左越近;
     *    y值越大距离越往下，反之,y值越小距离越往上
     * </p>
     *
     * @param pressText 文字
     * @param targetImg 目标图片
     * @param fontName  字体名
     * @param fontStyle 字体样式
     * @param color     字体颜色
     * @param fontSize  字体大小
     * @param x         偏移量
     * @param y         偏移量
     */
    public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, Color color,
                                 int fontSize, int x, int y) {
        try {
            File imageFile = new File(targetImg);
            Image src = ImageIO.read(imageFile);
            int weidth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(weidth, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, weidth, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.drawString(pressText, x, y);
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        String waterMarkImgPath = "E:\\images\\c.png";
        String srcImgPath = "E:\\images\\b1.png";

        System.out.println("水印图标路径:" + waterMarkImgPath);
        System.out.println("图片路径:" + srcImgPath);

        // 1.添加图标水印
        pressImage(waterMarkImgPath, srcImgPath, 33, 33);
        System.out.println("--------------------添加图标水印 执行完成！--------------------");

        String operator = "";
        String date = "";
        String time = "logo";

        // 2.添加文字水印
        pressText(operator, srcImgPath, "宋体", 0, Color.BLUE, 30, 540, 600);
        pressText(date, srcImgPath, "宋体", 0, Color.BLUE, 30, 540, 650);
        pressText(time, srcImgPath, "宋体", 0, Color.BLUE, 30, 540, 700);

        System.out.println("--------------------添加文字水印 执行完成！--------------------");
    }

    /**
     *  特别说明:
     *  如上面的效果展示的那样，要想将水印打印到图片指定位置，进行动态设置的话，需要：</p>
     * 　第一，原图片大小；</p>
     * 　第二，水印大小；</p>
     * 　满足这两个条件才能将水印打印到图片的指定位置，也就是可以进行动态位移。&nbsp;</p>
     * 　其中，图片水印可以进行动态设置，而文字水印则实现不了，因为我们无法获取文字水印的大小。</p>
     * 　调试水印输出位置也是个细致活呀。</p>
     * 另外，水印的偏移量一般情况下均为正值，图片左上角为起点0,0，不同于数学上的Y轴，向下偏移用正值表示。
     */
}