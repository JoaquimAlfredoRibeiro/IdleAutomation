package main;

import java.awt.*;

public class Robot extends java.awt.Robot implements Runnable {

    private static boolean stopLooping = false;

    private int msClickInterval;
    private int mouseButton;
    private int clickRepeatValue;
    private boolean currentLocation;
    private int xPos;
    private int yPos;

    public Robot(int msClickInterval, int mouseButton, int clickRepeatValue, boolean currentLocation, int xPos, int yPos) throws AWTException {
        super();

        this.msClickInterval = msClickInterval;
        this.mouseButton = mouseButton;
        this.clickRepeatValue = clickRepeatValue;
        this.currentLocation = currentLocation;
        this.xPos = xPos;
        this.yPos = yPos;

        stopLooping = false;
    }

    public static void stopLooping() {
        stopLooping = true;
    }

    @Override
    public void run() {

        //moves to set location if defined
        if (!currentLocation) {
            mouseMove(xPos, yPos);
        }

        if (clickRepeatValue != 0) {
            for (int i = 0; i < clickRepeatValue && !stopLooping; i++) {
                mousePress(mouseButton);
                mouseRelease(mouseButton);
                delay(msClickInterval);
            }
        } else {
            do {
                mousePress(mouseButton);
                mouseRelease(mouseButton);
                delay(msClickInterval);
            } while (!stopLooping);

        }
    }
}
