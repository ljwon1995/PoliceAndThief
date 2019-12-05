package com.example.policeandthief;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;

public class BeepManager extends Thread{
    private ToneGenerator tg;
    private int distance = 50;

    public BeepManager(){
        tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
    }

    public void setBeep(float dist){
        distance = (int)dist;
    }

    public void run(){

        while(true) {
            tg.startTone(ToneGenerator.TONE_CDMA_PIP, 100);
            try {
                Thread.sleep(distance * 10);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
