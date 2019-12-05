package com.example.policeandthief;

import android.view.View;
import android.widget.Button;

public class DecoderBtnManager {

    private Button decoderBtn;


    public DecoderBtnManager(Button decBtn) {
        decoderBtn = decBtn;
    }


    public void setDecoderVisible(float distance){
        if(distance < 50){
            decoderBtn.setVisibility(View.VISIBLE);
        }
        else{
            decoderBtn.setVisibility(View.INVISIBLE);
        }

    }




}
