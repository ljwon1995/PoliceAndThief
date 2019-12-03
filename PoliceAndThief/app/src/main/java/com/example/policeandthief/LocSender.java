package com.example.policeandthief;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocSender extends Thread {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private boolean flag;
    private String key;
    private GpsController gpsController;
    private Context c;
    private Activity a;
    private LatLng cur;
    private static final String TAG = "LocSender!";

    public LocSender(Context context, Activity activity){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        flag = true;
        c = context;
        a = activity;
        gpsController = new GpsController(c, a);
        Log.d(TAG, gpsController.toString());
    }

    public void run(){
        key = myRef.push().getKey();
        Log.d(TAG, "get key = " + key);

        while(flag){
            if (gpsController.isGetLocation()) {
                Location loc = gpsController.getLoc();
                cur = new LatLng(loc.getLatitude(), loc.getLongitude());
            } else {
                gpsController.popAlert();
            }
            myRef.child(key).setValue(cur);
            try {
                Thread.sleep(5000);
            }catch(Exception e){
                e.printStackTrace();
            }

        }

    }

    public void setFlag(boolean b){
        flag = b;
    }





}
