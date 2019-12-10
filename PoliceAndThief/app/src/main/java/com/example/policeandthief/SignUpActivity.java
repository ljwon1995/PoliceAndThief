package com.example.policeandthief;

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

public class SignUpActivity extends AppCompatActivity {

    private Button signUpBtn;
    private EditText idEt;
    private EditText pwEt;
    private EditText pwCheckEt;

    private String pw;
    private String id;
    private String pwCheck;

    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpBtn = findViewById(R.id.sign_up_btn);
        idEt = findViewById(R.id.id_et);
        pwEt = findViewById(R.id.pw_et);
        pwCheckEt = findViewById(R.id.pw_check_et);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Users");

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check pwcheck == pw
                pwCheck = pwCheckEt.getText().toString();
                pw = pwEt.getText().toString();

                if(pw.compareTo(pwCheck) == 0){

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //check if id already exists
                            id = idEt.getText().toString();
                            if(dataSnapshot.child(id).exists()){
                                Toast.makeText(getApplicationContext(), "ID already exists", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                myRef.child(id).setValue(pw);
                                Toast.makeText(getApplicationContext(), "Succeed", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

                else{
                    Toast.makeText(getApplicationContext(), "PwCheckDifferent", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
