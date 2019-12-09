package com.example.policeandthief;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WinnerActivity extends AppCompatActivity {

    private TextView tv;
    private int winner;
    private static final String TAG = "WinnerActivity!";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner);





        Intent intent = getIntent();
        winner = intent.getIntExtra("winner", -1);
        Log.d(TAG, "winner = " + winner);

        tv = findViewById(R.id.winnertv);
        Log.d(TAG, tv.toString());

        if(winner == 0){
            tv.setText("Police Won!!");
        }

        else if(winner == 1){
            tv.setText("Thief Won!!");
        }

        else{
            tv.setText("Unknwon Error Occur");
        }


    }
}
