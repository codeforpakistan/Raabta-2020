package com.kptrafficpolice.trafficapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.kptrafficpolice.trafficapp.Adapters.OffenceAdapter;
import com.kptrafficpolice.trafficapp.Model.OffenceModel;
import com.kptrafficpolice.trafficapp.R;

import java.util.ArrayList;

public class test extends AppCompatActivity {

    private RecyclerView offenceRecyclerView;
    private OffenceAdapter mAdapter;
    ArrayList<OffenceModel> offenceModelArrayList=new ArrayList<>();

    public static String PARENT_TITLE_ONE="Exceeding prescribed speed limit.";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_MotoryCycle="Motorcycle @Rs. 200";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_Jeep="Jeep @Rs. 300";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_Motorcar="Motorcar @Rs. 300";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_Bus="Bus @Rs. 500";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_Truck="Truck @Rs. 500";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_HTV="HTV/PSV @Rs. 700";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offence_list_new);
        offenceRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new OffenceAdapter(offenceModelArrayList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        offenceRecyclerView.setLayoutManager(mLayoutManager);
        offenceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        offenceRecyclerView.setAdapter(mAdapter);

        prepareMovieData();
    }

    private void prepareMovieData() {
        OffenceModel exceeding_limit_OffenceModel=new OffenceModel();
        exceeding_limit_OffenceModel.setOffence_title(PARENT_TITLE_ONE);
        exceeding_limit_OffenceModel.setBike_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_MotoryCycle);
        exceeding_limit_OffenceModel.setCar_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_Motorcar);
        exceeding_limit_OffenceModel.setJeep_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_Jeep);
        exceeding_limit_OffenceModel.setBus_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_Bus);
        exceeding_limit_OffenceModel.setTrolley_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_HTV);
        exceeding_limit_OffenceModel.setTruck_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_Truck);

        offenceModelArrayList.add(exceeding_limit_OffenceModel);
        mAdapter.notifyDataSetChanged();


    }
}