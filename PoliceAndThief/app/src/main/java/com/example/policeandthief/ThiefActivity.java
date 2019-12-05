package com.example.policeandthief;

import androidx.fragment.app.FragmentActivity;
import android.location.Location;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thief_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.thief_map);
        mapFragment.getMapAsync(this);

        ThiefLocSender tls = new ThiefLocSender(ThiefActivity.this, this);
        tls.start();

        decoderBtn = findViewById(R.id.decodeBtn);

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

            ThiefLocGetter tlg = new ThiefLocGetter(mMap, ThiefActivity.this, decoderBtn);
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
