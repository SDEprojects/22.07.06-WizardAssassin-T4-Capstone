package com.wizardassassin.gui.characters;

import java.awt.image.BufferedImage;

public class Character {

    public int x, y;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int walkCounter = 0;
    public int walkNum = 1;

}