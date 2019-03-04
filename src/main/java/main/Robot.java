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
        setAutoDelay(msClickInterval);

        this.mouseButton = mouseButton;
        this.clickRepeatValue = clickRepeatValue;
        this.currentLocation = currentLocation;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    @Override
    public void run() {
        if (!currentLocation) {
            mouseMove(xPos, yPos);
        }
    }

    public static void stopLooping() {
        stopLooping = true;
    }
}
