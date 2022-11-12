package com.wizardassassin.gui.model;

import com.apps.util.Prompter;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Music {


    //Field
    Prompter prompter = new Prompter(new Scanner(System.in));
    Clip clip;
    FloatControl fc;
    float currentVolume = 0;
    float previousVolume =0;
    boolean mute = false;

    //Methods
    public void play(String musicLocation) {

        try {
            File musicPath = new File(musicLocation);
            if (musicPath.exists()){
            AudioInputStream audio = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        }
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }
//    public void play(String sound_track){
//        clip.start();
//    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }



    public void stop(String musicLocation) throws IOException {
        clip.close();
        clip.stop();
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

    public Clip getClip() {
        return clip;
    }

    public FloatControl getFc() {
        return fc;
    }

    public float getCurrentVolume() {
        return currentVolume;
    }

    public float getPreviousVolume() {
        return previousVolume;
    }

    public boolean isMute() {
        return mute;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public void setFc(FloatControl fc) {
        this.fc = fc;
    }

    public void setCurrentVolume(float currentVolume) {
        this.currentVolume = currentVolume;
    }

    public void setPreviousVolume(float previousVolume) {
        this.previousVolume = previousVolume;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }


}