package com.example.policeandthief;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DecodeActivity extends AppCompatActivity {

    private Decoder decoder;
    private static final String TAG = "DecodeActivity!";
    private static final int RESULT_OK = 100;
    private int progress;
    private Button saveBtn;
    private TextView[] tvs;
    private EditText[] ets;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode);

        Intent intent = getIntent();

        decoder = (Decoder)intent.getSerializableExtra("decoder");



        tvs = new TextView[4];
        ets = new EditText[4];

        //set layout with data
        tvs[0] = findViewById(R.id.tv0);
        tvs[1] = findViewById(R.id.tv1);
        tvs[2] = findViewById(R.id.tv2);
        tvs[3] = findViewById(R.id.tv3);

        ets[0] = findViewById(R.id.et0);
        ets[1] = findViewById(R.id.et1);
        ets[2] = findViewById(R.id.et2);
        ets[3] = findViewById(R.id.et3);

        saveBtn = findViewById(R.id.saveBtn);

        for(int i = 0; i < 4; i++){
            tvs[i].setText(decoder.getTexts().get(i));
        }





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
