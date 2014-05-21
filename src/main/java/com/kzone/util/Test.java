package com.kzone.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.*;

/**
 * Created by Jeffy on 2014/5/21 0021.
 */
public class Test {
    public static InputStream test123 (InputStream inputStream) {
        double Ratio = 0.0;
        int width = 50;
        int height = 50;

        BufferedImage Bi = null;
        try {
            Bi = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image Itemp = Bi.getScaledInstance(width, height, Bi.SCALE_SMOOTH);
        if ((Bi.getHeight() > width) || (Bi.getWidth() > height)) {
            if (Bi.getHeight() > Bi.getWidth())
                Ratio = (double)width / Bi.getHeight();
            else
                Ratio = (double)height / Bi.getWidth();
        }
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(Ratio, Ratio), null);
        Itemp = op.filter(Bi, null);
        Bi.flush();
        ImageOutputStream imOut;
        InputStream is = null;
        ByteArrayOutputStream bs = new ByteArrayOutputStream();

        try {
            imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write((BufferedImage) Itemp, "jpg", imOut);
            is= new ByteArrayInputStream(bs.toByteArray());
            File ThF = new File("f://2", "ps_low1" +"."+"jpg");
            ImageIO.write((BufferedImage) Itemp, "jpg", ThF);
        } catch (Exception ex) {

        }
        return is;

    }
}
