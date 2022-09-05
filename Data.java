package com.example.diabetesmanagement.Model;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

import Diabetes.About1;
import Diabetes.About2;
import Diabetes.About3;
import Diabetes.About4;
import Diabetes.About5;
import Diabetes.About6;
import Diabetes.About7;

public class Data extends AppIntro
{
    @Override
    public void init(Bundle savedInstanceState)
    {
        addSlide(new About1());
        addSlide(new About2());
        addSlide(new About3());
        addSlide(new About4());
        addSlide(new About5());
        addSlide(new About6());
        addSlide(new About7());
    }


    @Override
    public void onSkipPressed(Fragment fragment) {
        finish();
    }

    @Override
    public void onDonePressed(Fragment fragment) {
        finish();
    }
}


