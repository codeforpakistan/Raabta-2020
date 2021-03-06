package com.kptrafficpolice.trafficapp.activities;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kptrafficpolice.trafficapp.fragments.LoginFragment;
import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.utilities.BackgroundService;
import com.kptrafficpolice.trafficapp.utilities.PrefManager;

public class MainActivity extends AppCompatActivity {
    //raabta
//rabta
    Fragment fragment;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String prefCNIC;
    public static boolean SLIDER_FLAG = false;

    PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("com.ghosttech.kptraffic", 0);
        prefManager=new PrefManager(this);
        prefManager.setFirstTimeLaunch(false);
        editor = sharedPreferences.edit();
        prefCNIC = sharedPreferences.getString("true", "");
        Log.d("zma shared pref drawer", prefCNIC);
        if (prefCNIC.toString().length() > 0) {
            Log.d("zma shared if drawer", prefCNIC);
            startActivity(new Intent(MainActivity.this, MainDrawerActivity.class));
            finish();
        } else {
            Log.d("zma shared pref else", prefCNIC);
            fragment = new LoginFragment();
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).
                    commit();

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}