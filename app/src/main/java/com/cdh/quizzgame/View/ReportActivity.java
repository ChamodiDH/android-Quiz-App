package com.cdh.quizzgame.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cdh.quizzgame.Model.ReportEntry;
import com.cdh.quizzgame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReportActivity extends AppCompatActivity {

    TextView userName, details;

    Button backButton;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);

        auth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.profileName);
        backButton = findViewById(R.id.report_back_btn);
        details = findViewById(R.id.details);
        user = auth.getCurrentUser();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
//                finish();
            }
        });

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
                    loadLatestAttemptData(uid);


                } else {

                }
            }
        });


    }

    private void loadLatestAttemptData(String uid) {
        DatabaseReference attemptRef = FirebaseDatabase.getInstance().getReference("users/" + uid + "/attempt");

        attemptRef.limitToLast(1).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ReportEntry latestReport = snapshot.getValue(ReportEntry.class);
                        if (latestReport != null) {

                            details.setText(String.format("Total Questions Answered: %s\nCorrect Answers: %s\nIncorrect Answers: %s", latestReport.getTotalQuestionsAnswered(), latestReport.getCorrectQuestions(), latestReport.getIncorrectQuestions()));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}