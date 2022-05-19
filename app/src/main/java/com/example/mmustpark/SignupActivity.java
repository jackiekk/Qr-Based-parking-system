package com.example.mmustpark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private TextView tvAccount;


    private EditText edFullName;
    private EditText edEmailAddress;
    private EditText edPlateno;
    private EditText edPassword;
    private EditText edPhone;
    private EditText edConfirmPassword;
    private Button btnRegister;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passPattern  =  "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DatabaseReference userDbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        tvAccount = findViewById(R.id.tv_account);

        edFullName = findViewById(R.id.ed_fullname);
        edEmailAddress = findViewById(R.id.ed_address);
        edPlateno = findViewById(R.id.ed_plateno);
        edPassword = findViewById(R.id.ed_pasword);
        edPhone = findViewById(R.id.ed_phone);
        edConfirmPassword = findViewById(R.id.ed_cpassword);
        btnRegister = findViewById(R.id.btn_register);
        tvAccount = findViewById(R.id.tv_account);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");

        tvAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(com.example.mmustpark.SignupActivity.this, com.example.mmustpark.MainActivity.class));
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PerforAuth();

                //insertData();
            }
        });
    }

    private void insertData() {

            String FullName = edFullName.getText().toString();
            String Plateno = edPlateno.getText().toString();
            String EmailAddress = edEmailAddress.getText().toString();
            String Phone = edPhone.getText().toString();

           User user = new User(FullName, Plateno, EmailAddress, Phone);

            userDbRef.push().setValue(user);

    }

    private void PerforAuth() {

        String FullName = edFullName.getText().toString();
        String Plateno = edPlateno.getText().toString();
        String EmailAddress = edEmailAddress.getText().toString();
        String Phone = edPhone.getText().toString();
        String Password = edPassword.getText().toString();
        String ConfirmPassword = edConfirmPassword.getText().toString();


        if (FullName.isEmpty()) {

            edFullName.setError("Field cannot be Empty");

        } else if (EmailAddress.isEmpty()) {

            edEmailAddress.setError("Field cannot be Empty");

        } else if (!EmailAddress.matches(emailpattern)) {

            edEmailAddress.setError("Enter Valid Email");

        } else if (Phone.isEmpty()) {

            edPhone.setError("Field cannot be Empty");

        } else if (Phone.length() < 10 || Phone.length() > 10) {

            edPhone.setError("Enter a Valid Phone Number");

        } else if (Plateno.isEmpty()) {

            edPlateno.setError("Field cannot be Empty");

        } else if (Plateno.length() < 8 || Plateno.length() > 8) {

            edPlateno.setError("Enter a Valid Plate");

        } else if (Password.isEmpty()) {

            edPassword.setError("Field cannot be Empty");

        } else if (!Password.matches(passPattern)) {

            edPassword.setError("Enter a strong password");

        }   else if (ConfirmPassword.isEmpty()) {

            edConfirmPassword.setError("Field cannot be Empty");

        } else if (!Password.equals(ConfirmPassword)) {

            edConfirmPassword.setError("Password Does Not Match");

        } else {
            insertData();

            progressDialog.setMessage("Please Wait While Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(EmailAddress, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(com.example.mmustpark.SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(com.example.mmustpark.SignupActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    private void sendUserToNextActivity() {
        Intent intent=new Intent(com.example.mmustpark.SignupActivity.this, com.example.mmustpark.NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

