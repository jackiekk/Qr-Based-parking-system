package com.example.mmustpark;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.mmustpark.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class ProfileActivity extends DrawerBaseActivity {

    ActivityProfileBinding binding;
    EditText pFullname, pPlateno, pEmailAddress, pPhone;
    TextView pUsername;
    Button btnUpdate;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DatabaseReference userDbRef;
    FirebaseDatabase Dbs;

    String userUID;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        allocateActivityTitle("Profile");
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userUID = mUser.getUid();

        pFullname = findViewById(R.id.p_fullname);
        pPlateno = findViewById(R.id.p_plateno);
        pEmailAddress = findViewById(R.id.p_emailAddress);
        pPhone = findViewById(R.id.p_phone);
        pUsername = findViewById(R.id.p_username);
        btnUpdate = findViewById(R.id.btn_update);

       /*btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.isAvailable = false; // new values go here
                user.where = true;

                Map<String, Object> newUserValues = user.toMap();
                String FullName = pFullname.getText().toString();
                String Phone = pPhone.getText().toString();

                pFullname.setText(FullName);
                pPhone.setText(Phone);

                userDbRef.child("Users").updateChildren((newUserValues).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        Toast.makeText(ProfileActivity.this, "Your Data is Successfully updated", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });*/

        Dbs = FirebaseDatabase.getInstance();

        pUsername.setText( mUser.getEmail());

        userDbRef = Dbs.getReference().child("Users");
        getdata();
    }
    public void getdata() {
       userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot Mainsnapshot) {
                for (DataSnapshot datasnapShot : Mainsnapshot.getChildren()) {

                //User userProfile = datasnapShot.getValue(User.class

                String FullName = datasnapShot.child("fullName").getValue(String.class);
                String Plateno = datasnapShot.child("plateno").getValue(String.class);
                String EmailAddress = datasnapShot.child("emailAddress").getValue(String.class);
                String Phone = datasnapShot.child("phone").getValue(String.class);

                //pFullName.setText(userProfile.getFullName());
                // pPlateno.setText(userProfile.getPlateno());
                //pEmailAddress.setText(userProfile.getEmailAddress());
                //pEmailAddress.setText(userProfile.getPhone());

                pFullname.setText(FullName);
                pPlateno.setText(Plateno);
                pEmailAddress.setText(EmailAddress);
                pPhone.setText(Phone);

            }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();


            }
        });
        /*userDbRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);

                    String FullName = user.getFullName();
                    String Plateno = user.getPlateno();
                    String EmailAddress = user.getEmailAddress();
                    String Phone = user.getPhone();


                    pFullName.setText(FullName);
                    pPlateno.setText(Plateno);
                    pEmailAddress.setText(EmailAddress);
                    pPhone.setText(Phone);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // [START_EXCLUDE]
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });*/
    }
    public void update(View view) {
        if (isFullnameChanged() || isPhonenoChanged()){
            Toast.makeText(this,"Data has been updated", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this,"Data is same cannot be updated", Toast.LENGTH_LONG).show();
    }

    private boolean isPhonenoChanged() {
        String Phone = pPhone.getText().toString();

        if (!Phone.equals(pPhone.getText().toString())){

            userDbRef.child("phone").setValue(pPhone.getText().toString());
            Phone = pPhone.getText().toString();
            return true;

        }else {
            return false;
        }
    }


    private boolean isFullnameChanged() {
        String FullName = pFullname.getText().toString();

        if (!FullName.equals(pFullname.getText().toString())){

            userDbRef.child("fullName").setValue(pFullname.getText().toString());
            FullName = pFullname.getText().toString();
            return true;

        }else {
            return false;
        }
    }
}