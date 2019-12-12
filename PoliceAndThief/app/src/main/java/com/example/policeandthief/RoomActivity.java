package com.example.policeandthief;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);


        Intent intent = getIntent();

        roomId = intent.getStringExtra("RoomId");
        userId = intent.getStringExtra("UserId");

        changeBtn = findViewById(R.id.change_btn);
        startBtn = findViewById(R.id.start_btn);
        policeTv = findViewById(R.id.police_id_tv);
        thiefTv = findViewById(R.id.thief_id_tv);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Rooms").child(roomId).child("RoomInfo");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    finish();
                }

                else {
                    r = dataSnapshot.getValue(RoomItem.class);


                    policeTv.setText(r.getPoliceId());
                    thiefTv.setText(r.getThiefId());
                    if (r.getGameStart() == 1) {
                        if (r.getPoliceId().compareTo(userId) == 0) {
                            Intent intent = new Intent(RoomActivity.this, PoliceActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("RoomId", r.getRoomName());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(RoomActivity.this, ThiefActivity.class);
                            intent.putExtra("RoomId", r.getRoomName());
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                }
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

                if(r.getPoliceId()!=null && r.getThiefId() != null) {
                    r.setGameStart(1);
                    myRef.setValue(r);
                    if (userId.compareTo(r.getPoliceId()) == 0) {
                        Intent intent = new Intent(RoomActivity.this, PoliceActivity.class);
                        intent.putExtra("RoomId", r.getRoomName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(RoomActivity.this, ThiefActivity.class);
                        intent.putExtra("RoomId", r.getRoomName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "두 명이 되어야 시작할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onBackPressed() {
        //방장일 시 방 파괴
        if(r.getMasterId().compareTo(userId) == 0){
            myRef.removeValue();
        }

        else{
            //방장 아닐 시 자기 ID null 값 만들어주고
            //person --;

            if(r.getPoliceId().compareTo(userId) == 0){
                r.setPoliceId(null);
                r.setPersons(r.getPersons()-1);
            }

            else{
                r.setThiefId(null);
                r.setPersons(r.getPersons()-1);
            }
            myRef.setValue(r);
        }

        finish();


    }
}
