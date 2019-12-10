package com.example.policeandthief;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ThiefLocGetter extends Thread {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private boolean flag;
    private ValueEventListener ve;
    private String TAG = "ThiefLocGetter!";
    private AllLocation allLoc;
    private HashMap<String, Double> Thief;
    private HashMap<String, Double> Police;
    private GenericTypeIndicator<HashMap<String, Double>> to;
    private GoogleMap mMap;
    private LatLng curPos;
    private float[] toDecoderDistance;
    private float toPoliceDistance;
    private DistanceManager dm;
    private BeepManager beepManager;
    private Context context;
    private Button decoderBtn;
    private DecoderBtnManager decoderBtnManager;
    private Decoder[] decoders;
    private String roomId;






    public ThiefLocGetter(GoogleMap map, Context c, Button decoder_btn, Decoder[] decs, String room){
        Log.d(TAG, "Enter LogGetter Constructor");
        roomId = room;

        mMap = map;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        flag = true;

        toDecoderDistance = new float[5];
        allLoc = new AllLocation();
        Thief = new HashMap<>();
        Police = new HashMap<>();
        to = new GenericTypeIndicator<HashMap<String, Double>>() {};
        beepManager = new BeepManager();


        context = c;
        decoderBtn = decoder_btn;
        decoderBtnManager = new DecoderBtnManager(decoderBtn);

        this.decoders = decs;


        ve = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GenericTypeIndicator<HashMap<String, Double>> to = new GenericTypeIndicator<HashMap<String, Double>>() {};


                if(dataSnapshot.child("Rooms").child(roomId).child("GameInfo").child("Thief").exists()
                && dataSnapshot.child("Rooms").child(roomId).child("GameInfo").child("Police").exists()){
                    Thief = dataSnapshot.child("Rooms").child(roomId).child("GameInfo").child("Thief").getValue(to);
                    Police = dataSnapshot.child("Rooms").child(roomId).child("GameInfo").child("Police").getValue(to);
                    Log.d(TAG, "Thief = " + Thief.toString());
                    Log.d(TAG, "Police = " + Police.toString());

                    allLoc.setThief(Thief);
                    allLoc.setPolice(Police);

//                    get distance from police and decoder;
                    dm = new DistanceManager(allLoc, decoders);


                    toPoliceDistance = dm.getDistance().get("toPolice");


                    for(int i =0 ;i < 5; i++){
                        toDecoderDistance[i] = dm.getDistance().get("toDecoder" + i);
                    }


                    Log.d(TAG, "To Police : " + toPoliceDistance);
                    Toast.makeText(context, "Distance = " + toPoliceDistance, Toast.LENGTH_SHORT).show();
                    for(int i = 0; i < 5; i++){
                        Log.d(TAG, "To Decoder" + i + " : " + toDecoderDistance[i]);
                    }

                    beepManager.setBeep(toPoliceDistance);
                    decoderBtnManager.setDecoderVisible(toDecoderDistance, decoders);
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


    }

    public void run(){

        Log.d(TAG, "enter run");
        myRef.addValueEventListener(ve);
        beepManager.start();
        while(flag){
            try {
                Thread.sleep(10);
                Log.d(TAG, "sleep");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        myRef.removeEventListener(ve);
        beepManager.setFlag(false);
    }

    public void setFlag(boolean b){
        flag = b;
    }
}
