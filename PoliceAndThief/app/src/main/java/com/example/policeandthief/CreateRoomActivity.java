package com.example.policeandthief;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText roomNameEt;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private final static String TAG = "CreateRoomActivity!";

    private RoomItem item;
    private Button createBtn;
    private String roomName;
    private String userId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        Intent intent = getIntent();
        userId = intent.getStringExtra("ID");


        roomNameEt = findViewById(R.id.room_name_et);
        createBtn = findViewById(R.id.create_room_btn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roomName = roomNameEt.getText().toString();
                if(roomName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "방 제목을 입력해주세요!", Toast.LENGTH_LONG).show();
                }

                else{
                    createRoom();
                }
            }
        });




    }

    public void createRoom(){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Rooms").child(roomName).child("RoomInfo");
        RoomItem r = new RoomItem(roomName, userId, 1, userId);


        myRef.setValue(r);
        Intent intent = new Intent(CreateRoomActivity.this, RoomActivity.class);
        intent.putExtra("RoomId", roomName);
        startActivity(intent);
    }
}
