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
    private GpsController gpsController;
    private Context c;
    private Activity a;
    private LatLng cur;
    private static final String TAG = "PoliceLocSender!";
    private Location loc;
    private HashMap<String, Double> Police;
    private double prevLongitude = -1;
    private double prevLatititude = -1;
    private String roomId;


    public PoliceLocSender(Context context, Activity activity, String room){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(roomId).child("GameInfo").child("Police");
        flag = true;
        c = context;
        a = activity;
        gpsController = new GpsController(c, a);
        Police = new HashMap<>();
        roomId = room;
    }

    public void run(){

        while(flag){
            if (gpsController.isGetLocation()) {
                loc = gpsController.getLoc();

                if(prevLatititude != loc.getLatitude() || prevLongitude != loc.getLongitude()){

                    Police.put("latitude", loc.getLatitude());
                    Police.put("longitude", loc.getLongitude());
                    myRef.setValue(Police);

                    prevLongitude = loc.getLongitude();
                    prevLatititude = loc.getLatitude();
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
