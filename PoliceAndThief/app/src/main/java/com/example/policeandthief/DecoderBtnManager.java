package com.example.policeandthief;

import android.view.View;
import android.widget.Button;

public class DecoderBtnManager {

    private Button decoderBtn;


    public DecoderBtnManager(Button decBtn) {
        decoderBtn = decBtn;
    }


    public void setDecoderVisible(float[] distances){

        boolean setFlag = true;

        for(int i = 0; i < 5; i++){
            if(distances[i] > 50){
                setFlag = false;
                break;
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
