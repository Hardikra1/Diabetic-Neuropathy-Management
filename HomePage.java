package com.example.diabetesmanagement;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.diabetesmanagement.intro.IntroActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.common.collect.ImmutableMap;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Map;

import Menu.AboutDiabetesFragment;
import Menu.DiabetesDetectionFragment;
import Menu.NervesHealthFragment;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "DiabetesManagement";
    private DrawerLayout drawer;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        firebaseAuth = FirebaseAuth.getInstance();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer=findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer,
                toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();
        if(savedInstanceState == null){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Dashboard()).commit();
            navigationView.setCheckedItem(R.id.homedashboard);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homedashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dashboard()).commit();
                break;

            case R.id.aboutdiabetes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AboutDiabetesFragment()).addToBackStack(null).commit();
                break;
            case R.id.diabetesdetection:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DiabetesDetectionFragment()).addToBackStack(null).commit();
                break;
            case R.id.dietplanner:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new DietPlannerFragment()).addToBackStack(null).commit();
                break;
            case R.id.nerveshealth:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NervesHealthFragment()).addToBackStack(null).commit();
                break;
            case R.id.profile:
                profile();
                break;
            case R.id.emergency:
                Toast.makeText(this,"Emergency", Toast.LENGTH_SHORT).show();
                emergency();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //function for emergency button in the navigation menu
    private void emergency(){
        String number = "0123456789";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        startActivity(intent);

    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
        super.onBackPressed();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_settings)
        {
            profile();
            return true;
        }

        if(id == R.id.log_out)
        {
            LogOut();
            return true;
        }

        if(id == R.id.action_intro)
        {
            startIntro();
            return true;
        }

        if(id == R.id.action_about)
        {
            displayAboutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void displayAboutDialog()
    {
        final Map<String, String> USED_LIBRARIES = ImmutableMap.of
                (
                        "Firebase", "https://github.com/firebase/firebase-android-sdk",
                        "YouTube", "https://developers.google.com/youtube/android/player/downloads",
                        "AppIntro", "https://github.com/apl-devs/AppIntro",
                        "Picasso", "https://square.github.io/picasso/"
                );

        final Map<String, String> USED_ASSETS = ImmutableMap.of
                (
                        "Resources1", "https://www.diabetes.org.uk/",
                        "Resources2", "https://www.healthline.com/health/type-2-diabetes/diabetic-neuropathy\n",
                        "Resources3", "http://www.diabetesforecast.org/2017/mar-apr/diabetes-applications.html",
                        "Resources4", "https://www.diabetes.org.uk/Guide-to-diabetes/complications/Nerves_Neuropathy",
                        "Resources5", "https://www.diabetes.org.uk/"
                );

        StringBuilder libs = new StringBuilder().append("<ul>");
        for (Map.Entry<String, String> entry : USED_LIBRARIES.entrySet())
        {
            libs.append("<li><a href=\"").append(entry.getValue()).append("\">").append(entry.getKey()).append("</a></li>");
        }
        libs.append("</ul>");

        StringBuilder resources = new StringBuilder().append("<ul>");
        for (Map.Entry<String, String> entry : USED_ASSETS.entrySet())
        {
            resources.append("<li><a href=\"").append(entry.getValue()).append("\">").append(entry.getKey()).append("</a></li>");
        }
        resources.append("</ul>");

        String appName = getString(R.string.app_name);
        int year = Calendar.getInstance().get(Calendar.YEAR);

        String version = "?";
        try
        {
            PackageManager manager = getPackageManager();
            if(manager != null)
            {
                PackageInfo pi = manager.getPackageInfo(getPackageName(), 0);
                version = pi.versionName;
            }
            else
            {
                Log.w(TAG, "Package name not found, PackageManager unavailable");
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            Log.w(TAG, "Package name not found", e);
        }

        WebView wv = new WebView(this);
        String html =
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />" +
                        "<img src=\"file:///android_res/mipmap/ic_launcher.png\" alt=\"" + appName + "\"/>" +
                        "<h1>" +
                        String.format(getString(R.string.about_title_fmt),
                                "<a href=\"" + getString(R.string.app_webpage_url)) + "\">" +
                        appName +
                        "</a>" +
                        "</h1><p>" +
                        appName +
                        " " +
                        String.format(getString(R.string.debug_version_fmt), version) +
                        "</p><p>" +
                        String.format(getString(R.string.app_revision_fmt),
                                "<a href=\"" + getString(R.string.app_revision_url) + "\">" +
                                        getString(R.string.app_revision_url) +
                                        "</a>") +
                        "</p><hr/><p>" +
                        String.format(getString(R.string.app_copyright_fmt), year) +
                        "</p><hr/><p>" +
                        getString(R.string.app_license) +
                        "</p><hr/><p>" +
                        String.format(getString(R.string.app_libraries), appName, libs.toString()) +
                        "</p><hr/><p>" +
                        String.format(getString(R.string.app_resources), appName, resources.toString());


        wv.loadDataWithBaseURL("file:///android_res/drawable/", html, "text/html", "utf-8", null);
        new AlertDialog.Builder(this)
                .setView(wv)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                })
                .show();

    }


    private void startIntro()
    {
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }
    private void LogOut(){
        firebaseAuth.signOut();
        Intent main = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(main);
        finish();
    }
    private void profile(){
        Intent main = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(main);
    }
    private void IntroActivity(){
        return;
    }
}
