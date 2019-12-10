package com.example.policeandthief;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WinnerChecker {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String roomId;

    public WinnerChecker(String room){

        roomId = room;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Rooms").child(roomId).child("GameInfo").child("Winner");


    }


    public void setThiefWin(){
        myRef.setValue(1);
    }

    public void setPoliceWin(){
        myRef.setValue(0);
    }


}
