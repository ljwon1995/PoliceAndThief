package com.example.policeandthief;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WinnerChecker {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    public WinnerChecker(){
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Test").child("Winner");


    }


    public void setThiefWin(){
        myRef.setValue(1);
    }

    public void setPoliceWin(){
        myRef.setValue(0);
    }


}
