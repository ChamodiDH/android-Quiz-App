package com.cdh.quizzgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportActivity extends AppCompatActivity {

    TextView userName, details;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);

        auth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.profileName);
        details = findViewById(R.id.details);
        user = auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        String uid = user.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/" + uid);
        databaseReference.child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    String name = task.getResult().getValue(String.class);
                    userName.setText(name.toString());


                } else {
                    // Handle the error
                }
            }
        });


    }
}