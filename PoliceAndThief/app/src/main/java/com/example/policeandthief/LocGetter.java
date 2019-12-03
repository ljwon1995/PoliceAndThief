package com.example.policeandthief;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThiefLocGetter extends Thread {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private boolean flag;
    private String key = "Test";
    private ChildEventListener ce;
    private LatLng thiefLoc;
    private LatLng policeLoc;
    private LatLng decoderLoc;
    private String TAG = "LocGetter!";

    public ThiefLocGetter(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(key);
        flag = true;

        ce = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                if(dataSnapshot.child("Police").exists()){
                    policeLoc = dataSnapshot.child("Police").getValue(LatLng.class);
                    Log.d(TAG, "Police lat = " + policeLoc.latitude + " Police lon = " + policeLoc.longitude);

                }

                if(dataSnapshot.child("Thief").exists()){
                    Log.d(TAG, "Enter Thief");
                    thiefLoc = dataSnapshot.child("Thief").getValue(LatLng.class);
                    Log.d(TAG, "Thief lat = " + thiefLoc.latitude + " Thief lon = " + thiefLoc.longitude);
                }

                if(dataSnapshot.child("Decoder").exists()){
                    decoderLoc = dataSnapshot.child("Decoder").getValue(LatLng.class);
                    Log.d(TAG, "Decoder lat = " + decoderLoc.latitude + " Decoder lon = " + decoderLoc.longitude);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.child("Police").exists()){
                    policeLoc = dataSnapshot.child("Police").getValue(LatLng.class);
                    Log.d(TAG, "Police lat = " + policeLoc.latitude + " Police lon = " + policeLoc.longitude);

                }

                if(dataSnapshot.child("Thief").exists()){
                    thiefLoc = dataSnapshot.child("Thief").getValue(LatLng.class);
                    Log.d(TAG, "Thief lat = " + thiefLoc.latitude + " Thief lon = " + thiefLoc.longitude);
                }

                if(dataSnapshot.child("Decoder").exists()){
                    decoderLoc = dataSnapshot.child("Decoder").getValue(LatLng.class);
                    Log.d(TAG, "Decoder lat = " + decoderLoc.latitude + " Decoder lon = " + decoderLoc.longitude);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    public void run(){

        myRef.addChildEventListener(ce);

        while(flag){
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        myRef.child(key).removeEventListener(ce);
    }
}
