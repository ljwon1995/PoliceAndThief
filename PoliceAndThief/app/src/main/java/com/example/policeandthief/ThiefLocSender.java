package com.example.policeandthief;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThiefLocSender extends Thread {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private boolean flag;
    private String key;
    private GpsController gpsController;
    private Context c;
    private Activity a;
    private LatLng cur;
    private static final String TAG = "LocSender!";
    private Location loc;

    public ThiefLocSender(Context context, Activity activity){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        flag = true;
        c = context;
        a = activity;
        gpsController = new GpsController(c, a);
        Log.d(TAG, gpsController.toString());
    }

    public void run(){
        key = "Test";


        while(flag){
            if (gpsController.isGetLocation()) {
                loc = gpsController.getLoc();
                cur = new LatLng(loc.getLatitude(), loc.getLongitude());
            } else {
                gpsController.popAlert();
            }
            myRef.child(key).setValue(cur);

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
