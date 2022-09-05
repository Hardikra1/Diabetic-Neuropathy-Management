package com.example.diabetesmanagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.Random;

public class DietPlannerFragment extends Fragment implements SensorEventListener, StepListener {

    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private static final String TEXT_DIS_COVER = "Number of Km Covered: ";
    private int numSteps;

    private Button BMR , BMR_Data;
    private CircularProgressBar bmr_view;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    float firebase_weight;
    float firebase_height;
    float firebase_age;
    float final_bmr;
    float calories;

    //calories calculation
    float no_excercise = 5;
    float light_excercise = 10;
    float moderate_excercise = 25;
    float hard_excercise = 50;
    float very_hard_excersise = 100;


    private TextView TvSteps, Distance, TextBmr, TextCalories;
    private Button BtnStart;
    private Button BtnStop;

    SensorManager sm;
    TextView tv;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        listen();
        View view = inflater.inflate(R.layout.fragment_dietplanner,container,false);

        BMR =(Button)view.findViewById(R.id.bmr);
        BMR_Data =(Button)view.findViewById(R.id.bmr_data);
        TextBmr =(TextView)view.findViewById(R.id.text_bmr);
        TextCalories =(TextView)view.findViewById(R.id.text_calories);

        bmr_view = (CircularProgressBar)view.findViewById(R.id.bmr_progress);

        //getting data from firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        BMR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String weight = dataSnapshot.child("Data/Bmr/Weight").getValue().toString();
                        firebase_weight = Float.parseFloat(weight);
                        String height = dataSnapshot.child("Data/Bmr/Height").getValue().toString();
                        firebase_height = Float.parseFloat(height);
                        String age = dataSnapshot.child("Data/Bmr/Age").getValue().toString();
                        firebase_age = Float.parseFloat(age);
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

                double bmr_double = 66 + (13.7 * firebase_weight) + (5 * firebase_height ) - (6.8 * firebase_age);
                final_bmr = (float ) bmr_double;
                TextBmr.setText("BMR: "+final_bmr);
                if(numSteps<=no_excercise){
                    calories = final_bmr * (float)1.2;
                }
                else if(numSteps<=light_excercise){
                    calories = final_bmr * (float) 1.375;
                }
                else if(numSteps<=moderate_excercise){
                    calories = final_bmr * (float)1.55;
                }
                else if(numSteps<=hard_excercise){
                    calories = final_bmr*(float)1.725;
                }
                else if(numSteps>=very_hard_excersise){
                    calories = final_bmr *(float)1.9;
                }else {
                    calories = final_bmr;
                }
                TextCalories.setText("Calories: " +calories);

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference ref = firebaseDatabase.getReference(firebaseAuth.getUid());
                ref.child("Data/Insulin/Calories").setValue(calories);
                // Fats Progress Bar
                if (calories > 0) {
                    Toast.makeText(getActivity(), "firebase BMR data is "+final_bmr,Toast.LENGTH_SHORT).show();
                    //fats.setProgress(firebase_glucose);
                    bmr_view.setProgress(100 *(calories)/10000f);
                } else{
                    Toast.makeText(getActivity(), "BMR value is "+final_bmr,Toast.LENGTH_SHORT).show();
                }

                bmr_view.setBackgroundProgressBarWidth(25);
                bmr_view.setBackgroundColor(Color.LTGRAY);
                bmr_view.setRoundBorder(true);
                bmr_view.startAnimation(translation);
            }
        });



        BMR_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Opening BMR Data view",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), BmrData.class);
                startActivity(intent);
            }
        });
        // Get an instance of the SensorManager
        sensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TvSteps = view.findViewById(R.id.tv_steps);

        Distance = view.findViewById(R.id.distance);


        BtnStart = view.findViewById(R.id.btn_start);
        BtnStop = view.findViewById(R.id.btn_stop);



        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(DietPlannerFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                sensorManager.unregisterListener(DietPlannerFragment.this);

                getDistanceRun(numSteps);

            }
        });



        tv= view.findViewById(R.id.textView1);
        //get sensor service
        sm=(SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        //Tell which sensor you are going to use
        //And declare delay of sensor
        //Register all to your sensor object to use
        sm.registerListener(this,sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState)
    }

    public void listen() {
        Toast a = Toast.makeText(getContext(),"Oncreate is displaying test completed",Toast.LENGTH_SHORT);
        a.show();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // TODO Auto-generated method stub
    }
    //This method is called when your mobile moves any direction
    @Override
    public void onSensorChanged(SensorEvent event)
    {


        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            getDistanceRun(numSteps);





            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);

            // x, y, z values
            float value[]=event.values;
            float x=value[0];
            float y=value[1];
            float z=value[2];

            //use moon gravity if outside earth
            float asr=(x*x+y*y+z*z)/(SensorManager.GRAVITY_EARTH*
                    SensorManager.GRAVITY_EARTH);
            //if their is motion detected, then this will be true
            if(asr>=2)
            {
                //Generate random number to detect the motion.
                Random r=new Random();
                int i=r.nextInt(10);
                tv.setText(""+i);
            }

            /*
            String dist = TEXT_DIS_COVER+numSteps;
            long dist1 = Long.parseLong(dist);
            getDistanceRun(dist1);
            */

        }
    }


    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }

    public float getDistanceRun(long numSteps){
        float distance_covered = (float)(numSteps*78)/(float)100000;
        Distance.setText(TEXT_DIS_COVER + distance_covered);
        return distance_covered;

    }


}
