package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//если случайно реплей нажался-нужно выйти
//по стрелочкам запросы
//"на защиту"
//строить магов в бараках


public class Main {

    static BufferedImage mag_eng = null;
    static BufferedImage mag_rus = null;

    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        BufferedImage image2 = null; //small picture(button)
        try {
            image2 = ImageIO.read(new File("D:\\don.png"));
            mag_eng = ImageIO.read(new File("D:\\mageng.png"));
            mag_rus = ImageIO.read(new File("D:\\magrus.png"));
        } catch (IOException e) {
            System.out.println("cant donwload image1");
        }

        //х = 330 конец окна чата
        //y = 250 начала чата
        // y = 820 конец чата


        Robot robot = new Robot();
        while (true) {
            robot.mouseMove(590,520);
            robot.keyPress(KeyEvent.VK_ENTER);
            Thread.sleep(5000);
            robot.mouseMove(20,520);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);

            BufferedImage image1 = get_screen();
            int x1 = 50;
            int y1 = 250;
            int x2 = 330;
            int y2 = 820;
            CompareImages comp = new CompareImages();
            ArrayList<Point> list = comp.compare(image1, image2, x1, y1, x2, y2);
            int xCoord = list.get(0).x;
            int yCoord = list.get(0).y;
            ArrayList<Point> tmp1 = comp.compare(image1, mag_eng, 0, list.get(0).y - 70, x2, list.get(0).y);
            ArrayList<Point> tmp2 = comp.compare(image1, mag_rus, 0, list.get(0).y - 70, x2, list.get(0).y);
            if (tmp1.size() == 1 && tmp2.size() == 1)
                continue;

            robot.mouseMove(xCoord, yCoord);
                Thread.sleep(1000);

            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);

            xCoord += 600;
            yCoord -= 50;
            robot.mouseMove(xCoord, yCoord); //where mages

            Thread.sleep(2000);

            for (int j = 0; j < 5; j++) {
                Thread.sleep(500);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
            robot.keyPress(KeyEvent.VK_ESCAPE);
        }
    }

    private static boolean check(Point point, BufferedImage image, BufferedImage big) {
        int x1 = point.x;
        int y1 = point.y;
        int x2 = x1 + image.getWidth();
        int y2 = y1 + image.getHeight();
        for (int i = x1; i < x2; i++) {
            for (int j = y1; j < y2; j++) {
                int t1 = big.getRGB(i, j);
                int t2 = image.getRGB(i - x1, j - y1);
                if (t1 != t2) {
                    return false;
                }
            }
        }
        /*int t;
        for(int i = 30; i <= 400; i++)
            for(int j = 300; j <= 400; j++)
                if (big.getRGB(i,j) == image.getRGB(0,0)) {
                    t = i + j;
                }*/
        return true;
    }

    private static BufferedImage get_screen() throws AWTException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        //ImageIO.write(image, "png", new File("D:\\test\\123.png"));
        return image;
    }


}
