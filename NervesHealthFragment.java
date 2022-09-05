package Menu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diabetesmanagement.ProgressData;
import com.example.diabetesmanagement.R;
import com.example.diabetesmanagement.YoutubeViewNueropathy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class NervesHealthFragment extends Fragment {

    private Button Add_Data, Display, NueropathyView;
    private CircularProgressBar glucose,carbohydrate,Insulin;
    private TextView Text_Glucose, Text_Carbohydrate,Text_Insulin;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    float firebase_glucose;
    float max_glucose = 8f;
    float max_carbohydrate = 1000f;

    float firebase_carbohydrate;
    float Insulin_Cal;
    float Carbohydrate_Cover;
    float Sugar_Correction;
    String S_Insulin;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_nerveshealth,container,false);

        Add_Data =(Button)view.findViewById(R.id.add_data);
        Display =(Button)view.findViewById(R.id.display);
        NueropathyView = (Button)view.findViewById(R.id.nueropathy_view);
        Text_Glucose =(TextView)view.findViewById(R.id.text_glucose);
        Text_Carbohydrate =(TextView)view.findViewById(R.id.text_carbohydrate);
        Text_Insulin =(TextView)view.findViewById(R.id.text_insulin);

        glucose = (CircularProgressBar)view.findViewById(R.id.blood_progress);
        carbohydrate = (CircularProgressBar)view.findViewById(R.id.Carbohydrate_progress);
        Insulin = (CircularProgressBar)view.findViewById(R.id.insulin_progress);

        //getting data from firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        NueropathyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Opening youtube view",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), YoutubeViewNueropathy.class);
                startActivity(intent);
            }
        });

        Add_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Opening Progress Data view",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProgressData.class);
                startActivity(intent);
            }
        });

        Display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String data1 = dataSnapshot.child("Data/Insulin/Blood Glucose").getValue().toString();
                        firebase_glucose = Float.parseFloat(data1);

                        String data2 = dataSnapshot.child("Data/Insulin/Carbohydrate").getValue().toString();
                        firebase_carbohydrate = Float.parseFloat(data1);

                        Toast.makeText(getActivity(), "firebase Carbohydrate data "+firebase_carbohydrate,Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "firebase Blood Glucose data "+firebase_glucose,Toast.LENGTH_SHORT).show();
                    }

                    @Override

                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getActivity(), databaseError.getCode(),Toast.LENGTH_SHORT).show();

                    }
                });

                // Animation
                TranslateAnimation translation;
                translation = new TranslateAnimation(0f, 0F, 0f, 180);
                translation.setStartOffset(100);
                translation.setDuration(2000);
                translation.setFillAfter(true);
                translation.setInterpolator(new BounceInterpolator());

                TranslateAnimation translation1;
                translation1 = new TranslateAnimation(0f, 0F, 0f, 370);
                translation1.setStartOffset(100);
                translation1.setDuration(2000);
                translation1.setFillAfter(true);
                translation1.setInterpolator(new BounceInterpolator());

                // Blood Glucose Progress Bar
                if (firebase_glucose > 0) {
                    Toast.makeText(getActivity(), "firebase blood glucose data is "+firebase_glucose,Toast.LENGTH_SHORT).show();
                    //fats.setProgress(firebase_glucose);
                    glucose.setProgress((100 * (firebase_glucose)) / max_glucose);
                } else{
                    Toast.makeText(getActivity(), "Unexpected data is received"+firebase_glucose,Toast.LENGTH_SHORT).show();
                    glucose.setProgress(firebase_glucose);
                }

                glucose.setBackgroundProgressBarWidth(25);
                glucose.setBackgroundColor(Color.LTGRAY);
                glucose.setRoundBorder(true);
                glucose.startAnimation(translation);

                Text_Glucose.setText("Blood Glucose : "+((100 * (firebase_glucose)) / max_glucose)+"mmol/L");

                // Carbohydrate Progress Bar
                if (firebase_carbohydrate > 0) {
                    Toast.makeText(getActivity(), "firebase Carbohydrate data is "+firebase_carbohydrate,Toast.LENGTH_SHORT).show();
                    //fats.setProgress(firebase_glucose);
                    carbohydrate.setProgress(firebase_carbohydrate);
                } else{
                    Toast.makeText(getActivity(), "Unexpected data is received"+firebase_carbohydrate,Toast.LENGTH_SHORT).show();
                    carbohydrate.setProgress(firebase_carbohydrate);
                }

                carbohydrate.setBackgroundProgressBarWidth(25);
                carbohydrate.setBackgroundColor(Color.LTGRAY);
                carbohydrate.setRoundBorder(true);
                carbohydrate.startAnimation(translation);

                Text_Carbohydrate.setText("Carbohydrate : "+((100 * (firebase_carbohydrate)) / max_carbohydrate)+"gram");


                Carbohydrate_Cover = firebase_carbohydrate/10;// Carbohydrate cover ratio is 1:10
                Sugar_Correction = firebase_glucose-5/5;
                Insulin_Cal = Carbohydrate_Cover + Sugar_Correction;


                // Insulin Progress Bar
                if (Insulin_Cal > 0) {
                    Toast.makeText(getActivity(), "Insulin data is "+Insulin_Cal+"units",Toast.LENGTH_SHORT).show();
                    //fats.setProgress(firebase_glucose);
                    Insulin.setProgress(Insulin_Cal);
                } else{
                    Toast.makeText(getActivity(), "Unexpected data is received"+Insulin_Cal+"units",Toast.LENGTH_SHORT).show();
                    Insulin.setProgress(Insulin_Cal);
                }

                Insulin.setBackgroundProgressBarWidth(25);
                Insulin.setBackgroundColor(Color.LTGRAY);
                Insulin.setRoundBorder(true);
                Insulin.startAnimation(translation);


                Text_Insulin.setText("Insulin : " + Insulin_Cal+"units");
                S_Insulin = Float.toString(Insulin_Cal);
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ref = firebaseDatabase.getReference(firebaseAuth.getUid());
                ref.child("Data/Insulin/Insulin").setValue(S_Insulin);


            }
        });

        return view;
    }

}
