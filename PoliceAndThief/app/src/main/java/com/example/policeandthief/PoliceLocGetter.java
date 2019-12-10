package com.example.policeandthief;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PoliceLocGetter extends Thread {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private boolean flag;
    private ValueEventListener ve;
    private String TAG = "PoliceLocGetter!";
    private AllLocation allLoc;
    private HashMap<String, Double> Thief;
    private HashMap<String, Double> Police;

    private GenericTypeIndicator<HashMap<String, Double>> to;
    private GoogleMap mMap;

    private PoliceDistanceManager pdm;

    private float toThiefDistance;
    private CatchBtnManager catchBtnManager;
    private Button catchBtn;

    private Context context;

    private String roomId;

    public PoliceLocGetter(GoogleMap map, Button catch_btn, Context con, String room){

        mMap = map;

        roomId = room;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        flag = true;

        allLoc = new AllLocation();
        Thief = new HashMap<>();
        Police = new HashMap<>();
        catchBtn = catch_btn;
        catchBtnManager = new CatchBtnManager(catchBtn);

        context = con;

        to = new GenericTypeIndicator<HashMap<String, Double>>() {};
        ve = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                GenericTypeIndicator<HashMap<String, Double>> to = new GenericTypeIndicator<HashMap<String, Double>>() {};


                if(dataSnapshot.child("Rooms").child(roomId).child("GameInfo").child("Thief").exists()){
                    Thief = dataSnapshot.child("Rooms").child(roomId).child("GameInfo").child("Thief").getValue(to);
                }

                if(dataSnapshot.child("Rooms").child(roomId).child("GameInfo").child("Police").exists()){
                    Police = dataSnapshot.child("Rooms").child(roomId).child("GameInfo").child("Police").getValue(to);
                }

                Log.d(TAG, "Thief = " + Thief.toString());
                Log.d(TAG, "Police = " + Police.toString());


                allLoc.setThief(Thief);
                allLoc.setPolice(Police);
                pdm = new PoliceDistanceManager(allLoc);
                toThiefDistance = pdm.getDistance().get("toThief");
                Log.d(TAG, "To Thief Distance = " + toThiefDistance);
                Toast.makeText(context, "Distance = " + toThiefDistance, Toast.LENGTH_SHORT).show();

                catchBtnManager.setCatchVisible(toThiefDistance);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


    }

    public void run(){

        myRef.addValueEventListener(ve);

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

    public void setFlag(boolean b){
        flag = b;
    }
}
