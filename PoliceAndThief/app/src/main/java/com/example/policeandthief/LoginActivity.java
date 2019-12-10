package com.example.policeandthief;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {


    private Button signInBtn;
    private Button signUpBtn;
    private EditText idEt;
    private EditText pwEt;

    private String id;
    private String pw;
    private String expPw;


    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        signInBtn = findViewById(R.id.sign_in_btn);
        signUpBtn = findViewById(R.id.sign_up_btn);
        idEt = findViewById(R.id.id_et);
        pwEt = findViewById(R.id.pw_et);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Users");

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                id = idEt.getText().toString();
                pw = pwEt.getText().toString();

                if(!id.isEmpty()) {

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(id).exists()) {
                                expPw = dataSnapshot.child(id).getValue(String.class);
                                if (expPw.compareTo(pw) == 0) {
                                    Toast.makeText(getApplicationContext(), "Succeed", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(LoginActivity.this, RoomListActivity.class);
                                    intent.putExtra("ID", id);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            }


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                else{
                    Toast.makeText(getApplicationContext(), "ID를 입력해주세요!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}
