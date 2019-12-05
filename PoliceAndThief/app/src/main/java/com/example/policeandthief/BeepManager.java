package com.example.policeandthief;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;

public class BeepManager extends Thread{
    private ToneGenerator tg;
    private int distance = 50;
    private boolean flag;

    public BeepManager(){
        tg = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        flag = true;
    }

    public void setBeep(float dist){
        distance = (int)dist;
    }

    public void run(){

        while(flag) {
            tg.startTone(ToneGenerator.TONE_CDMA_PIP, 100);
            try {
                Thread.sleep(distance * 10);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void setFlag(boolean b){
        flag = b;
    }

}
