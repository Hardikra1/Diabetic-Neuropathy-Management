package com.example.diabetesmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    //Firebase
    private EditText username, password;
    private TextView Info;
    private Button forgotPassword;
    private Button SignInbtn;
    private Button SignUpbtn;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username =(EditText)findViewById(R.id.Username);
        password =(EditText)findViewById(R.id.password);

        SignUpbtn =(Button)findViewById(R.id.signUpbtn);
        SignInbtn =(Button)findViewById(R.id.signInbtn);
        Info =(TextView)findViewById(R.id.Info);
        forgotPassword =(Button) findViewById(R.id.forgot_password);

        Info.setText("No of attempts remaning: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user!=null){
            Intent home = new Intent(getApplicationContext(), HomePage.class);
            startActivity(home);
            finish();
        }


        SignInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()){
                    validate(username.getText().toString(),password.getText().toString());
                }

            }
        });

        SignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(log);
            }
        });

    }

    private void validate(String username, String password){
        progressDialog.setMessage("validating");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Loged in Sucessfully",Toast.LENGTH_SHORT).show();
                    Intent home = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(home);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Login Failed",Toast.LENGTH_SHORT).show();
                    counter --;
                    Info.setText("No of attempts remaning: "+ counter);
                    progressDialog.dismiss();
                    if (counter == 0){
                        SignInbtn.setEnabled(false);
                    }
                }
            }
        });
    }

    private Boolean check(){
        Boolean result = false;
        String username1 = username.getText().toString();
        String password1 = password.getText().toString();
        if(username1.isEmpty() || password1.isEmpty() ){
            Toast.makeText(LoginActivity.this, "Please enter all the details",Toast.LENGTH_SHORT).show();

        }else{
            result = true;
        }
        return result;
    }


}
