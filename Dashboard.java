package com.example.diabetesmanagement;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import Menu.AboutDiabetesFragment;
import Menu.DiabetesDetectionFragment;
import Menu.NervesHealthFragment;

public class Dashboard extends Fragment {
    private ImageView About, Diet, Insulin, Diabetes, Profile, Emergency;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard,container,false);

        About =(ImageView)view.findViewById(R.id.diabetes_d);
        Diet =(ImageView)view.findViewById(R.id.diet_d);
        Insulin =(ImageView)view.findViewById(R.id.insulin_d);
        Diabetes =(ImageView)view.findViewById(R.id.report_d);
        Profile =(ImageView)view.findViewById(R.id.profile_d);
        Emergency =(ImageView)view.findViewById(R.id.emergency_d);

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutDiabetesFragment()).addToBackStack(null).commit();
            }
        });

        Diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DietPlannerFragment()).addToBackStack(null).commit();
            }
        });

        Insulin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NervesHealthFragment()).addToBackStack(null).commit();
            }
        });

        Diabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DiabetesDetectionFragment()).addToBackStack(null).commit();
            }
        });

        Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(getActivity(), ProfileActivity.class);
                startActivity(main);
            }
        });

        Emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "0123456789";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+number));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(intent);
            }
        });

        return view;
    }

}
