package com.example.diabetesmanagement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText username, password, email, date;
    private Button SignUpbtn;
    private Button SignInbtn;
    private ImageView userprofile;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;

    String username1;
    String email1;
    String password1;
    String date1;

    private static int PICK_IMAGE = 123;
    Uri path;
    private StorageReference storageReference;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE){
            path = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                userprofile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupviews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();
        //StorageReference ref = storageReference.child(firebaseAuth.getUid());
        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");//application/* audio/*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_IMAGE);
            }
        });

        SignInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(log);
                finish();

            }
        });

        SignUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String user_email = email.getText().toString().trim();
                    String user_password = password.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                send_date();
                                Toast.makeText(MainActivity.this, "User is Register sucessfully", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent log = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(log);
                            }else{
                                Toast.makeText(MainActivity.this, "User is Register Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private void setupviews(){
        username =(EditText)findViewById(R.id.Username);
        password =(EditText)findViewById(R.id.password);
        email =(EditText)findViewById(R.id.email);
        date =(EditText)findViewById(R.id.dob);
        userprofile =(ImageView)findViewById(R.id.profile);

        SignUpbtn =(Button)findViewById(R.id.signUpbtn);
        SignInbtn =(Button)findViewById(R.id.signInbtn);

    }

    private Boolean validate(){
        Boolean result = false;
        username1 = username.getText().toString();
        email1 = email.getText().toString();
        password1 = password.getText().toString();
        date1 = date.getText().toString();
            if(username1.isEmpty() || email1.isEmpty() || password1.isEmpty() || date1.isEmpty() || path == null){
                Toast.makeText(MainActivity.this, "Please enter all the details",Toast.LENGTH_SHORT).show();

            }else{
                result = true;
            }
            return result;
    }

    private void send_date()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageRef = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Picture");
        UploadTask uploadTask = imageRef.putFile(path);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Image Upoading Failed.",Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(MainActivity.this, "Image Uploaded.",Toast.LENGTH_SHORT).show();
            }
        });
        Profile profile = new Profile(username1,email1,password1,date1);
        ref.child("User").setValue(profile);

    }

}

