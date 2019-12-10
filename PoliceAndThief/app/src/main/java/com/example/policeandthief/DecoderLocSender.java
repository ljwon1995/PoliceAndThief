package com.example.policeandthief;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DecoderLocSender {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private static final String TAG = "DecoderLocSender!";
    private String roomId;

    public DecoderLocSender(String room){
        roomId = room;
    }

    void sendToServer(Decoder[] decoders){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();


        List<Decoder> decs = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            decs.add(decoders[i]);
        }

        myRef.child(roomId).child("GameInfo").child("Decoders").setValue(decs);
        Log.d(TAG, "decoders send finished");
    }
}
