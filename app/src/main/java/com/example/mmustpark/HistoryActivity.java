package com.example.mmustpark;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mmustpark.databinding.ActivityHistoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HistoryActivity extends DrawerBaseActivity {

    ActivityHistoryBinding binding;

   TextView hDetails;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DatabaseReference entryDbRef;
    FirebaseDatabase Dbs;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityHistoryBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            allocateActivityTitle("History");

        hDetails = findViewById(R.id.h_details);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        Dbs = FirebaseDatabase.getInstance();
        entryDbRef = Dbs.getReference("QrHistoryData");
        getdata();
    }

    private void getdata() {
        entryDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Mainsnapshot) {

                for (DataSnapshot datasnapShot : Mainsnapshot.getChildren()) {

                    String Details = datasnapShot.getValue(String.class);;

                    hDetails.setText( Details);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}