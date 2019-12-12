package com.example.policeandthief;

import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CatchBtnManager {
    private Button catchBtn;

    public CatchBtnManager(Button catch_btn) {
        catchBtn = catch_btn;
    }


    public void setCatchVisible(float distance){

        boolean setFlag = false;

        if(distance > 15){
            setFlag = false;
        }
        else{
            setFlag = true;
        }

        if(setFlag){
            catchBtn.setVisibility(View.VISIBLE);
        }
        else{
            catchBtn.setVisibility(View.INVISIBLE);
        }

    }
}
