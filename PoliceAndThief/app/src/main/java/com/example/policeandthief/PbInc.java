package com.example.policeandthief;

import android.widget.ProgressBar;

public class PbInc extends Thread {
    private ProgressBar pb;
    private boolean flag;

    public PbInc(ProgressBar progressBar){
        pb = progressBar;
        flag = true;
    }

    public void run(){
        while(flag){
            pb.setProgress(pb.getProgress()+1);
            try{
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void setFlag(boolean b){
        flag = b;
    }
}
