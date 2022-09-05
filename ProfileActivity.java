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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    private EditText profileUsername, profilePassword, profileEmail, profileDate;
    private Button profileUpdate;
    private Button profileCancel;
    private ImageView profileImage;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private FirebaseUser  firebaseUser;

    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri path;
    private StorageReference storageReference;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE ){
            path = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        profileUsername =(EditText)findViewById(R.id.profile_Username);
        profilePassword =(EditText)findViewById(R.id.profile_password);
        profileEmail =(EditText)findViewById(R.id.profile_email);
        profileDate =(EditText)findViewById(R.id.profile_dob);
        profileImage =(ImageView)findViewById(R.id.profile_image);

        profileUpdate =(Button)findViewById(R.id.profile_update);
        profileCancel =(Button)findViewById(R.id.profile_cancel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();

        final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

        final StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Picture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profileImage);
            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.child("User").getValue(Profile.class);
                profileUsername.setText("Username: "+profile.getUsername());
                profilePassword.setText("Password: "+profile.getPassword());
                profileEmail.setText("Email: "+profile.getEmail());
                profileDate.setText("DOB: "+profile.getDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });

        profileCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getApplicationContext(), HomePage.class);
                startActivity(main);
                finish();
            }
        });
        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPassword = profilePassword.getText().toString();
                String newemail = profileEmail.getText().toString();

                firebaseUser.updateEmail(newemail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this, "Email is been updated",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ProfileActivity.this, "Email updated Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                firebaseUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProfileActivity.this, "Password is been updated",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ProfileActivity.this, "Password updated Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //update image
                StorageReference imageRef = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Picture");
                UploadTask uploadTask = imageRef.putFile(path);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Image Upoading Failed.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(ProfileActivity.this, "Image Uploaded.",Toast.LENGTH_SHORT).show();
                    }
                });


                String username = profileUsername.getText().toString();
                String password = profilePassword.getText().toString();
                String email = profileEmail.getText().toString();
                String date = profileDate.getText().toString();

                Profile profile = new Profile(username,email,password,date);
                databaseReference.child("User").setValue(profile);
                Toast.makeText(ProfileActivity.this, "Profile data is updated Sucessfully",Toast.LENGTH_SHORT).show();
                Intent main = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(main);
                finish();

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");//application/* audio/*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_IMAGE);
            }
        });

    }
}
