package com.kptrafficpolice.trafficapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.kptrafficpolice.trafficapp.R;

import java.util.ArrayList;

public class LicenseProcedureResult extends AppCompatActivity {
    ArrayList<String> resultArraylist;
    ScrollView scrollView1,scrollView2,scrollView3,scrollView4,scrollView5,scrollView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_license_procedure_result);
        scrollView1=findViewById(R.id.main1);
        scrollView2=findViewById(R.id.main2);
        scrollView3=findViewById(R.id.main3);
        scrollView4=findViewById(R.id.main4);
        scrollView5=findViewById(R.id.main5);
        scrollView6=findViewById(R.id.main6);

        String number=getIntent().getStringExtra("key");
        if (number.equalsIgnoreCase("1")){
            scrollView1.setVisibility(View.VISIBLE);
            scrollView2.setVisibility(View.GONE);
            scrollView3.setVisibility(View.GONE);
            scrollView4.setVisibility(View.GONE);
            scrollView5.setVisibility(View.GONE);
            scrollView6.setVisibility(View.GONE);

        }
        else if (number.equalsIgnoreCase("2")){
            scrollView1.setVisibility(View.GONE);
            scrollView2.setVisibility(View.VISIBLE);
            scrollView3.setVisibility(View.GONE);
            scrollView4.setVisibility(View.GONE);
            scrollView5.setVisibility(View.GONE);
            scrollView6.setVisibility(View.GONE);
        }

        else if (number.equalsIgnoreCase("3")){
            scrollView1.setVisibility(View.GONE);
            scrollView2.setVisibility(View.GONE);
            scrollView3.setVisibility(View.VISIBLE);
            scrollView4.setVisibility(View.GONE);
            scrollView5.setVisibility(View.GONE);
            scrollView6.setVisibility(View.GONE);
        }

        else if (number.equalsIgnoreCase("4")){
            scrollView1.setVisibility(View.GONE);
            scrollView2.setVisibility(View.GONE);
            scrollView3.setVisibility(View.GONE);
            scrollView4.setVisibility(View.VISIBLE);
            scrollView5.setVisibility(View.GONE);
            scrollView6.setVisibility(View.GONE);
        }

        else if (number.equalsIgnoreCase("5")){
            scrollView1.setVisibility(View.GONE);
            scrollView2.setVisibility(View.GONE);
            scrollView3.setVisibility(View.GONE);
            scrollView4.setVisibility(View.GONE);
            scrollView5.setVisibility(View.VISIBLE);
            scrollView6.setVisibility(View.GONE);
        }

        else if (number.equalsIgnoreCase("6")){
            scrollView1.setVisibility(View.GONE);
            scrollView2.setVisibility(View.GONE);
            scrollView3.setVisibility(View.GONE);
            scrollView4.setVisibility(View.GONE);
            scrollView5.setVisibility(View.GONE);
            scrollView6.setVisibility(View.VISIBLE);
        }


    }
    public void fillDate(){


    }
}