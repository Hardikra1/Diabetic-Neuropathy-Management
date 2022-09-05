package com.example.diabetesmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BmrData extends AppCompatActivity {

    private EditText Weight_Bmr,Height_Bmr,Age_Bmr;
    private Button Insert;

    private FirebaseAuth firebaseAuth;

    String S_Weight_Bmr,S_Height_Bmr,S_Age_Bmr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bmr_data);

        Weight_Bmr =(EditText)findViewById(R.id.weight);
        Height_Bmr =(EditText)findViewById(R.id.height);
        Age_Bmr =(EditText)findViewById(R.id.age);

        Insert=(Button)findViewById(R.id.update_data);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Toast.makeText(BmrData.this, "Validated", Toast.LENGTH_SHORT).show();
                    send_date();
                    Toast.makeText(BmrData.this, "User data send sucessfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BmrData.this, "Data send Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private Boolean validate(){
        Boolean result = false;
        S_Weight_Bmr = Weight_Bmr.getText().toString();
        S_Height_Bmr = Height_Bmr.getText().toString();
        S_Age_Bmr = Age_Bmr.getText().toString();

        if(S_Weight_Bmr.isEmpty() ||S_Height_Bmr.isEmpty() || S_Age_Bmr.isEmpty() ){
            Toast.makeText(BmrData.this, "Please enter all the details",Toast.LENGTH_SHORT).show();

        }else{
            result = true;
        }
        return result;
    }

    private void send_date()
    {
        S_Weight_Bmr = Weight_Bmr.getText().toString();
        S_Height_Bmr = Height_Bmr.getText().toString();
        S_Age_Bmr = Age_Bmr.getText().toString();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference(firebaseAuth.getUid());
        ref.child("Data/Bmr/Weight").setValue(S_Weight_Bmr);
        ref.child("Data/Bmr/Height").setValue(S_Height_Bmr);
        ref.child("Data/Bmr/Age").setValue(S_Age_Bmr);
    }
}
