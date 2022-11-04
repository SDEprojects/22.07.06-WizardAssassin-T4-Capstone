package com.wizardassassin.gui.characters;

import com.wizardassassin.gui.screen.GamePanel;
import com.wizardassassin.gui.screen.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;

public class Player extends Character{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 500;
        y = 100;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read((getClass().getResourceAsStream("/player/boy_up_1.png")));
            up2 = ImageIO.read((getClass().getResourceAsStream("/player/boy_up_2.png")));
            down1 = ImageIO.read((getClass().getResourceAsStream("/player/boy_down_1.png")));
            down2 = ImageIO.read((getClass().getResourceAsStream("/player/boy_down_2.png")));
            left1 = ImageIO.read((getClass().getResourceAsStream("/player/boy_left_1.png")));
            left2 = ImageIO.read((getClass().getResourceAsStream("/player/boy_left_2.png")));
            right1 = ImageIO.read((getClass().getResourceAsStream("/player/boy_right_1.png")));
            right2 = ImageIO.read((getClass().getResourceAsStream("/player/boy_right_2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() { // gets called 60x per second -- updating at 60 FPS

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if(keyH.upPressed) { // X values increase to the right, Y values increase as they go down
                direction = "up";
                y -= speed;
            }
            else if (keyH.downPressed) {
                direction = "down";
                y += speed;
            }
            else if (keyH.leftPressed) {
                direction = "left";
                x -= speed;
            }
            else if (keyH.rightPressed) {
                direction = "right";
                x += speed;
            }

            walkCounter++; // player image changes every 15 frames and draws 1 of 2 images based on direction
            if(walkCounter > 15) {
                if (walkNum == 1) {
                    walkNum = 2;
                }
                else if(walkNum == 2) {
                    walkNum = 1;
                }
                walkCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;

        switch(direction) {
            case "up":
                if(walkNum == 1) {
                    image = up1;
                }
                if(walkNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(walkNum == 1) {
                    image = down1;
                }
                if(walkNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(walkNum == 1) {
                    image = left1;
                }
                if(walkNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(walkNum == 1) {
                    image = right1;
                }
                if(walkNum == 2) {
                    image = right2;
                }
                break;
            }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null); // image img, int x, int y, int width, int height,
        // ImageObserver observer
    }
}