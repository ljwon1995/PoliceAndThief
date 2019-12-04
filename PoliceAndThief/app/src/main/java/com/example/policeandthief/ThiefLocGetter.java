package com.example.policeandthief;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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
    private String key = "Test";
    private ValueEventListener ve;
    private String TAG = "ThiefLocGetter!";
    private AllLocation allLoc;
    private HashMap<String, Double> Thief;
    private HashMap<String, Double> Police;
    private HashMap<String, Double> Decoder;
    private GenericTypeIndicator<HashMap<String, Double>> to;
    private GoogleMap mMap;
    private LatLng curPos;
    private float toDecoderDistance;
    private float toPoliceDistance;
    private DistanceManager dm;
    private BeepManager beepManager;




    public ThiefLocGetter(GoogleMap map){
        Log.d(TAG, "Enter LogGetter Constructor");

        mMap = map;

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        flag = true;


        allLoc = new AllLocation();
        Thief = new HashMap<>();
        Police = new HashMap<>();
        Decoder = new HashMap<>();
        to = new GenericTypeIndicator<HashMap<String, Double>>() {};
        beepManager = new BeepManager();
        ve = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GenericTypeIndicator<HashMap<String, Double>> to = new GenericTypeIndicator<HashMap<String, Double>>() {};


                if(dataSnapshot.child(key).child("Thief").exists()){
                    Thief = dataSnapshot.child(key).child("Thief").getValue(to);
                }

                if(dataSnapshot.child(key).child("Police").exists()){
                    Police = dataSnapshot.child(key).child("Police").getValue(to);
                }

                if(dataSnapshot.child(key).child("Decoder").exists()){
                    Decoder = dataSnapshot.child(key).child("Decoder").getValue(to);
                }

                Log.d(TAG, "Thief = " + Thief.toString());
                Log.d(TAG, "Police = " + Police.toString());
                Log.d(TAG, "Decoder = " + Decoder.toString());

                allLoc.setThief(Thief);
                allLoc.setPolice(Police);
                allLoc.setDecoder(Decoder);


//                    get distance from police and decoder;
                dm = new DistanceManager(allLoc);
                toPoliceDistance = dm.getDistance().get("toPolice");
                toDecoderDistance = dm.getDistance().get("toDecoder");

                Log.d(TAG, "To Police : " + toPoliceDistance);
                Log.d(TAG, "To Decoder : " + toDecoderDistance);
                beepManager.setBeep(toPoliceDistance);
//                decoderBtnManager.setDecoderVisible(toDecoderDistance);


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
                Thread.sleep(1000);
                Log.d(TAG, "sleep");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        myRef.removeEventListener(ve);
    }
}
