package com.kptrafficpolice.trafficapp.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.kptrafficpolice.trafficapp.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrivingSchoolLocations#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrivingSchoolLocations extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button firstDirection,secondDirection;

    public DrivingSchoolLocations() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrivingSchoolLocations.
     */
    // TODO: Rename and change types and number of parameters
    public static DrivingSchoolLocations newInstance(String param1, String param2) {
        DrivingSchoolLocations fragment = new DrivingSchoolLocations();
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
        View view= inflater.inflate(R.layout.fragment_driving_school_locations, container, false);
        customActionBar();
        firstDirection=view.findViewById(R.id.firstDirection);
        secondDirection=view.findViewById(R.id.secondDirection);
        firstDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String my_data= String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=33.9918899,71.4680156(Driving Learning School West Cantt Police Station Saddar, Peshawar)");

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(my_data));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });

        secondDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String my_data= String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=34.0014486,71.544826(My Destination Place)");

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(my_data));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
        return view;
    }
    public void customActionBar() {
       ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Driving School Directions");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
}