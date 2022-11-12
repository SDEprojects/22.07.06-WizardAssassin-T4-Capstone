package com.wizardassassin.model;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Music {
    //Field
    Clip clip;
    private FloatControl fc;
    float currentVolume = 0;
    float previousVolume = 0;
    boolean mute = false;
    Boolean playCompleted = false;

    public InputStream fileGetter(String fileName) {

        try {
            InputStream input = this.getClass()
                    .getClassLoader()
                    .getResourceAsStream(fileName);
            return input;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Methods
    public void play() {
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

    public void volumeUp() {
        currentVolume += 1.0f;
        if (getCurrentVolume() > 6.0f) {
            setCurrentVolume(6.0f);
        }
        getFc().setValue(getCurrentVolume());
    }

    public void volumeDown() {
        currentVolume -= 1.0f;
        if (getCurrentVolume() < -80.0f) {
            setCurrentVolume(-80.0f);
        }
        getFc().setValue(getCurrentVolume());
    }

    public void volumeMute() {
        if (!mute) {
            previousVolume = currentVolume;
            currentVolume = 80.0f;
            getFc().setValue(getCurrentVolume());
            mute = true;
        } else if (mute) {
            currentVolume = previousVolume;
            getFc().setValue(getCurrentVolume());
            mute = false;
        }
    }

    public Boolean getPlayCompleted() {
        return playCompleted;
    }

    public void setPlayCompleted(Boolean playCompleted) {
        this.playCompleted = playCompleted;
    }

    public FloatControl getFc() {
        return fc;
    }

    public float getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(float currentVolume) {
        this.currentVolume = currentVolume;
    }

}