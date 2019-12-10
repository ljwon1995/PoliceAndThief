package com.example.policeandthief;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class RoomListActivity extends AppCompatActivity {

    private ArrayList<RoomItem> list;
    private ListView listview;
    private RoomAdaptor adaptor;
    private Context c = this;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private final static String TAG = "RoomListActivity!";
    private String userId;
    Button createRoomBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        Log.d(TAG, "onCreate");


        Intent intent = getIntent();
        userId = intent.getStringExtra("ID");

        listview = (ListView) findViewById(R.id.roomlv);

        list = new ArrayList<RoomItem>();


        adaptor = new RoomAdaptor(c, list);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, ""+dataSnapshot.getChildrenCount());

                list.clear();
                adaptor.setRoomList(list);
                adaptor.notifyDataSetChanged();


                Iterator<DataSnapshot> itr = dataSnapshot.getChildren().iterator();
                Log.d(TAG,  "get iterator");
                while(itr.hasNext()){
                    RoomItem r = itr.next().child("RoomInfo").getValue(RoomItem.class);
                    Log.d(TAG, r.getRoomName());
                    list.add(r);
                }
                adaptor.setRoomList(list);
                listview.setAdapter(adaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() { // 리스트 버튼 작동
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RoomActivity.class);
                String roomName = list.get(position).getRoomName();
                intent.putExtra("id", roomName);
                startActivity(intent);
            }
        });

        createRoomBtn = findViewById(R.id.createBtn);
        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRoom();
            }
        });

    }


    public void createRoom(){
        Intent intent = new Intent(getApplicationContext(), CreateRoomActivity.class);
        intent.putExtra("ID", userId);
        startActivity(intent);
    }


}