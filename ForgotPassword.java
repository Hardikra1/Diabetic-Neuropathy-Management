package com.example.diabetesmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText Checkemail;
    private Button Checkverify;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        Checkemail = (EditText)findViewById(R.id.email);
        Checkverify = (Button)findViewById(R.id.verify);
        firebaseAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Checkverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Checkemail.getText().toString().trim();
                if(username.equals("")){
                    Toast.makeText(ForgotPassword.this, "Please enter you registered email id.", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this, "password reset email send", Toast.LENGTH_SHORT).show();
                                Intent log = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(log);
                                finish();
                            }else{
                                Toast.makeText(ForgotPassword.this, "Cannot find the registered email in the database.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }


            }
        });

    }
}
