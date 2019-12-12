package com.example.policeandthief;

import android.util.Log;
import android.view.View;
import android.widget.Button;

public class DecoderBtnManager {

    private Button decoderBtn;
    private final static String TAG = "DecodeBtnManager!";


    public DecoderBtnManager(Button decBtn) {
        decoderBtn = decBtn;
    }


    public void setDecoderVisible(float[] distances, Decoder[] d){

        boolean setFlag = false;

        for(int i = 0; i < 5; i++){
            Log.d(TAG, "distances" + i + " : " +distances[i]);
            if(distances[i] > 30){
                d[i].setActive(false);
            }
            else{
                d[i].setActive(true);
                setFlag = true;
            }
        }


        if(setFlag){
            decoderBtn.setVisibility(View.VISIBLE);
        }
        else{
            decoderBtn.setVisibility(View.INVISIBLE);
        }

    }




}
