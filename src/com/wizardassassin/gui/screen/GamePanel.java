package com.wizardassassin.gui.screen;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings

    final int originalTileSize = 16; // 16x16 tile, default size of player characters
    final int scale = 3; // scaling for tile size, 16x3 = 48x48

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16; // 16 columns of tiles
    final int maxScreenRow = 12; // 12 rows of tiles
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // Set player's default position
    int playerX = 500;
    int playerY = 100;
    int playerSpeed = 4; // 4 pixels

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // drawing will be done in offscreen painting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//    public void run() {
//
//        double drawInterval = 1000000000/FPS; // 0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//
//        while (gameThread != null) {
//
//            // Update information such as character position
//            update();
//            // Draw screen with updated information
//            repaint(); // how you call paintComponent method, draws rectangle with updated player positions
//
//            try {
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000; // convert mills to nanos for more precise time
//
//                if(remainingTime < 0) {
//                    remainingTime = 0;
//                }
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public void run() {

        double drawInterval = 1000000000/FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        // every loop, add the passed time / drawInterval to delta. when delta reaches drawInterval then update and
        // repaint, then reset delta
        while(gameThread != null) {

            // check current time
            currentTime = System.nanoTime();

            // subtract last time from current time (how much time has passed)
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta > 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS: " + drawCount); // game is drawing at 60 FPS
                drawCount = 0;
                timer = 0;
            }
        }
    }
    public void update() {

        if(keyH.upPressed) { // X values increase to the right, Y values increase as they go down
            playerY -= playerSpeed;
        }
        else if (keyH.downPressed) {
            playerY += playerSpeed;
        }
        else if (keyH.leftPressed) {
            playerX -= playerSpeed;
        }
        else if (keyH.rightPressed) {
            playerX += playerSpeed;
        }


    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // change Graphics g to Graphics 2D

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize); // (x, y, width, height)

        g2.dispose(); // dispose of this graphics context
    }
}