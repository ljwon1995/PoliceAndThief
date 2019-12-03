package com.example.policeandthief;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class GpsController implements LocationListener {

    private Context c;
    private Activity a;
    private boolean isGPSOn;
    private boolean isNetworkOn;
    private boolean isGetLoc;
    private Location loc;


    private static final long MIN_DISTANCE = 1;
    private static final long MIN_TIME = 1000;
    private static final int PERMISSIONS_ACCESS_FINE_LOCATION = 100;
    private static final int PERMISSIONS_ACCESS_COARSE_LOCATION = 200;
    private boolean isAccessFineLocationPerm = false;
    private boolean isAccessCoarseLocationPerm = false;
    private boolean isPermission = false;

    protected LocationManager lm;

    public GpsController(Context context, Activity activity) {
        isGPSOn = false;
        isNetworkOn = false;
        isGetLoc = false;
        c = context;
        a = activity;
        getLoc();
        if (!isPermission) {
            askPermission();
            return;
        }

    }

    @TargetApi(23)
    public Location getLoc() {
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(c, android.Manifest.permission.ACCESS_FINE_LOCATION )
                        != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(c, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        try {
            lm = (LocationManager) c.getSystemService(Service.LOCATION_SERVICE);
            isGPSOn = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkOn = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (isNetworkOn) {
                isGetLoc = true;
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                if (lm != null) {
                    loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }

            if (isGPSOn) {
                isGetLoc = true;
                if (loc == null) {
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
                    if (lm != null) {
                        loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return loc;
    }



    public boolean isGetLocation() {
        return this.isGetLoc;
    }


    public void popAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(c);

        alertDialog.setTitle("GPS Permission");
        alertDialog.setMessage("GPS가 꺼져있습니다. \n 설정창으로 가시겠습니까?");


        alertDialog.setPositiveButton("설정",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        c.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    private void askPermission() {


        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(a, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocationPerm = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocationPerm = true;
        }

        if (isAccessFineLocationPerm && isAccessCoarseLocationPerm) {
            isPermission = true;
        }
    }

    public void stopGPS(){
        if(lm != null){
            lm.removeUpdates(GpsController.this);
        }
    }
}



