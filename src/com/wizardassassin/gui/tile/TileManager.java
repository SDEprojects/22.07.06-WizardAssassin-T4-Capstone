package com.wizardassassin.gui.tile;

import com.wizardassassin.domain.Game;
import com.wizardassassin.gui.screen.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TileManager {

    GamePanel gp;
    Tile[] tile;

    public TileManager(GamePanel gp) {

        this.gp = gp;

        tile = new Tile[10];

        getTileImage();
    }

    public void getTileImage() {

        System.out.println("Loading tile image...");

        try {

            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Tile image loaded...");

    }

    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenCol && row < gp.maxScreenRow) {

            g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x+= gp.tileSize;

            if(col == gp.maxScreenCol) {
                col = 0;
                x= 0;
                row++;
                y+= gp.tileSize;
            }
        }

//        // Top Row
//        g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 48, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 96, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 144, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 192, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 240, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 288, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 336, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 384, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 432, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 480, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 528, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 576, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 624, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 672, 0, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 720, 0, gp.tileSize, gp.tileSize, null);
//
//        // Row 2
//        g2.drawImage(tile[0].image, 0, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 48, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 96, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 144, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 192, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 240, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 288, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 336, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 384, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 432, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 480, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 528, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 576, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 624, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 672, 48, gp.tileSize, gp.tileSize, null);
//        g2.drawImage(tile[0].image, 720, 48, gp.tileSize, gp.tileSize, null);


//        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null); // image img, int x, int y, int width, int height,
//        // ImageObserver observer
    }

}