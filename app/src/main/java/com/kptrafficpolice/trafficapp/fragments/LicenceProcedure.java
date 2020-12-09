package com.kptrafficpolice.trafficapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.activities.LicenseProcedureResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LicenceProcedure#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LicenceProcedure extends Fragment implements View.OnClickListener {

    LinearLayout learner_license_permit,permanent_driving_license,light_transport_license
            ,diplomat_non_deplomat_license,forign_license_procedure
            ,afghan_license_procedure;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LicenceProcedure() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LicenceProcedure.
     */
    // TODO: Rename and change types and number of parameters
    public static LicenceProcedure newInstance(String param1, String param2) {
        LicenceProcedure fragment = new LicenceProcedure();
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
        View view= inflater.inflate(R.layout.fragment_licence_procedure, container, false);
        learner_license_permit=view.findViewById(R.id.learner_license_permit);
        permanent_driving_license=view.findViewById(R.id.permanent_driving_license);
        light_transport_license=view.findViewById(R.id.light_transport_license);
        diplomat_non_deplomat_license=view.findViewById(R.id.diplomat_non_deplomat_license);
        forign_license_procedure=view.findViewById(R.id.forign_license_procedure);
        afghan_license_procedure=view.findViewById(R.id.afghan_license_procedure);

        learner_license_permit.setOnClickListener(this);
        permanent_driving_license.setOnClickListener(this);
        light_transport_license.setOnClickListener(this);
        diplomat_non_deplomat_license.setOnClickListener(this);
        forign_license_procedure.setOnClickListener(this);
        afghan_license_procedure.setOnClickListener(this);
        return view;
    }

    public void customActionBar() {
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("FRESH LICENSE ISSUANCE");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.learner_license_permit:
                startActivity(new Intent(getActivity(), LicenseProcedureResult.class)
                .putExtra("key","1"));
                break;
            case R.id.permanent_driving_license:
                startActivity(new Intent(getActivity(), LicenseProcedureResult.class)
                        .putExtra("key","2"));
                break;
            case R.id.light_transport_license:
                startActivity(new Intent(getActivity(), LicenseProcedureResult.class)
                        .putExtra("key","3"));
                break;
            case R.id.diplomat_non_deplomat_license:
                startActivity(new Intent(getActivity(), LicenseProcedureResult.class)
                        .putExtra("key","4"));
                break;
            case R.id.forign_license_procedure:
                startActivity(new Intent(getActivity(), LicenseProcedureResult.class)
                        .putExtra("key","5"));

                break;
            case R.id.afghan_license_procedure:
                startActivity(new Intent(getActivity(), LicenseProcedureResult.class)
                        .putExtra("key","6"));
                break;
        }
    }
}