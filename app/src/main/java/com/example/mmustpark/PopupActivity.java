package com.example.mmustpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PopupActivity extends AppCompatActivity {

    EditText edVehicle;
    Button btnAdd;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DatabaseReference userDbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);

        btnAdd = findViewById(R.id.btn_add);
        edVehicle = findViewById(R.id.ed_vehicle);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.3));

        WindowManager.LayoutParams params =getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Plateno");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertUserData();

                Toast.makeText(com.example.mmustpark.PopupActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void insertUserData() {

        String Plateno = edVehicle.getText().toString();

        User user = new User("", Plateno, "", "");

        userDbRef.push().setValue(user);
    }
}