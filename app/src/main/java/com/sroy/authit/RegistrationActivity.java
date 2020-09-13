package com.sroy.authit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    public EditText eT1, eT2;
    public Button bT1, bT2;
    public ProgressBar pB1;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        bT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });


        bT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
    }

    private void registerNewUser() {
        pB1.setVisibility(View.VISIBLE);

        String email = eT1.getText().toString();
        String password = eT2.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, "Registration " +
                                            "Successful!",
                                    Toast.LENGTH_SHORT).show();
                            pB1.setVisibility(View.GONE);

                            Intent intent = new Intent(RegistrationActivity.this,
                                    LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Registration Failed. " +
                                    "Please try again later.", Toast.LENGTH_SHORT).show();
                            pB1.setVisibility(View.GONE);
                        }
                    }
                });
    }






    private void loginUserAccount() {
        pB1.setVisibility(View.VISIBLE);

        String email, password;

        email = eT1.getText().toString();
        password = eT2.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Successful",
                                    Toast.LENGTH_SHORT).show();
                            pB1.setVisibility(View.GONE);

                            Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Unsuccessful. Please try " +
                                            "again later!",
                                    Toast.LENGTH_SHORT).show();
                            pB1.setVisibility(View.GONE);
                        }
                    }
                });

    }


    public void initializeUI() {
        eT1 = findViewById(R.id.eT1);
        eT2 = findViewById(R.id.eT2);
        bT1 = findViewById(R.id.bT1);
        bT2 = findViewById(R.id.bT2);
        pB1 = findViewById(R.id.pB1);
    }
}