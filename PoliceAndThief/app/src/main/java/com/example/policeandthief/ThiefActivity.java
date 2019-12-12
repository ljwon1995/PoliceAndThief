package com.example.policeandthief;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    private DecoderLocSender dls;
    private WinnerChecker winnerChecker;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String roomId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thief_maps);

        Intent intent = getIntent();
        roomId = intent.getStringExtra("RoomId");
        database = FirebaseDatabase.getInstance();



        myRef = database.getReference().child("Rooms").child(roomId).child("GameInfo").child("Winner");
        myRef.setValue(-1);







        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int winner = dataSnapshot.getValue(Integer.class);

                if(winner == 0){
                    Intent intent = new Intent(ThiefActivity.this, WinnerActivity.class);
                    intent.putExtra("winner", 0);
                    startActivity(intent);
                }

                else if(winner == 1){
                    Intent intent = new Intent(ThiefActivity.this, WinnerActivity.class);
                    intent.putExtra("winner", 1);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        winnerChecker = new WinnerChecker(roomId);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.thief_map);
        mapFragment.getMapAsync(this);




        dls = new DecoderLocSender(roomId);

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
            Log.d(TAG, "progress_result = " + data.getIntExtra("progress", 0));
            decoders[activeDecoder].setProgress(data.getIntExtra("progress", 0));
            boolean[] resultDone = data.getBooleanArrayExtra("isDone");

            ArrayList<Boolean> b = new ArrayList<>();
            for(int i = 0; i < 4; i++){
                b.add(resultDone[i]);
            }

            decoders[activeDecoder].setIsDone(b);
            Log.d(TAG, decoders[activeDecoder].getIsDone().toString());


            //send decoders info to server!
            dls.sendToServer(decoders);

            //맵에 디코더 찍기!
            putDecoderOnMap();

            for(int i = 0; i < 5; i ++){
                int count = 0;

                if(decoders[i].getProgress() == 100){
                    count++;
                }

                if(count >= 3){
                    winnerChecker.setThiefWin();
                    Intent intent = new Intent(ThiefActivity.this, WinnerActivity.class);
                    intent.putExtra("winner", 1);
                    startActivity(intent);
                }
            }


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        tls.setFlag(false);
        tlg.setFlag(false);




        finish();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        gpsController = new GpsController(ThiefActivity.this, this);
        Log.d(TAG, gpsController.toString());

        if (gpsController.isGetLocation()) {

            Location loc = gpsController.getLoc();

            Toast.makeText(
                    getApplicationContext(),
                    "당신의 위치 - \n위도: " + loc.getLatitude() + "\n경도: " + loc.getLongitude(),
                    Toast.LENGTH_LONG).show();

            LatLng CUR = new LatLng(loc.getLatitude(), loc.getLongitude());
            Log.d(TAG, "Current location : " + loc.getLatitude() + " + " + loc.getLongitude());

            mMap.moveCamera(CameraUpdateFactory.newLatLng(CUR));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            mMap.setMyLocationEnabled(true);


            decoders = new Decoder[5];
            for(int i = 0; i < 5; i++) {
                decoders[i] = new Decoder();
            }

            new DecoderInitializer(decoders, CUR);
            //send decoders info to server!
            dls.sendToServer(decoders);

            tlg = new ThiefLocGetter(mMap, ThiefActivity.this, decoderBtn, decoders, roomId);
            tlg.start();
            tls = new ThiefLocSender(ThiefActivity.this, this, roomId);
            tls.start();




            //맵에 디코더 찍기!!
            putDecoderOnMap();






        } else {
            gpsController.popAlert();
        }

    }

    public void putDecoderOnMap(){
        MarkerOptions markerOptions = new MarkerOptions();




        for(int i = 0; i < 5; i++){

            BitmapDrawable bitmapDrawable;

            switch(decoders[i].getProgress()){

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




            LatLng pos = new LatLng(decoders[i].getLocation().get("latitude"), decoders[i].getLocation().get("longitude"));
            markerOptions.position(pos);
            mMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        gpsController.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
}
