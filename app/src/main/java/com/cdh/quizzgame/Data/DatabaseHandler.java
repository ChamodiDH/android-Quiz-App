package com.cdh.quizzgame.Data;

import com.cdh.quizzgame.Model.ReportEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHandler {
    private final DatabaseReference databaseRef;
    private final FirebaseUser user;

    public DatabaseHandler() {
        user = FirebaseAuth.getInstance().getCurrentUser();

        String uid = user.getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference("users/" + uid);

    }

    public boolean checkUser(){
            if(user == null){
                return false;
            }else {
                return true;
            }
    }

    public void loadUserName(OnCompleteListener<DataSnapshot> onCompleteListener) {
        databaseRef.child("username").get().addOnCompleteListener(onCompleteListener);
    }

    public void saveReportEntry(ReportEntry reportEntry) {
        databaseRef.child("attempt").push().setValue(reportEntry);
    }

}
