package com.kptrafficpolice.trafficapp.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kptrafficpolice.trafficapp.Adapters.ExpandableListAdapter2;
import com.kptrafficpolice.trafficapp.Adapters.OffenceAdapter;
import com.kptrafficpolice.trafficapp.Model.OffenceModel;
import com.kptrafficpolice.trafficapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kptrafficpolice.trafficapp.Constants.AppConstats.*;

public class OffenceListFragments extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<String> parent_title;
    private HashMap<String, List<String>> child_title;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter2 expandableListAdapter2;

    private static String PARENT_TITLE_5="Overtaking where prohibited..";






    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView offenceRecyclerView;
    private OffenceAdapter mAdapter;


    ArrayList<OffenceModel> offenceModelArrayList=new ArrayList<>();

    public static String PARENT_TITLE_ONE="Exceeding prescribed speed limit.";
    public static String PARENT_TITLE_ONE_URDU= String.valueOf(R.string.PARENT_TITLE_ONE_URDU);
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_MotoryCycle="Motorcycle @Rs. 200";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_Jeep="Jeep @Rs. 300";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_Motorcar="Motorcar @Rs. 300";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_Bus="Bus @Rs. 500";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_Truck="Truck @Rs. 500";
    public static String CHILD_TITLE_ONE_Exceeding_prescribed_HTV="HTV/PSV @Rs. 700";


    private static String PARENT_TITLE_TWO="Carrying passengers in public service vehicles exceeding permissible limit.";
    public static String PARENT_TITLE_TWO_URDU= String.valueOf(R.string.PARENT_TITLE_TWO_URDU);

    public static String CHILD_TITLE_2_1="Motorcycle @Rs. --";
    public static String CHILD_TITLE_2_2="Motorcar @Rs. 200";
    public static String CHILD_TITLE_2_3="Jeep @Rs. 200";
    public static String CHILD_TITLE_2_4="Bus @Rs. 500";
    public static String CHILD_TITLE_2_5="Truck @Rs. 500";
    public static String CHILD_TITLE_2_6="HTV/PSV @Rs. 700";

    private static String PARENT_TITLE_3="Violation of traffic signals (manual/electrical)";
    public static String PARENT_TITLE_3_URDU= String.valueOf(R.string.PARENT_TITLE_3_URDU);

    public static String CHILD_TITLE_3_1="Motorcycle @Rs. 200";
    public static String CHILD_TITLE_3_2="Motorcar @Rs. 300";
    public static String CHILD_TITLE_3_3="Jeep @Rs. 300";
    public static String CHILD_TITLE_3_4="Bus @Rs. 500";
    public static String CHILD_TITLE_3_5="Truck @Rs. 500";
    public static String CHILD_TITLE_3_6="HTV/PSV @Rs. 700";

    private static String PARENT_TITLE_4="Overtaking where prohibited..";
    public static String PARENT_TITLE_4_URDE= String.valueOf(R.string.PARENT_TITLE_4_URDE);
    public static String CHILD_TITLE_4_1="Motorcycle @Rs. 100";
    public static String CHILD_TITLE_4_2="Motorcar @Rs. 200";
    public static String CHILD_TITLE_4_3="Jeep @Rs. 200";
    public static String CHILD_TITLE_4_4="Bus @Rs. 200";
    public static String CHILD_TITLE_4_5="Truck @Rs. 200";
    public static String CHILD_TITLE_4_6="HTV/PSV @Rs. 500";



    private static String PARENT_TITLE_6="Failure to yield right of way to other vehicles..";
    public static String PARENT_TITLE_6_URDU= String.valueOf(R.string.PARENT_TITLE_6_URDU);

    public static String CHILD_TITLE_6_1="Motorcycle @Rs. 100";
    public static String CHILD_TITLE_6_2="Motorcar @Rs. 100";
    public static String CHILD_TITLE_6_3="Jeep @Rs. 100";
    public static String CHILD_TITLE_6_4="Bus @Rs. 100";
    public static String CHILD_TITLE_6_5="Truck @Rs. 100";
    public static String CHILD_TITLE_6_6="HTV/PSV @Rs. 100";


    private static String PARENT_TITLE_7="Obstructing movement of emergency vehicles.";
    public static String PARENT_TITLE_7_URDU= String.valueOf(R.string.PARENT_TITLE_7_URDU);

    public static String CHILD_TITLE_7_1="Motorcycle @Rs. 100";
    public static String CHILD_TITLE_7_2="Motorcar @Rs. 200";
    public static String CHILD_TITLE_7_3="Jeep @Rs. 200";
    public static String CHILD_TITLE_7_4="Bus @Rs. 400";
    public static String CHILD_TITLE_7_5="Truck @Rs. 400";
    public static String CHILD_TITLE_7_6="HTV/PSV @Rs. 500";

    private static String PARENT_TITLE_8="Loading in excess of the restrictions of dimension of goods";
    public static String PARENT_TITLE_8_URDU= String.valueOf(R.string.PARENT_TITLE_8_URDU);

    public static String CHILD_TITLE_8_1="Motorcycle @Rs. --";
    public static String CHILD_TITLE_8_2="Motorcar @Rs. --";
    public static String CHILD_TITLE_8_3="Jeep @Rs. --";
    public static String CHILD_TITLE_8_4="Bus @Rs. 300";
    public static String CHILD_TITLE_8_5="Truck @Rs. 400";
    public static String CHILD_TITLE_8_6="HTV/PSV @Rs. 400";


    private static String PARENT_TITLE_9="Driving at night without proper lights.";
    public static String PARENT_TITLE_9_URDU= String.valueOf(R.string.PARENT_TITLE_9_URDU);

    public static String CHILD_TITLE_9_1="Motorcycle @Rs. 100";
    public static String CHILD_TITLE_9_2="Motorcar @Rs. 200";
    public static String CHILD_TITLE_9_3="Jeep @Rs. 200";
    public static String CHILD_TITLE_9_4="Bus @Rs. 300";
    public static String CHILD_TITLE_9_5="Truck @Rs. 500";
    public static String CHILD_TITLE_9_6="HTV/PSV @Rs. 500";


    private static String PARENT_TITLE_10="Driving on the wrong side of the road..";
    public static String PARENT_TITLE_10_URDU= String.valueOf(R.string.PARENT_TITLE_10_URDU);

    public static String CHILD_TITLE_10_1="Motorcycle @Rs. 100";
    public static String CHILD_TITLE_10_2="Motorcar @Rs. 200";
    public static String CHILD_TITLE_10_3="Jeep @Rs. 200";
    public static String CHILD_TITLE_10_4="Bus @Rs. 300";
    public static String CHILD_TITLE_10_5="Truck @Rs. 300";
    public static String CHILD_TITLE_10_6="HTV/PSV @Rs. 500";

    public OffenceListFragments() {
    }

    // TODO: Rename and change types and number of parameters
    public static OffenceListFragments newInstance(String param1, String param2) {
        OffenceListFragments fragment = new OffenceListFragments();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.offence_list_new, container, false);
        customActionBar();
        offenceRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new OffenceAdapter(offenceModelArrayList,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        offenceRecyclerView.setLayoutManager(mLayoutManager);
        offenceRecyclerView.setItemAnimator(new DefaultItemAnimator());
        offenceRecyclerView.setAdapter(mAdapter);

        prepareMovieData();
        return view;
    }

    public void customActionBar() {
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("OFFENCE lIST");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    private void prepareMovieData() {
        OffenceModel exceeding_limit_OffenceModel=new OffenceModel();
        exceeding_limit_OffenceModel.setOffence_title(PARENT_TITLE_ONE);
        exceeding_limit_OffenceModel.setOffence_title_URDU(getResources().getString(R.string.PARENT_TITLE_ONE_URDU));
        exceeding_limit_OffenceModel.setBike_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_MotoryCycle);
        exceeding_limit_OffenceModel.setCar_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_Motorcar);
        exceeding_limit_OffenceModel.setJeep_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_Jeep);
        exceeding_limit_OffenceModel.setBus_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_Bus);
        exceeding_limit_OffenceModel.setTrolley_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_HTV);
        exceeding_limit_OffenceModel.setTruck_offence_fee(CHILD_TITLE_ONE_Exceeding_prescribed_Truck);

        OffenceModel offenceModel_2=new OffenceModel();
        offenceModel_2.setOffence_title(PARENT_TITLE_TWO);
        offenceModel_2.setOffence_title_URDU(getResources().getString(R.string.PARENT_TITLE_TWO_URDU));

        offenceModel_2.setBike_offence_fee(CHILD_TITLE_2_1);
        offenceModel_2.setCar_offence_fee(CHILD_TITLE_2_2);
        offenceModel_2.setJeep_offence_fee(CHILD_TITLE_2_3);
        offenceModel_2.setBus_offence_fee(CHILD_TITLE_2_4);
        offenceModel_2.setTrolley_offence_fee(CHILD_TITLE_2_5);
        offenceModel_2.setTruck_offence_fee(CHILD_TITLE_2_6);


        OffenceModel offenceModel_3=new OffenceModel();
        offenceModel_3.setOffence_title(PARENT_TITLE_3);
        offenceModel_3.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_3_URDU));
        offenceModel_3.setBike_offence_fee(CHILD_TITLE_3_1);
        offenceModel_3.setCar_offence_fee(CHILD_TITLE_3_2);
        offenceModel_3.setJeep_offence_fee(CHILD_TITLE_3_3);
        offenceModel_3.setBus_offence_fee(CHILD_TITLE_3_4);
        offenceModel_3.setTrolley_offence_fee(CHILD_TITLE_3_5);
        offenceModel_3.setTruck_offence_fee(CHILD_TITLE_3_6);

        OffenceModel offenceModel_4=new OffenceModel();
        offenceModel_4.setOffence_title(PARENT_TITLE_4 );
        offenceModel_4.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_4_URDE));
        offenceModel_4.setBike_offence_fee(CHILD_TITLE_4_1);
        offenceModel_4.setCar_offence_fee(CHILD_TITLE_4_2);
        offenceModel_4.setJeep_offence_fee(CHILD_TITLE_4_3);
        offenceModel_4.setBus_offence_fee(CHILD_TITLE_4_4);
        offenceModel_4.setTrolley_offence_fee(CHILD_TITLE_4_5);
        offenceModel_4.setTruck_offence_fee(CHILD_TITLE_4_6);


        OffenceModel offenceModel_6=new OffenceModel();
        offenceModel_6.setOffence_title(PARENT_TITLE_6);
        offenceModel_6.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_6_URDU));
        offenceModel_6.setBike_offence_fee(CHILD_TITLE_6_1);
        offenceModel_6.setCar_offence_fee(CHILD_TITLE_6_2);
        offenceModel_6.setJeep_offence_fee(CHILD_TITLE_6_3);
        offenceModel_6.setBus_offence_fee(CHILD_TITLE_6_4);
        offenceModel_6.setTrolley_offence_fee(CHILD_TITLE_6_5);
        offenceModel_6.setTruck_offence_fee(CHILD_TITLE_6_6);


        OffenceModel offenceModel_7=new OffenceModel();
        offenceModel_7.setOffence_title(PARENT_TITLE_7 );
        offenceModel_7.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_7_URDU));

        offenceModel_7.setBike_offence_fee(CHILD_TITLE_7_1);
        offenceModel_7.setCar_offence_fee(CHILD_TITLE_7_2);
        offenceModel_7.setJeep_offence_fee(CHILD_TITLE_7_3);
        offenceModel_7.setBus_offence_fee(CHILD_TITLE_7_4);
        offenceModel_7.setTrolley_offence_fee(CHILD_TITLE_7_5);
        offenceModel_7.setTruck_offence_fee(CHILD_TITLE_7_6);


        OffenceModel offenceModel_8=new OffenceModel();
        offenceModel_8.setOffence_title(PARENT_TITLE_8 );
        offenceModel_8.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_8_URDU));
        offenceModel_8.setBike_offence_fee(CHILD_TITLE_8_1);
        offenceModel_8.setCar_offence_fee(CHILD_TITLE_8_2);
        offenceModel_8.setJeep_offence_fee(CHILD_TITLE_8_3);
        offenceModel_8.setBus_offence_fee(CHILD_TITLE_8_4);
        offenceModel_8.setTrolley_offence_fee(CHILD_TITLE_8_5);
        offenceModel_8.setTruck_offence_fee(CHILD_TITLE_8_6);

        OffenceModel offenceModel_9=new OffenceModel();
        offenceModel_9.setOffence_title(PARENT_TITLE_9 );
        offenceModel_9.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_9_URDU));

        offenceModel_9.setBike_offence_fee(CHILD_TITLE_9_1);
        offenceModel_9.setCar_offence_fee(CHILD_TITLE_9_2);
        offenceModel_9.setJeep_offence_fee(CHILD_TITLE_9_3);
        offenceModel_9.setBus_offence_fee(CHILD_TITLE_9_4);
        offenceModel_9.setTrolley_offence_fee(CHILD_TITLE_9_5);
        offenceModel_9.setTruck_offence_fee(CHILD_TITLE_9_6);


        OffenceModel offenceModel_10=new OffenceModel();
        offenceModel_10.setOffence_title(PARENT_TITLE_10 );
        offenceModel_10.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_10_URDU));
        offenceModel_10.setBike_offence_fee(CHILD_TITLE_9_1);
        offenceModel_10.setCar_offence_fee(CHILD_TITLE_9_2);
        offenceModel_10.setJeep_offence_fee(CHILD_TITLE_9_3);
        offenceModel_10.setBus_offence_fee(CHILD_TITLE_9_4);
        offenceModel_10.setTrolley_offence_fee(CHILD_TITLE_9_5);
        offenceModel_10.setTruck_offence_fee(CHILD_TITLE_9_6);







        OffenceModel offenceMode_11=new OffenceModel();
        offenceMode_11.setOffence_title(PARENT_TITLE_11 );
        offenceMode_11.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_11_URDU));
        offenceMode_11.setBike_offence_fee(CHILD_TITLE_11_1);
        offenceMode_11.setCar_offence_fee(CHILD_TITLE_11_2);
        offenceMode_11.setJeep_offence_fee(CHILD_TITLE_11_3);
        offenceMode_11.setBus_offence_fee(CHILD_TITLE_11_4);
        offenceMode_11.setTrolley_offence_fee(CHILD_TITLE_11_5);
        offenceMode_11.setTruck_offence_fee(CHILD_TITLE_11_6);


        OffenceModel offenceMode_12=new OffenceModel();
        offenceMode_12.setOffence_title(PARENT_TITLE_12);
        offenceMode_12.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_12_URDU));

        offenceMode_12.setBike_offence_fee(CHILD_TITLE_12_1);
        offenceMode_12.setCar_offence_fee(CHILD_TITLE_12_2);
        offenceMode_12.setJeep_offence_fee(CHILD_TITLE_12_3);
        offenceMode_12.setBus_offence_fee(CHILD_TITLE_12_4);
        offenceMode_12.setTrolley_offence_fee(CHILD_TITLE_12_5);
        offenceMode_12.setTruck_offence_fee(CHILD_TITLE_12_6);


        OffenceModel offenceMode_13=new OffenceModel();
        offenceMode_13.setOffence_title(PARENT_TITLE_13);
        offenceMode_13.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_13_URUD));

        offenceMode_13.setBike_offence_fee(CHILD_TITLE_13_1);
        offenceMode_13.setCar_offence_fee(CHILD_TITLE_13_2);
        offenceMode_13.setJeep_offence_fee(CHILD_TITLE_13_3);
        offenceMode_13.setBus_offence_fee(CHILD_TITLE_13_4);
        offenceMode_13.setTrolley_offence_fee(CHILD_TITLE_13_5);
        offenceMode_13.setTruck_offence_fee(CHILD_TITLE_13_6);


        OffenceModel offenceMode_14=new OffenceModel();
        offenceMode_14.setOffence_title(PARENT_TITLE_14);
        offenceMode_14.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_14_URDU));
        offenceMode_14.setBike_offence_fee(CHILD_TITLE_14_1);
        offenceMode_14.setCar_offence_fee(CHILD_TITLE_14_2);
        offenceMode_14.setJeep_offence_fee(CHILD_TITLE_14_3);
        offenceMode_14.setBus_offence_fee(CHILD_TITLE_14_4);
        offenceMode_14.setTrolley_offence_fee(CHILD_TITLE_14_5);
        offenceMode_14.setTruck_offence_fee(CHILD_TITLE_14_6);


        OffenceModel offenceMode_15=new OffenceModel();
        offenceMode_15.setOffence_title(PARENT_TITLE_15);
        offenceMode_15.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_15_URDU));

        offenceMode_15.setBike_offence_fee(CHILD_TITLE_15_1);
        offenceMode_15.setCar_offence_fee(CHILD_TITLE_15_2);
        offenceMode_15.setJeep_offence_fee(CHILD_TITLE_15_3);
        offenceMode_15.setBus_offence_fee(CHILD_TITLE_15_4);
        offenceMode_15.setTrolley_offence_fee(CHILD_TITLE_15_5);
        offenceMode_15.setTruck_offence_fee(CHILD_TITLE_15_6);


        OffenceModel offenceMode_16=new OffenceModel();
        offenceMode_16.setOffence_title(PARENT_TITLE_16);
        offenceMode_16.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_16_URDU));
        offenceMode_16.setBike_offence_fee(CHILD_TITLE_16_1);
        offenceMode_16.setCar_offence_fee(CHILD_TITLE_16_2);
        offenceMode_16.setJeep_offence_fee(CHILD_TITLE_16_3);
        offenceMode_16.setBus_offence_fee(CHILD_TITLE_16_4);
        offenceMode_16.setTrolley_offence_fee(CHILD_TITLE_16_5);
        offenceMode_16.setTruck_offence_fee(CHILD_TITLE_16_6);


        OffenceModel offenceMode_17=new OffenceModel();
        offenceMode_17.setOffence_title(PARENT_TITLE_17);
        offenceMode_17.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_17_URDU));
        offenceMode_17.setBike_offence_fee(CHILD_TITLE_17_1);
        offenceMode_17.setCar_offence_fee(CHILD_TITLE_17_2);
        offenceMode_17.setJeep_offence_fee(CHILD_TITLE_17_3);
        offenceMode_17.setBus_offence_fee(CHILD_TITLE_17_4);
        offenceMode_17.setTrolley_offence_fee(CHILD_TITLE_17_5);
        offenceMode_17.setTruck_offence_fee(CHILD_TITLE_17_6);


        OffenceModel offenceMode_18=new OffenceModel();
        offenceMode_18.setOffence_title(PARENT_TITLE_18);
        offenceMode_18.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_18_URDU));
        offenceMode_18.setBike_offence_fee(CHILD_TITLE_18_1);
        offenceMode_18.setCar_offence_fee(CHILD_TITLE_18_2);
        offenceMode_18.setJeep_offence_fee(CHILD_TITLE_18_3);
        offenceMode_18.setBus_offence_fee(CHILD_TITLE_18_4);
        offenceMode_18.setTrolley_offence_fee(CHILD_TITLE_18_5);
        offenceMode_18.setTruck_offence_fee(CHILD_TITLE_18_6);


        OffenceModel offenceMode_19=new OffenceModel();
        offenceMode_19.setOffence_title(PARENT_TITLE_19);
        offenceMode_19.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_19_URDU));

        offenceMode_19.setBike_offence_fee(CHILD_TITLE_19_1);
        offenceMode_19.setCar_offence_fee(CHILD_TITLE_19_2);
        offenceMode_19.setJeep_offence_fee(CHILD_TITLE_19_3);
        offenceMode_19.setBus_offence_fee(CHILD_TITLE_19_4);
        offenceMode_19.setTrolley_offence_fee(CHILD_TITLE_19_5);
        offenceMode_19.setTruck_offence_fee(CHILD_TITLE_19_6);


        OffenceModel offenceMode_20=new OffenceModel();
        offenceMode_20.setOffence_title(PARENT_TITLE_20);
        offenceMode_20.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_20_URDU));

        offenceMode_20.setBike_offence_fee(CHILD_TITLE_20_1);
        offenceMode_20.setCar_offence_fee(CHILD_TITLE_20_2);
        offenceMode_20.setJeep_offence_fee(CHILD_TITLE_20_3);
        offenceMode_20.setBus_offence_fee(CHILD_TITLE_20_4);
        offenceMode_20.setTrolley_offence_fee(CHILD_TITLE_20_5);
        offenceMode_20.setTruck_offence_fee(CHILD_TITLE_20_6);


        OffenceModel offenceMode_21=new OffenceModel();
        offenceMode_21.setOffence_title(PARENT_TITLE_21);
        offenceMode_21.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_21_URUD));

        offenceMode_21.setBike_offence_fee(CHILD_TITLE_21_1);
        offenceMode_21.setCar_offence_fee(CHILD_TITLE_21_2);
        offenceMode_21.setJeep_offence_fee(CHILD_TITLE_21_3);
        offenceMode_21.setBus_offence_fee(CHILD_TITLE_21_4);
        offenceMode_21.setTrolley_offence_fee(CHILD_TITLE_21_5);
        offenceMode_21.setTruck_offence_fee(CHILD_TITLE_21_6);


        OffenceModel offenceMode_22=new OffenceModel();
        offenceMode_22.setOffence_title(PARENT_TITLE_22);
        offenceMode_22.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_22_URDU));

        offenceMode_22.setBike_offence_fee(CHILD_TITLE_22_1);
        offenceMode_22.setCar_offence_fee(CHILD_TITLE_22_2);
        offenceMode_22.setJeep_offence_fee(CHILD_TITLE_22_3);
        offenceMode_22.setBus_offence_fee(CHILD_TITLE_22_4);
        offenceMode_22.setTrolley_offence_fee(CHILD_TITLE_22_5);
        offenceMode_22.setTruck_offence_fee(CHILD_TITLE_22_6);


        OffenceModel offenceMode_23=new OffenceModel();
        offenceMode_23.setOffence_title(PARENT_TITLE_23);
        offenceMode_23.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_23_URDU));

        offenceMode_23.setBike_offence_fee(CHILD_TITLE_23_1);
        offenceMode_23.setCar_offence_fee(CHILD_TITLE_23_2);
        offenceMode_23.setJeep_offence_fee(CHILD_TITLE_23_3);
        offenceMode_23.setBus_offence_fee(CHILD_TITLE_23_4);
        offenceMode_23.setTrolley_offence_fee(CHILD_TITLE_23_5);
        offenceMode_23.setTruck_offence_fee(CHILD_TITLE_23_6);


        OffenceModel offenceMode_24=new OffenceModel();
        offenceMode_24.setOffence_title(PARENT_TITLE_24);
        offenceMode_24.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_24_URDU));
        offenceMode_24.setBike_offence_fee(CHILD_TITLE_24_1);
        offenceMode_24.setCar_offence_fee(CHILD_TITLE_24_2);
        offenceMode_24.setJeep_offence_fee(CHILD_TITLE_24_3);
        offenceMode_24.setBus_offence_fee(CHILD_TITLE_24_4);
        offenceMode_24.setTrolley_offence_fee(CHILD_TITLE_24_5);
        offenceMode_24.setTruck_offence_fee(CHILD_TITLE_24_6);


        OffenceModel offenceMode_25=new OffenceModel();
        offenceMode_25.setOffence_title(PARENT_TITLE_25);
        offenceMode_25.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_25_URDU));

        offenceMode_25.setBike_offence_fee(CHILD_TITLE_25_1);
        offenceMode_25.setCar_offence_fee(CHILD_TITLE_25_2);
        offenceMode_25.setJeep_offence_fee(CHILD_TITLE_25_3);
        offenceMode_25.setBus_offence_fee(CHILD_TITLE_25_4);
        offenceMode_25.setTrolley_offence_fee(CHILD_TITLE_25_5);
        offenceMode_25.setTruck_offence_fee(CHILD_TITLE_25_6);


        OffenceModel offenceMode_26=new OffenceModel();
        offenceMode_26.setOffence_title(PARENT_TITLE_26);
        offenceMode_26.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_26_URDU));

        offenceMode_26.setBike_offence_fee(CHILD_TITLE_26_1);
        offenceMode_26.setCar_offence_fee(CHILD_TITLE_26_2);
        offenceMode_26.setJeep_offence_fee(CHILD_TITLE_26_3);
        offenceMode_26.setBus_offence_fee(CHILD_TITLE_26_4);
        offenceMode_26.setTrolley_offence_fee(CHILD_TITLE_26_5);
        offenceMode_26.setTruck_offence_fee(CHILD_TITLE_26_6);


        OffenceModel offenceMode_27=new OffenceModel();
        offenceMode_27.setOffence_title(PARENT_TITLE_27);
        offenceMode_27.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_27_URDU));

        offenceMode_27.setBike_offence_fee(CHILD_TITLE_27_1);
        offenceMode_27.setCar_offence_fee(CHILD_TITLE_27_2);
        offenceMode_27.setJeep_offence_fee(CHILD_TITLE_27_3);
        offenceMode_27.setBus_offence_fee(CHILD_TITLE_27_4);
        offenceMode_27.setTrolley_offence_fee(CHILD_TITLE_27_5);
        offenceMode_27.setTruck_offence_fee(CHILD_TITLE_27_6);


        OffenceModel offenceMode_28=new OffenceModel();
        offenceMode_28.setOffence_title(PARENT_TITLE_28);
        offenceMode_28.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_28_URDU));

        offenceMode_28.setBike_offence_fee(CHILD_TITLE_28_1);
        offenceMode_28.setCar_offence_fee(CHILD_TITLE_28_2);
        offenceMode_28.setJeep_offence_fee(CHILD_TITLE_28_3);
        offenceMode_28.setBus_offence_fee(CHILD_TITLE_28_4);
        offenceMode_28.setTrolley_offence_fee(CHILD_TITLE_28_5);
        offenceMode_28.setTruck_offence_fee(CHILD_TITLE_28_6);


        OffenceModel offenceMode_29=new OffenceModel();
        offenceMode_29.setOffence_title(PARENT_TITLE_29);
        offenceMode_29.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_29_URDU));

        offenceMode_29.setBike_offence_fee(CHILD_TITLE_29_1);
        offenceMode_29.setCar_offence_fee(CHILD_TITLE_29_2);
        offenceMode_29.setJeep_offence_fee(CHILD_TITLE_29_3);
        offenceMode_29.setBus_offence_fee(CHILD_TITLE_29_4);
        offenceMode_29.setTrolley_offence_fee(CHILD_TITLE_29_5);
        offenceMode_29.setTruck_offence_fee(CHILD_TITLE_29_6);


        OffenceModel offenceMode_30=new OffenceModel();
        offenceMode_30.setOffence_title(PARENT_TITLE_30);
        offenceMode_30.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_30_URDU));
        offenceMode_30.setBike_offence_fee(CHILD_TITLE_30_1);
        offenceMode_30.setCar_offence_fee(CHILD_TITLE_30_2);
        offenceMode_30.setJeep_offence_fee(CHILD_TITLE_30_3);
        offenceMode_30.setBus_offence_fee(CHILD_TITLE_30_4);
        offenceMode_30.setTrolley_offence_fee(CHILD_TITLE_30_5);
        offenceMode_30.setTruck_offence_fee(CHILD_TITLE_30_6);


        OffenceModel offenceMode_31=new OffenceModel();
        offenceMode_31.setOffence_title(PARENT_TITLE_31);
        offenceMode_31.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_31_URDU));

        offenceMode_31.setBike_offence_fee(CHILD_TITLE_31_1);
        offenceMode_31.setCar_offence_fee(CHILD_TITLE_31_2);
        offenceMode_31.setJeep_offence_fee(CHILD_TITLE_31_3);
        offenceMode_31.setBus_offence_fee(CHILD_TITLE_31_4);
        offenceMode_31.setTrolley_offence_fee(CHILD_TITLE_31_5);
        offenceMode_31.setTruck_offence_fee(CHILD_TITLE_31_6);


        OffenceModel offenceMode_32=new OffenceModel();
        offenceMode_32.setOffence_title(PARENT_TITLE_32);
        offenceMode_32.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_32_URDU));
        offenceMode_32.setBike_offence_fee(CHILD_TITLE_32_1);
        offenceMode_32.setCar_offence_fee(CHILD_TITLE_32_2);
        offenceMode_32.setJeep_offence_fee(CHILD_TITLE_32_3);
        offenceMode_32.setBus_offence_fee(CHILD_TITLE_32_4);
        offenceMode_32.setTrolley_offence_fee(CHILD_TITLE_32_5);
        offenceMode_32.setTruck_offence_fee(CHILD_TITLE_32_6);



        OffenceModel offenceMode_33=new OffenceModel();
        offenceMode_33.setOffence_title(PARENT_TITLE_33);
        offenceMode_33.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_33_URDU));
        offenceMode_33.setBike_offence_fee(CHILD_TITLE_33_1);
        offenceMode_33.setCar_offence_fee(CHILD_TITLE_33_2);
        offenceMode_33.setJeep_offence_fee(CHILD_TITLE_33_3);
        offenceMode_33.setBus_offence_fee(CHILD_TITLE_33_4);
        offenceMode_33.setTrolley_offence_fee(CHILD_TITLE_33_5);
        offenceMode_33.setTruck_offence_fee(CHILD_TITLE_33_6);


        OffenceModel offenceMode_34=new OffenceModel();
        offenceMode_34.setOffence_title(PARENT_TITLE_34);
        offenceMode_34.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_34_URDU));

        offenceMode_34.setBike_offence_fee(CHILD_TITLE_34_1);
        offenceMode_34.setCar_offence_fee(CHILD_TITLE_34_2);
        offenceMode_34.setJeep_offence_fee(CHILD_TITLE_34_3);
        offenceMode_34.setBus_offence_fee(CHILD_TITLE_34_4);
        offenceMode_34.setTrolley_offence_fee(CHILD_TITLE_34_5);
        offenceMode_34.setTruck_offence_fee(CHILD_TITLE_34_6);


        OffenceModel offenceMode_35=new OffenceModel();
        offenceMode_35.setOffence_title(PARENT_TITLE_35);
        offenceMode_35.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_35_URDU));

        offenceMode_35.setBike_offence_fee(CHILD_TITLE_35_1);
        offenceMode_35.setCar_offence_fee(CHILD_TITLE_35_2);
        offenceMode_35.setJeep_offence_fee(CHILD_TITLE_35_3);
        offenceMode_35.setBus_offence_fee(CHILD_TITLE_35_4);
        offenceMode_35.setTrolley_offence_fee(CHILD_TITLE_35_5);
        offenceMode_35.setTruck_offence_fee(CHILD_TITLE_35_6);


        OffenceModel offenceMode_36=new OffenceModel();
        offenceMode_36.setOffence_title(PARENT_TITLE_36);
        offenceMode_36.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_36_URDU));
        offenceMode_36.setBike_offence_fee(CHILD_TITLE_36_1);
        offenceMode_36.setCar_offence_fee(CHILD_TITLE_36_2);
        offenceMode_36.setJeep_offence_fee(CHILD_TITLE_36_3);
        offenceMode_36.setBus_offence_fee(CHILD_TITLE_36_4);
        offenceMode_36.setTrolley_offence_fee(CHILD_TITLE_36_5);
        offenceMode_36.setTruck_offence_fee(CHILD_TITLE_36_6);


        OffenceModel offenceMode_37=new OffenceModel();
        offenceMode_37.setOffence_title(PARENT_TITLE_37);
        offenceMode_37.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_37_URDU));

        offenceMode_37.setBike_offence_fee(CHILD_TITLE_37_1);
        offenceMode_37.setCar_offence_fee(CHILD_TITLE_37_2);
        offenceMode_37.setJeep_offence_fee(CHILD_TITLE_37_3);
        offenceMode_37.setBus_offence_fee(CHILD_TITLE_37_4);
        offenceMode_37.setTrolley_offence_fee(CHILD_TITLE_37_5);
        offenceMode_37.setTruck_offence_fee(CHILD_TITLE_37_6);


        OffenceModel offenceMode_38=new OffenceModel();
        offenceMode_38.setOffence_title(PARENT_TITLE_38);
        offenceMode_38.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_38_URDU));
        offenceMode_38.setBike_offence_fee(CHILD_TITLE_38_1);
        offenceMode_38.setCar_offence_fee(CHILD_TITLE_38_2);
        offenceMode_38.setJeep_offence_fee(CHILD_TITLE_38_3);
        offenceMode_38.setBus_offence_fee(CHILD_TITLE_38_4);
        offenceMode_38.setTrolley_offence_fee(CHILD_TITLE_38_5);
        offenceMode_38.setTruck_offence_fee(CHILD_TITLE_38_6);


        OffenceModel offenceMode_39=new OffenceModel();
        offenceMode_39.setOffence_title(PARENT_TITLE_39);
        offenceMode_39.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_39_URDU));
        offenceMode_39.setBike_offence_fee(CHILD_TITLE_39_1);
        offenceMode_39.setCar_offence_fee(CHILD_TITLE_39_2);
        offenceMode_39.setJeep_offence_fee(CHILD_TITLE_39_3);
        offenceMode_39.setBus_offence_fee(CHILD_TITLE_39_4);
        offenceMode_39.setTrolley_offence_fee(CHILD_TITLE_39_5);
        offenceMode_39.setTruck_offence_fee(CHILD_TITLE_39_6);


        OffenceModel offenceMode_40=new OffenceModel();
        offenceMode_40.setOffence_title(PARENT_TITLE_40);
        offenceMode_40.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_40_URDU));
        offenceMode_40.setBike_offence_fee(CHILD_TITLE_40_1);
        offenceMode_40.setCar_offence_fee(CHILD_TITLE_40_2);
        offenceMode_40.setJeep_offence_fee(CHILD_TITLE_40_3);
        offenceMode_40.setBus_offence_fee(CHILD_TITLE_40_4);
        offenceMode_40.setTrolley_offence_fee(CHILD_TITLE_40_5);
        offenceMode_40.setTruck_offence_fee(CHILD_TITLE_40_6);


        OffenceModel offenceMode_41=new OffenceModel();
        offenceMode_41.setOffence_title(PARENT_TITLE_41);
        offenceMode_41.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_41_URDU));
        offenceMode_41.setBike_offence_fee(CHILD_TITLE_41_1);
        offenceMode_41.setCar_offence_fee(CHILD_TITLE_41_2);
        offenceMode_41.setJeep_offence_fee(CHILD_TITLE_41_3);
        offenceMode_41.setBus_offence_fee(CHILD_TITLE_41_4);
        offenceMode_41.setTrolley_offence_fee(CHILD_TITLE_41_5);
        offenceMode_41.setTruck_offence_fee(CHILD_TITLE_41_6);



        OffenceModel offenceMode_42=new OffenceModel();
        offenceMode_42.setOffence_title(PARENT_TITLE_42);
        offenceMode_42.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_42_URDU));
        offenceMode_42.setBike_offence_fee(CHILD_TITLE_42_1);
        offenceMode_42.setCar_offence_fee(CHILD_TITLE_42_2);
        offenceMode_42.setJeep_offence_fee(CHILD_TITLE_42_3);
        offenceMode_42.setBus_offence_fee(CHILD_TITLE_42_4);
        offenceMode_42.setTrolley_offence_fee(CHILD_TITLE_42_5);
        offenceMode_42.setTruck_offence_fee(CHILD_TITLE_42_6);


        OffenceModel offenceMode_43=new OffenceModel();
        offenceMode_43.setOffence_title(PARENT_TITLE_43);
        offenceMode_43.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_43_URDU));

        offenceMode_43.setBike_offence_fee(CHILD_TITLE_43_1);
        offenceMode_43.setCar_offence_fee(CHILD_TITLE_43_2);
        offenceMode_43.setJeep_offence_fee(CHILD_TITLE_43_3);
        offenceMode_43.setBus_offence_fee(CHILD_TITLE_43_4);
        offenceMode_43.setTrolley_offence_fee(CHILD_TITLE_43_5);
        offenceMode_43.setTruck_offence_fee(CHILD_TITLE_43_6);


        OffenceModel offenceMode_44=new OffenceModel();
        offenceMode_44.setOffence_title(PARENT_TITLE_44);
        offenceMode_44.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_44_URDU));
        offenceMode_44.setBike_offence_fee(CHILD_TITLE_44_1);
        offenceMode_44.setCar_offence_fee(CHILD_TITLE_44_2);
        offenceMode_44.setJeep_offence_fee(CHILD_TITLE_44_3);
        offenceMode_44.setBus_offence_fee(CHILD_TITLE_44_4);
        offenceMode_44.setTrolley_offence_fee(CHILD_TITLE_44_5);
        offenceMode_44.setTruck_offence_fee(CHILD_TITLE_44_6);


        OffenceModel offenceMode_45=new OffenceModel();
        offenceMode_45.setOffence_title(PARENT_TITLE_45);
        offenceMode_45.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_45_URDU));
        offenceMode_45.setBike_offence_fee(CHILD_TITLE_45_1);
        offenceMode_45.setCar_offence_fee(CHILD_TITLE_45_2);
        offenceMode_45.setJeep_offence_fee(CHILD_TITLE_45_3);
        offenceMode_45.setBus_offence_fee(CHILD_TITLE_45_4);
        offenceMode_45.setTrolley_offence_fee(CHILD_TITLE_45_5);
        offenceMode_45.setTruck_offence_fee(CHILD_TITLE_45_6);


        OffenceModel offenceMode_46=new OffenceModel();
        offenceMode_46.setOffence_title(PARENT_TITLE_46);
        offenceMode_46.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_46_URDU));
        offenceMode_46.setBike_offence_fee(CHILD_TITLE_46_1);
        offenceMode_46.setCar_offence_fee(CHILD_TITLE_46_2);
        offenceMode_46.setJeep_offence_fee(CHILD_TITLE_46_3);
        offenceMode_46.setBus_offence_fee(CHILD_TITLE_46_4);
        offenceMode_46.setTrolley_offence_fee(CHILD_TITLE_46_5);
        offenceMode_46.setTruck_offence_fee(CHILD_TITLE_46_6);


        OffenceModel offenceMode_47=new OffenceModel();
        offenceMode_47.setOffence_title(PARENT_TITLE_47);
        offenceMode_47.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_47_URDU));
        offenceMode_47.setBike_offence_fee(CHILD_TITLE_47_1);
        offenceMode_47.setCar_offence_fee(CHILD_TITLE_47_2);
        offenceMode_47.setJeep_offence_fee(CHILD_TITLE_47_3);
        offenceMode_47.setBus_offence_fee(CHILD_TITLE_47_4);
        offenceMode_47.setTrolley_offence_fee(CHILD_TITLE_47_5);
        offenceMode_47.setTruck_offence_fee(CHILD_TITLE_47_6);


        OffenceModel offenceMode_48=new OffenceModel();
        offenceMode_48.setOffence_title(PARENT_TITLE_48);
        offenceMode_48.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_48_URDU));
        offenceMode_48.setBike_offence_fee(CHILD_TITLE_48_1);
        offenceMode_48.setCar_offence_fee(CHILD_TITLE_48_2);
        offenceMode_48.setJeep_offence_fee(CHILD_TITLE_48_3);
        offenceMode_48.setBus_offence_fee(CHILD_TITLE_48_4);
        offenceMode_48.setTrolley_offence_fee(CHILD_TITLE_48_5);
        offenceMode_48.setTruck_offence_fee(CHILD_TITLE_48_6);


        OffenceModel offenceMode_49=new OffenceModel();
        offenceMode_49.setOffence_title(PARENT_TITLE_49);
        offenceMode_49.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_49_URDU));
        offenceMode_49.setBike_offence_fee(CHILD_TITLE_49_1);
        offenceMode_49.setCar_offence_fee(CHILD_TITLE_49_2);
        offenceMode_49.setJeep_offence_fee(CHILD_TITLE_49_3);
        offenceMode_49.setBus_offence_fee(CHILD_TITLE_49_4);
        offenceMode_49.setTrolley_offence_fee(CHILD_TITLE_49_5);
        offenceMode_49.setTruck_offence_fee(CHILD_TITLE_49_6);



        OffenceModel offenceMode_50=new OffenceModel();
        offenceMode_50.setOffence_title(PARENT_TITLE_50);
        offenceMode_50.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_50_URDU));

        offenceMode_50.setBike_offence_fee(CHILD_TITLE_50_1);
        offenceMode_50.setCar_offence_fee(CHILD_TITLE_50_2);
        offenceMode_50.setJeep_offence_fee(CHILD_TITLE_50_3);
        offenceMode_50.setBus_offence_fee(CHILD_TITLE_50_4);
        offenceMode_50.setTrolley_offence_fee(CHILD_TITLE_50_5);
        offenceMode_50.setTruck_offence_fee(CHILD_TITLE_50_6);


        OffenceModel offenceMode_51=new OffenceModel();
        offenceMode_51.setOffence_title(PARENT_TITLE_51);
        offenceMode_51.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_51_URDU));
        offenceMode_51.setBike_offence_fee(CHILD_TITLE_51_1);
        offenceMode_51.setCar_offence_fee(CHILD_TITLE_51_2);
        offenceMode_51.setJeep_offence_fee(CHILD_TITLE_51_3);
        offenceMode_51.setBus_offence_fee(CHILD_TITLE_51_4);
        offenceMode_51.setTrolley_offence_fee(CHILD_TITLE_51_5);
        offenceMode_51.setTruck_offence_fee(CHILD_TITLE_51_6);


        OffenceModel offenceMode_52=new OffenceModel();
        offenceMode_52.setOffence_title(PARENT_TITLE_52);
        offenceMode_52.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_52_URDU));
        offenceMode_52.setBike_offence_fee(CHILD_TITLE_52_1);
        offenceMode_52.setCar_offence_fee(CHILD_TITLE_52_2);
        offenceMode_52.setJeep_offence_fee(CHILD_TITLE_52_3);
        offenceMode_52.setBus_offence_fee(CHILD_TITLE_52_4);
        offenceMode_52.setTrolley_offence_fee(CHILD_TITLE_52_5);
        offenceMode_52.setTruck_offence_fee(CHILD_TITLE_52_6);


        OffenceModel offenceMode_53=new OffenceModel();
        offenceMode_53.setOffence_title(PARENT_TITLE_53);
        offenceMode_53.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_53_URDU));

        offenceMode_53.setBike_offence_fee(CHILD_TITLE_53_1);
        offenceMode_53.setCar_offence_fee(CHILD_TITLE_53_2);
        offenceMode_53.setJeep_offence_fee(CHILD_TITLE_53_3);
        offenceMode_53.setBus_offence_fee(CHILD_TITLE_53_4);
        offenceMode_53.setTrolley_offence_fee(CHILD_TITLE_53_5);
        offenceMode_53.setTruck_offence_fee(CHILD_TITLE_53_6);


        OffenceModel offenceMode_54=new OffenceModel();
        offenceMode_54.setOffence_title(PARENT_TITLE_54);
        offenceMode_54.setOffence_title_URDU( getResources().getString(R.string.PARENT_TITLE_54_URDU));
        offenceMode_54.setBike_offence_fee(CHILD_TITLE_54_1);
        offenceMode_54.setCar_offence_fee(CHILD_TITLE_54_2);
        offenceMode_54.setJeep_offence_fee(CHILD_TITLE_54_3);
        offenceMode_54.setBus_offence_fee(CHILD_TITLE_54_4);
        offenceMode_54.setTrolley_offence_fee(CHILD_TITLE_54_5);
        offenceMode_54.setTruck_offence_fee(CHILD_TITLE_54_6);




        offenceModelArrayList.add(exceeding_limit_OffenceModel);
        offenceModelArrayList.add(offenceModel_2);
        offenceModelArrayList.add(offenceModel_3);
        offenceModelArrayList.add(offenceModel_4);
        offenceModelArrayList.add(offenceModel_6);
        offenceModelArrayList.add(offenceModel_7);
        offenceModelArrayList.add(offenceModel_8);
        offenceModelArrayList.add(offenceModel_9);
        offenceModelArrayList.add(offenceModel_10);
        offenceModelArrayList.add(offenceMode_11);
        offenceModelArrayList.add(offenceMode_12);
        offenceModelArrayList.add(offenceMode_13);
        offenceModelArrayList.add(offenceMode_14);
        offenceModelArrayList.add(offenceMode_15);
        offenceModelArrayList.add(offenceMode_16);
        offenceModelArrayList.add(offenceMode_17);
        offenceModelArrayList.add(offenceMode_18);
        offenceModelArrayList.add(offenceMode_19);
        offenceModelArrayList.add(offenceMode_20);
        offenceModelArrayList.add(offenceMode_21);
        offenceModelArrayList.add(offenceMode_22);
        offenceModelArrayList.add(offenceMode_23);
        offenceModelArrayList.add(offenceMode_24);
        offenceModelArrayList.add(offenceMode_25);
        offenceModelArrayList.add(offenceMode_26);
        offenceModelArrayList.add(offenceMode_27);
        offenceModelArrayList.add(offenceMode_28);
        offenceModelArrayList.add(offenceMode_29);
        offenceModelArrayList.add(offenceMode_30);
        offenceModelArrayList.add(offenceMode_31);
        offenceModelArrayList.add(offenceMode_32);
        offenceModelArrayList.add(offenceMode_33);
        offenceModelArrayList.add(offenceMode_34);
        offenceModelArrayList.add(offenceMode_35);
        offenceModelArrayList.add(offenceMode_36);
        offenceModelArrayList.add(offenceMode_37);
        offenceModelArrayList.add(offenceMode_38);
        offenceModelArrayList.add(offenceMode_39);
        offenceModelArrayList.add(offenceMode_40);
        offenceModelArrayList.add(offenceMode_41);
        offenceModelArrayList.add(offenceMode_42);
        offenceModelArrayList.add(offenceMode_43);
        offenceModelArrayList.add(offenceMode_44);
        offenceModelArrayList.add(offenceMode_45);
        offenceModelArrayList.add(offenceMode_46);
        offenceModelArrayList.add(offenceMode_47);
        offenceModelArrayList.add(offenceMode_48);
        offenceModelArrayList.add(offenceMode_49);
        offenceModelArrayList.add(offenceMode_50);
        offenceModelArrayList.add(offenceMode_51);
        offenceModelArrayList.add(offenceMode_52);
        offenceModelArrayList.add(offenceMode_53);
        offenceModelArrayList.add(offenceMode_54);
        mAdapter.notifyDataSetChanged();


    }


}
