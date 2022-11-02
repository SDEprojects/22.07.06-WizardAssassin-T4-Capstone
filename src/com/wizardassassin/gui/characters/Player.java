package com.wizardassassin.gui.characters;

import com.wizardassassin.gui.screen.GamePanel;
import com.wizardassassin.gui.screen.KeyHandler;

public class Player extends Character{

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;
    }


}