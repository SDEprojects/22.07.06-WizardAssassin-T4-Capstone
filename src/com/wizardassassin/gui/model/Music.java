package com.wizardassassin.gui.model;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Music {
    //Field
    Clip clip;
    private FloatControl fc;
    float currentVolume = 0;
    float previousVolume =0;
    boolean mute = false;
    Boolean playCompleted = false;

    public InputStream fileGetter(String fileName)  {

        try {
            InputStream input = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(fileName);
            return  input;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Methods
    public void play()  {
        try {
           AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(fileGetter("wizard.wav")));
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            fc.setValue(6f);
           setPlayCompleted(false);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!playCompleted) {
                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    clip.close();
                }
            });
            thread.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }


    }


    public void stop() throws IOException {
        setPlayCompleted(true);
    }

    public void volumeUp(){
        currentVolume += 1.0f;
        if(currentVolume > 6.0f){
            currentVolume = 6.0f;
        }
        fc.setValue(currentVolume);
    }

    public void volumeDown(){
        currentVolume -= 1.0f;
        if(currentVolume < -80.0f){
            currentVolume = -80.0f;
        }
        fc.setValue(currentVolume);
    }

    public void volumeMute(){
        if(!mute){
            previousVolume  = currentVolume;
            currentVolume = 80.0f;
            fc.setValue(currentVolume);
            mute = true;
        }
        else if(mute){
            currentVolume = previousVolume;
            fc.setValue(currentVolume);
            mute = false;
        }

    }

    public Boolean getPlayCompleted() {
        return playCompleted;
    }

    public void setPlayCompleted(Boolean playCompleted) {
        this.playCompleted = playCompleted;
    }



}