package com.example.policeandthief;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class RoomListActivity extends AppCompatActivity {

    private ArrayList<RoomItem> list;
    private ListView listview;
    private RoomAdaptor adaptor;
    private MenuItem mSearch;
    private Context c = this;
    private String text;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<RoomItem> arraylist;
    private final static String TAG = "RoomListActivity!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);


        listview = (ListView) findViewById(R.id.roomlv);

        list = new ArrayList<RoomItem>();

        arraylist = new ArrayList<RoomItem>();

        adaptor = new RoomAdaptor(c, list);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef.child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, ""+dataSnapshot.getChildrenCount());

                Iterator<DataSnapshot> itr = dataSnapshot.getChildren().iterator();
                Log.d(TAG,  "get iterator");
                while(itr.hasNext()){
                    RoomItem r = itr.next().child("RoomInfo").getValue(RoomItem.class);
                    Log.d(TAG, r.getRoomName());
                    list.add(r);
                }
                adaptor.setBoardList(list);
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

    }


    @Override
    protected void onResume() {
        super.onResume();


        list.clear();
        adaptor.setBoardList(list);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        Log.d(TAG,  "get ref");
        myRef.child("Rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, ""+dataSnapshot.getChildrenCount());

                Iterator<DataSnapshot> itr = dataSnapshot.getChildren().iterator();
                Log.d(TAG,  "get iterator");
                while(itr.hasNext()){
                    RoomItem r = itr.next().child("RoomInfo").getValue(RoomItem.class);
                    Log.d(TAG, r.getRoomName());
                    list.add(r);
                }
                adaptor.setBoardList(list);
                listview.setAdapter(adaptor);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void make_board(View v){
        Intent intent = new Intent(getApplicationContext(), CreateRoomActivity.class);
        startActivity(intent);
    }


}