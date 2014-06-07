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

    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        String path = new File(".").getCanonicalPath();
        BufferedImage image2 = null; //small picture(button)
        BufferedImage mag_eng = null;
        BufferedImage mag_rus = null;
        try {
            image2 = ImageIO.read(new File(path + "\\don.png"));
            mag_eng = ImageIO.read(new File(path + "\\mageng.png"));
            mag_rus = ImageIO.read(new File(path + "\\magrus.png"));
        } catch (IOException e) {
            System.out.println("cant donwload image1");
        }

        //х = 330 конец окна чата
        //y = 250 начала чата
        // y = 820 конец чата

        Point reload = new Point(590,520);
        Point openChat = new Point(20,520);

        Robot robot = new Robot();
        while (true) {
            robot.mouseMove(reload.x, reload.y);
            robot.keyPress(KeyEvent.VK_ENTER);
            Thread.sleep(5000);
            robot.mouseMove(openChat.x, openChat.y);
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
            ArrayList<Point> engFound = comp.compare(image1, mag_eng, 0, yCoord - 70, x2, yCoord);
            ArrayList<Point> rusFound = comp.compare(image1, mag_rus, 0, yCoord - 70, x2, yCoord);
            if (engFound.size() == 1 && rusFound.size() == 1)
                continue;

            robot.mouseMove(xCoord, yCoord); //button donate
            Thread.sleep(300);

            robot.mousePress(InputEvent.BUTTON1_MASK); //open window donate
            robot.mouseRelease(InputEvent.BUTTON1_MASK);

            robot.mouseMove(xCoord + 620, yCoord - 50); //where mages

            Thread.sleep(1000);

            for (int j = 0; j < 5; j++) {
                Thread.sleep(500);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }
            robot.keyPress(KeyEvent.VK_ESCAPE); //exit donate window
        }
    }

    private static BufferedImage get_screen() throws AWTException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        return image;
    }


}
