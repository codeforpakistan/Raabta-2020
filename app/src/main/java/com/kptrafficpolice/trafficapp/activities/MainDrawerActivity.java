package com.kptrafficpolice.trafficapp.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.navigation.NavigationView;
import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.fragments.ChangePassword;
import com.kptrafficpolice.trafficapp.fragments.MainFragment;
import com.kptrafficpolice.trafficapp.fragments.MyComplaintsFragment;
import com.kptrafficpolice.trafficapp.fragments.MyLicenseFragment;
import com.thefinestartist.finestwebview.FinestWebView;

import io.fabric.sdk.android.Fabric;
//raabta
//rabta
public class MainDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String prefCNIC;
    DrawerLayout drawer;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main_drawer);
        sharedPreferences = getSharedPreferences("com.ghosttech.kptraffic", 0);
        editor = sharedPreferences.edit();

        fragment = new MainFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean("key",false);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        fragment.setArguments(bundle);
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.fragment_container, fragment);
//        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();

        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // customActionBar();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item != null && item.getItemId() == android.R.id.home) {
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);
            } else {
                drawer.openDrawer(Gravity.RIGHT);
            }
        }
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_website) {
            new FinestWebView.Builder(MainDrawerActivity.this)
                    .titleDefault("KP Traffic Police Official Website")
                    .titleFont("Roboto-Medium.ttf")
                    .disableIconForward(true)
                    .disableIconBack(true)
                    .show("http://kppolice.gov.pk/");
        } else if (id == R.id.nav_logout) {
            editor.clear().commit();
            startActivity(new Intent(MainDrawerActivity.this, MainActivity.class));
            finish();
        }

        else if (id == R.id.nav_contact) {
            contactDialog();
        }
        else if (id == R.id.nav_home) {

            fragment = new MainFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("key",false);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            fragment.setArguments(bundle);
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();

        }


        else if (id == R.id.nav_change_password) {

            fragment = new ChangePassword();
            Bundle bundle = new Bundle();
            bundle.putBoolean("key",false);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            fragment.setArguments(bundle);
// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();

        }
        /*else if (id==R.id.nav_license){

            Bundle bundle = new Bundle();
            bundle.putBoolean("key",true);

            fragment = new MainFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment.setArguments(bundle);

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();


        }*/



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void contactDialog() {
        dialog = new Dialog(MainDrawerActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.contact_us_dialog);
        dialog.setCancelable(true);
        Button call1=dialog.findViewById(R.id.caller_no1);
        Button call2=dialog.findViewById(R.id.caller_no2);
        Button call3=dialog.findViewById(R.id.caller_no3);
        call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0919225361"));
                startActivity(intent);
            }
        });
        call2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0919225355"));
                startActivity(intent);
            }
        });
        call3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0919211475"));
                startActivity(intent);

            }
        });
        dialog.show();
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

    }


    public void customActionBar() {
        ActionBar mActionBar = MainDrawerActivity.this.getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(MainDrawerActivity.this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Write a complaint here");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

}
