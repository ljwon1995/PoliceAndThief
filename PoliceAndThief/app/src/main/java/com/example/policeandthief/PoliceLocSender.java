package com.example.policeandthief;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PoliceLocSender extends Thread {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private boolean flag;
    private String key = "Test";
    private GpsController gpsController;
    private Context c;
    private Activity a;
    private LatLng cur;
    private static final String TAG = "PoliceLocSender!";
    private Location loc;
    private HashMap<String, Double> Police;


    //나중에 key 바꿔야함
    public PoliceLocSender(Context context, Activity activity){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(key).child("Police");
        flag = true;
        c = context;
        a = activity;
        gpsController = new GpsController(c, a);
        Police = new HashMap<>();
        Log.d(TAG, gpsController.toString());
    }

    public void run(){

        while(flag){
            if (gpsController.isGetLocation()) {
                loc = gpsController.getLoc();

                Police.put("latitude", loc.getLatitude());
                Police.put("longitude", loc.getLongitude());
            } else {
                gpsController.popAlert();
            }
            myRef.setValue(Police);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setFlag(boolean b){
        flag = b;
    }
}