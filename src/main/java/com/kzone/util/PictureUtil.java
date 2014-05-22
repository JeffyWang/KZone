package com.kzone.util;

import com.kzone.bo.Picture;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.concurrent.Callable;

/**
* Created by Jeffy on 2014/5/21 0021.
*/
public class PictureUtil implements Callable<InputStream> {
    private String pictureName;
    private double ratio;
    private int width;
    private int height;
    private String contentType;

    public PictureUtil(String pictureName, double ratio, int width, int height, String contentType) {
        this.pictureName = pictureName;
        this.ratio = ratio;
        this.width = width;
        this.height = height;
        this.contentType = contentType;
    }

    public static InputStream compressPicture(String pictureName, double ratio, int width, int height, String contentType) throws IOException {
        File picture = new File(Thread.currentThread().getContextClassLoader().getResource("picture").getPath() + pictureName + "." + contentType);
        InputStream inputStream = new FileInputStream(picture);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("read input stream of the picture error");
        }
        Image image = bufferedImage.getScaledInstance(width, height, bufferedImage.SCALE_SMOOTH);
        if ((bufferedImage.getHeight() > width) || (bufferedImage.getWidth() > height)) {
            if (bufferedImage.getHeight() > bufferedImage.getWidth())
                ratio = (double)width / bufferedImage.getHeight();
            else
                ratio = (double)height / bufferedImage.getWidth();
        }

        AffineTransformOp affineTransformOp = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
        image = affineTransformOp.filter(bufferedImage, null);
        bufferedImage.flush();
        InputStream is = null;
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageOutputStream imageOutputStream = null;
        try {
//            File ThF = new File("f://2", "ps_low1" +"."+"jpg");
//            ImageIO.write((BufferedImage) image, "jpg", ThF);
            imageOutputStream = ImageIO.createImageOutputStream(bs);
            ImageIO.write((BufferedImage) image, "jpg", imageOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("write the stream error");
        }

        is= new ByteArrayInputStream(bs.toByteArray());
        return is;
    }

    @Override
    public InputStream call(){
        InputStream inputStream1 = null;
        try {
            inputStream1 = compressPicture(pictureName, ratio, width, height, contentType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream1;
    }
}
