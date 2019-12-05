package com.example.policeandthief;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class ProgressCalManager {
    private static final String TAG = "ProgressCalManager!";

    public int calProgress(EditText[] ets, TextView[] tvs){
        int progress = 0;

        for(int i = 0; i < 4; i++){

            if(ets[i].getText().toString().compareTo(tvs[i].getText().toString()) == 0){
                progress += 25;
            }

        }

        Log.d(TAG, "Progress Calculated : " + progress);
        return progress;
    }

    public boolean[] calIsDone(EditText[] ets, TextView[] tvs){
        boolean[] isDone = new boolean[4];

        for(int i = 0; i < 4; i++){
            isDone[i] = false;
        }

        for(int i = 0; i < 4; i++){

            if(ets[i].getText().toString().compareTo(tvs[i].getText().toString()) == 0){
                isDone[i] = true;
            }

        }

        Log.d(TAG, "IsDone Calculated : " + isDone.toString());

        return isDone;
    }



}
