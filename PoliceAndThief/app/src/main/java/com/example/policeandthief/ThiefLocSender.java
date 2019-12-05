package com.example.policeandthief;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ThiefLocSender extends Thread {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private boolean flag;
    private String key = "Test";
    private GpsController gpsController;
    private Context c;
    private Activity a;
    private LatLng cur;
    private static final String TAG = "ThiefLocSender!";
    private Location loc;
    private HashMap<String, Double> Thief;
    private double prevLongitude = -1;
    private double prevLatititude = -1;

    //나중에 key 값 바꿔야함!!

    public ThiefLocSender(Context context, Activity activity){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(key).child("Thief");
        flag = true;
        c = context;
        a = activity;
        gpsController = new GpsController(c, a);
        Thief = new HashMap<>();
        Log.d(TAG, gpsController.toString());
    }

    public void run(){

        while(flag){
            if (gpsController.isGetLocation()) {
                loc = gpsController.getLoc();

                if(prevLatititude != loc.getLatitude() || prevLongitude != loc.getLongitude()){

                    Thief.put("latitude", loc.getLatitude());
                    Thief.put("longitude", loc.getLongitude());
                    myRef.setValue(Thief);

                    prevLongitude = loc.getLongitude();
                    prevLatititude = loc.getLatitude();
                    Log.d(TAG, "latitude : "+Thief.get("latitude"));
                    Log.d(TAG, "longitude : " + Thief.get("longitude"));

                }

            } else {
                gpsController.popAlert();
            }
        }

    }

    public void setFlag(boolean b){
        flag = b;
    }
}
