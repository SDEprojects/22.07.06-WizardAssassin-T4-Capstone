package com.wizardassassin.gui.screen;

import com.wizardassassin.gui.characters.Player;
import com.wizardassassin.gui.tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // Screen Settings

    final int originalTileSize = 16; // 16x16 tile, default size of player characters
    final int scale = 3; // scaling for tile size, 16x3 = 48x48

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 24; // 24 columns of tiles
    public final int maxScreenRow = 18; // 18 rows of tiles
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // FPS
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

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

        player.update();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; // change Graphics g to Graphics 2D

        tileM.draw(g2);

        player.draw(g2);

        g2.dispose(); // dispose of this graphics context
    }
}