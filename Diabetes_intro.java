package Diabetes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;

public class Diabetes_intro extends AppIntro
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


