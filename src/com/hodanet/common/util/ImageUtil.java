package com.hodanet.common.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageUtil {

    public static void createImage(int width, int height, String imagePath, String formatName) throws IOException {
        File myimage = new File(imagePath);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        image.flush();
        // BufferedImage image = ImageIO.read(new URL(
        // "http://hiphotos.baidu.com/huyangdiy/pic/item/de8ad3f9e49e344f08244dcc.jpg"));
        ImageIO.write(image, formatName, myimage);
    }

    public static void createTempImage(String imagePath, String content) throws IOException {
        File myimage = new File(imagePath);
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.getGraphics();
        g.setColor(new Color(117, 174, 243));
        g.fillRect(0, 0, 100, 100);
        g.setColor(Color.BLACK);
        g.drawString(content, 30, 50);
        image.flush();
        ImageIO.write(image, "jpeg", myimage);
    }

    /**
     * ͼ
     * 
     * @param src :ԭͼƬļ·
     * @param dist :ɵͼƬļ·
     * @param width :ͼƬĿ
     * @param height :ͼƬĸ߶
     * @throws IOException
     */
    public static void zoomImg(String src, String dist, int width, int height) throws IOException {
        Image image = ImageIO.read(new File(src));
        BufferedImage tag = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        ImageIO.write(tag, "jpeg", new File(dist));
    }

    public static Rectangle getImageRectangle(String src) throws IOException {
        Image image = ImageIO.read(new File(src));
        return new Rectangle(image.getWidth(null), image.getHeight(null));
    }

    public static Rectangle getImageRectangle(InputStream inputStream) throws IOException {
        Image image = ImageIO.read(inputStream);
        return new Rectangle(image.getWidth(null), image.getHeight(null));
    }

    /**
     * radian only 0 90 180 270
     * 
     * @param bufferedImage
     * @param radian
     * @return
     * @throws IOException
     */
    public static BufferedImage rotation(BufferedImage bufferedImage, int radian) throws IOException {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        BufferedImage dstImage = null;
        AffineTransform affineTransform = new AffineTransform();

        if (radian == 180) {
            affineTransform.translate(width, height);
            dstImage = new BufferedImage(width, height, bufferedImage.getType());
        } else if (radian == 90) {
            affineTransform.translate(height, 0);
            dstImage = new BufferedImage(height, width, bufferedImage.getType());
        } else if (radian == 270) {
            affineTransform.translate(0, width);
            dstImage = new BufferedImage(height, width, bufferedImage.getType());
        } else if (radian == 0) {
            return bufferedImage;
        }

        affineTransform.rotate(Math.toRadians(radian));
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        return affineTransformOp.filter(bufferedImage, dstImage);
    }

    public static void main(String[] args) throws IOException {
        // zoomImg("E:/icon.png", "E:/icon2.png", 72, 72);
        // BufferedImage bufferedImage = ImageIO.read(new File("F:/pptTest/left.jpg"));
        // BufferedImage b = rotation(bufferedImage, 90);
        // ImageIO.write(b, "jpg", new File("F:/pptTest/out.jpg"));
        // zoomImg("F://ppt/template/simple/slide_1.png",
        // "F://ppt/template/simple/slide_1_zoom.png",
        // 400, 300);
        // createTempImage("F:/pptTest/picture.jpg", "picture");
        // ImageUtil.copyImage("F:/pptTest/test.jpg",
        // "F:/pptTest/test/ppt/media/image1.jpeg", "jpeg");
    }
}
