package com.example.diabetesmanagement.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.diabetesmanagement.R;

public class IntroSlide5 extends Fragment

{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.intro5_layout, container, false);
        return v;
    }
}
