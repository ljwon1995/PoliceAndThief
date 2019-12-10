package com.example.policeandthief;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomActivity extends AppCompatActivity {

    private Button changeBtn;
    private Button startBtn;
    private TextView policeTv;
    private TextView thiefTv;
    private String roomId;
    private RoomItem r;

    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);


        Intent intent = getIntent();

        roomId = intent.getStringExtra("RoomId");

        changeBtn = findViewById(R.id.change_btn);
        startBtn = findViewById(R.id.start_btn);
        policeTv = findViewById(R.id.police_id_tv);
        thiefTv = findViewById(R.id.thief_id_tv);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Rooms").child(roomId).child("RoomInfo");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                r = dataSnapshot.getValue(RoomItem.class);
                policeTv.setText(r.getPoliceId());
                thiefTv.setText(r.getThiefId());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp;
                temp = r.getThiefId();
                r.setThiefId(r.getPoliceId());
                r.setPoliceId(temp);

                policeTv.setText(r.getPoliceId());
                thiefTv.setText(r.getThiefId());

                //send info to server
                myRef.setValue(r);

            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


}
