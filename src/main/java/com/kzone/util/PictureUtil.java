package com.kzone.util;

import javax.imageio.ImageIO;
import javax.mail.FetchProfile;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Jeffy on 14-5-17
 */
public class PictureUtil {
    String fromFileStr;
    String saveToFileStr;
    String sysimgfile;
    int width;
    int height;
    String suffix;
    /**
     * @param fromFileStr
     *            原始图片完整路径
     * @param saveToFileStr
     *            缩略图片保存路径

     *
     */
    public PictureUtil(String fromFileStr, String saveToFileStr, String sysimgfile,String suffix,int width,int height) {
        this.fromFileStr = fromFileStr;
        this.saveToFileStr = saveToFileStr;
        this.sysimgfile = sysimgfile;
        this.width=width;
        this.height=height;
        this.suffix=suffix;
    }
    public boolean createThumbnail() throws Exception {
        // fileExtNmae是图片的格式 gif JPG 或png
        // String fileExtNmae="";
        double Ratio = 0.0;
        File F = new File(fromFileStr);
        if (!F.isFile())
            throw new Exception(F + " is not image file error in CreateThumbnail!");
        File ThF = new File(saveToFileStr, sysimgfile +"."+suffix);


        InputStream is = null;
        is = new FileInputStream(F);

        BufferedImage Bi = ImageIO.read(is);
        Image Itemp = Bi.getScaledInstance(width, height, Bi.SCALE_SMOOTH);
        if ((Bi.getHeight() > width) || (Bi.getWidth() > height)) {
            if (Bi.getHeight() > Bi.getWidth())
                Ratio = (double)width / Bi.getHeight();
            else
                Ratio = (double)height / Bi.getWidth();
        }
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(Ratio, Ratio), null);
        Itemp = op.filter(Bi, null);

        try {
            ImageIO.write((BufferedImage) Itemp, suffix, ThF);
        } catch (Exception ex) {
            throw new Exception(" ImageIo.write error in CreatThum.: "
                    + ex.getMessage());
        }
        return (true);
    }
    public static void main(String[] args) {
       PictureUtil UI;
        boolean ss = false;
        try {
            UI = new PictureUtil("f://1.jpg", "f://2", "ps_low1","png",20,20);
            ss = UI.createThumbnail();
            if (ss) {
                System.out.println("Success");
            } else {
                System.out.println("Error");
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }




        File f = new File("F:\\1.jpg");
        InputStream is = null;
        try {
            is = new FileInputStream(f);
            BufferedImage Bi = ImageIO.read(is);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
