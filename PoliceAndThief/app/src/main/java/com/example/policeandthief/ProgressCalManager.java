package com.example.policeandthief;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class ProgressCalManager {
    private int progress;
    private static final String TAG = "ProgressCalManager!";

    public ProgressCalManager(){
        progress = 0;
    }

    public int calProgress(EditText[] ets, TextView[] tvs){
        for(int i = 0; i < 4; i++){
            if(ets[i].getText().toString().compareTo(tvs[i].getText().toString()) == 0){
                progress += 25;
            }
        }

        Log.d(TAG, "Progress Calculated : " + progress);

        return progress;
    }



}
