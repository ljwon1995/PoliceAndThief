package com.example.policeandthief;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import android.os.Handler;
import android.widget.Toast;

public class ItemButtonController extends Thread {
    private ProgressBar progressBar;
    private Button itemBtn;
    private Context c;
    private boolean flag;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private GenericTypeIndicator<HashMap<String, Double>> to;
    private GoogleMap mMap;
    private PbInc pbInc;
    private Marker marker;
    private String roomId;


    private final static String TAG = "IBC!";


    public ItemButtonController(ProgressBar pb, Button btn, Context context, GoogleMap map, String room){
        progressBar = pb;
        itemBtn = btn;
        flag = true;
        c = context;
        mMap = map;
        pbInc = new PbInc(pb);
        roomId = room;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Rooms").child(roomId).child("GameInfo").child("Thief");




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(progressBar.getProgress() == 100){
                    //아이템 사용 시 행동
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //3초간 도둑의 현재위치 보여주기

                            HashMap<String, Double> Thief;
                            to = new GenericTypeIndicator<HashMap<String, Double>>() {};

                            Thief = dataSnapshot.getValue(to);

                            LatLng cur = new LatLng(Thief.get("latitude"), Thief.get("longitude"));

                            Log.d(TAG, "cur = "+ cur.toString());

                            MarkerOptions mo = new MarkerOptions();
                            mo.position(cur);
                            BitmapDrawable bitmapDrawable = (BitmapDrawable)c.getResources().getDrawable(R.drawable.thief);
                            Bitmap b = bitmapDrawable.getBitmap();
                            Bitmap decoderImg = Bitmap.createScaledBitmap(b, 120, 120, false);
                            mo.icon(BitmapDescriptorFactory.fromBitmap(decoderImg));
                            marker = mMap.addMarker(mo);

                            Log.d(TAG, "marker added");

                            Handler delayHandler = new Handler();
                            delayHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    marker.remove();
                                }
                            }, 3000);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    progressBar.setProgress(0);

                }

                else{
                    Toast.makeText(c, "아직 사용할 수 없습니다!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void run() {
        pbInc.start();

        while(flag){
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        pbInc.setFlag(false);
        try {
            pbInc.join();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setFlag(boolean b){
        flag = b;
    }

}
