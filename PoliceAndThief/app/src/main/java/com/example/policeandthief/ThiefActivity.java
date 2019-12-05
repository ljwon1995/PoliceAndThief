package com.example.policeandthief;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ThiefActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GpsController gpsController;
    private Button decoderBtn;
    private Decoder[] decoders;
    private static final String TAG = "ThiefActivity!";
    private static final int REQUEST_DECODE = 100;
    private ThiefLocSender tls;
    private ThiefLocGetter tlg;
    private int activeDecoder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thief_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.thief_map);
        mapFragment.getMapAsync(this);


        tls = new ThiefLocSender(ThiefActivity.this, this);
        tls.start();

        decoderBtn = findViewById(R.id.decodeBtn);
        decoderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThiefActivity.this, DecodeActivity.class);
                //디코드 정보 intent에 넣어주기
                for(int i = 0; i < 5; i++){
                    if(decoders[i].isActive()){
                        activeDecoder = i;
                        intent.putExtra("decoder", decoders[i]);
                        break;
                    }
                }

                startActivityForResult(intent, REQUEST_DECODE);
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_DECODE){
            Log.d(TAG, "progress = "+decoders[activeDecoder].getProgress());
            Log.d(TAG, "progress_result = " + data.getIntExtra("progress", -1));
            decoders[activeDecoder].setProgress(data.getIntExtra("progress", 0));
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        try {
            tls.setFlag(false);
            tls.join();
            tlg.setFlag(false);
            tlg.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        finish();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        gpsController = new GpsController(ThiefActivity.this, this);

        if (gpsController.isGetLocation()) {

            Location loc = gpsController.getLoc();

            Toast.makeText(
                    getApplicationContext(),
                    "당신의 위치 - \n위도: " + loc.getAltitude() + "\n경도: " + loc.getLongitude(),
                    Toast.LENGTH_LONG).show();

            LatLng CUR = new LatLng(loc.getLatitude(), loc.getLongitude());

            mMap.moveCamera(CameraUpdateFactory.newLatLng(CUR));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            mMap.setMyLocationEnabled(true);


            decoders = new Decoder[5];
            for(int i = 0; i < 5; i++) {
                decoders[i] = new Decoder();
            }

            new DecoderInitializer(decoders);

            for(int i = 0; i < 5; i++) {
                Log.d(TAG, "Decoder" + i + " : "+decoders[i].getLocation().get("latitude"));
                Log.d(TAG, "Decoder" + i + " : "+decoders[i].getLocation().get("longitude"));
            }



            tlg = new ThiefLocGetter(mMap, ThiefActivity.this, decoderBtn, decoders);
            tlg.start();

        } else {
            gpsController.popAlert();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        gpsController.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
}
