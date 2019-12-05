package com.example.policeandthief;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DecodeActivity extends AppCompatActivity {

    private Decoder decoder;
    private static final String TAG = "DecodeActivity!";
    private static final int RESULT_OK = 100;
    private int progress;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode);

        Intent intent = getIntent();

        decoder = (Decoder)intent.getSerializableExtra("decoder");








        


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //calculate progress

                Intent result = new Intent();
                result.putExtra("progress", progress);
                setResult(RESULT_OK, result);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();


        //calculate progress

        Intent result = new Intent();
        result.putExtra("progress", progress);
        setResult(RESULT_OK, result);
        finish();

    }


}
