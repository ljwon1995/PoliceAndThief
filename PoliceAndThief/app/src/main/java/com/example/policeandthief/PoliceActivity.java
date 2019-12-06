package com.example.policeandthief;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PoliceActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GpsController gpsController;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<Decoder> decoders;
    private PoliceLocSender pls;
    private PoliceLocGetter plg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.police_map);
        mapFragment.getMapAsync(this);

        pls = new PoliceLocSender(PoliceActivity.this, this);
        pls.start();

        plg = new PoliceLocGetter(mMap);
        plg.start();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        gpsController = new GpsController(PoliceActivity.this, this);

        if (gpsController.isGetLocation()) {

            Location loc = gpsController.getLoc();

            Toast.makeText(
                    getApplicationContext(),
                    "당신의 위치 - \n위도: " + loc.getAltitude() + "\n경도: " + loc.getLongitude(),
                    Toast.LENGTH_LONG).show();

            LatLng CUR = new LatLng(loc.getLatitude(), loc.getLongitude());


            mMap.moveCamera(CameraUpdateFactory.newLatLng(CUR));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            mMap.setMyLocationEnabled(true);

            //디코더 맵에 찍기
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference().child("Test").child("Decoders");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    GenericTypeIndicator<ArrayList<Decoder>> to = new GenericTypeIndicator<ArrayList<Decoder>>() {};
                    decoders = dataSnapshot.getValue(to);
                    putDecoderOnMap();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        } else {
            gpsController.popAlert();
        }

    }

    public void putDecoderOnMap(){
        MarkerOptions markerOptions = new MarkerOptions();


        for(int i = 0; i < 5; i++){

            BitmapDrawable bitmapDrawable;

            switch(decoders.get(i).getProgress()){

                case 25:
                    bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.decoder_img25);
                    break;
                case 50:
                    bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.decoder_img50);
                    break;
                case 75:
                    bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.decoder_img75);
                    break;
                case 100:
                    bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.decoder_img100);
                    break;

                default:
                    bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.decoder_img);
                    break;

            }
            Bitmap b = bitmapDrawable.getBitmap();
            Bitmap decoderImg = Bitmap.createScaledBitmap(b, 70, 70, false);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(decoderImg));




            LatLng pos = new LatLng(decoders.get(i).getLocation().get("latitude"), decoders.get(i).getLocation().get("longitude"));
            markerOptions.position(pos);
            mMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        try {
            pls.setFlag(false);
            pls.join();
            plg.setFlag(false);
            plg.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        gpsController.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
