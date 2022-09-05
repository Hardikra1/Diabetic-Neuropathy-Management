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

public class ProgressData extends AppCompatActivity {

    private EditText Blood_Glucose, Carbohydrate;
    private Button Insert;

    private FirebaseAuth firebaseAuth;

    String S_Blood_Glucose;
    String S_Carbohydrate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_data);

        Blood_Glucose =(EditText)findViewById(R.id.weight);
        Carbohydrate =(EditText)findViewById(R.id.carbohydrate);

        Insert=(Button)findViewById(R.id.update_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    Toast.makeText(ProgressData.this, "Validated", Toast.LENGTH_SHORT).show();
                    send_date();
                    Toast.makeText(ProgressData.this, "User data send sucessfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ProgressData.this, "Data send Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Boolean validate(){
        Boolean result = false;
        S_Blood_Glucose = Blood_Glucose.getText().toString();
        S_Carbohydrate = Blood_Glucose.getText().toString();
        if(S_Blood_Glucose.isEmpty() || S_Carbohydrate.isEmpty()){
            Toast.makeText(ProgressData.this, "Please enter all the details",Toast.LENGTH_SHORT).show();

        }else{
            result = true;
        }
        return result;
    }

    private void send_date()
    {
        S_Blood_Glucose = Blood_Glucose.getText().toString();
        S_Carbohydrate = Carbohydrate.getText().toString();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference(firebaseAuth.getUid());
        ref.child("Data/Insulin/Blood Glucose").setValue(S_Blood_Glucose);
        ref.child("Data/Insulin/Carbohydrate").setValue(S_Carbohydrate);
    }

}
