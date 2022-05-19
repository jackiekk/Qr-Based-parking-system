package com.example.mmustpark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mmustpark.databinding.ActivityAddvehicleBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddvehicleActivity extends DrawerBaseActivity {

    ActivityAddvehicleBinding binding;
    TextView aVehicle;
    Button btnVehicle;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DatabaseReference userDbRef;
    FirebaseDatabase Dbs;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityAddvehicleBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            allocateActivityTitle("My Vehicle");

            aVehicle =  findViewById(R.id.a_vehicle);
            btnVehicle = findViewById(R.id.btn_vehicle);

            btnVehicle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(com.example.mmustpark.AddvehicleActivity.this, com.example.mmustpark.PopupActivity.class));
                }
            });

            mAuth = FirebaseAuth.getInstance();
            mUser = mAuth.getCurrentUser();

            Dbs = FirebaseDatabase.getInstance();
            userDbRef = Dbs.getReference("Users");
            getdata();

    }

    private void getdata() {
        userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Mainsnapshot) {
                for (DataSnapshot datasnapShot : Mainsnapshot.getChildren()) {

                    String Plateno = datasnapShot.child("plateno").getValue(String.class);

                    aVehicle.setText(Plateno);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddvehicleActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}