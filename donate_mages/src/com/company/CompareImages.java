package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by charm on 5/21/14.
 */
public class CompareImages {

    public ArrayList compare(BufferedImage image1, BufferedImage image2, int x1, int y1, int x2, int y2) {
        y1 = Math.max(y1, 0);
        y2 = Math.min(y2, 1000);
        ArrayList<Point> ans = new ArrayList();
        int w1 = image1.getWidth();
        int h1 = image1.getHeight();
        int w2 = image2.getWidth();
        int h2 = image2.getHeight();
        int bestX = 0;
        int bestY = 0;
        double lowestDiff = Double.POSITIVE_INFINITY;
        for (int x = x1; x < x2; x++) {
            for (int y = y1; y < y2 - h2; y++) {
                if (x + w2 >= w1 || y + h2 >= h1)
                    break;
                double comp = compareImages(image1.getSubimage(x, y, w2, h2), image2);
                if (comp < lowestDiff) {
                    bestX = x;
                    bestY = y;
                    lowestDiff = comp;
                }
            }
        }
        ans.add(new Point(bestX, bestY));
        if (lowestDiff < 0.1)
            ans.add(new Point(10000, 10000));
        return ans;
    }

    public static double compareImages(BufferedImage im1, BufferedImage im2) {
        double variation = 0.0;
        for (int x = 0; x < im1.getWidth(); x++) {
            for (int y = 0; y < im1.getHeight(); y++) {
                variation += compareARGB(im1.getRGB(x, y), im2.getRGB(x, y)) / Math.sqrt(3);
            }
        }
        return variation / (im1.getWidth() * im1.getHeight());
    }

    public static double compareARGB(int rgb1, int rgb2) {
        double r1 = ((rgb1 >> 16) & 0xFF) / 255.0;
        double r2 = ((rgb2 >> 16) & 0xFF) / 255.0;
        double g1 = ((rgb1 >> 8) & 0xFF) / 255.0;
        double g2 = ((rgb2 >> 8) & 0xFF) / 255.0;
        double b1 = (rgb1 & 0xFF) / 255.0;
        double b2 = (rgb2 & 0xFF) / 255.0;
        double a1 = ((rgb1 >> 24) & 0xFF) / 255.0;
        double a2 = ((rgb2 >> 24) & 0xFF) / 255.0;
        return a1 * a2 * Math.sqrt((r1 - r2) * (r1 - r2) + (g1 - g2) * (g1 - g2) + (b1 - b2) * (b1 - b2));
    }


}
