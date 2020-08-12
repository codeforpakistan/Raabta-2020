package com.kptrafficpolice.trafficapp.fragments;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kptrafficpolice.trafficapp.R;
//raabta
//rabta
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChallanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChallanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChallanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    TextView tvChallanDate, tvChallanDistrict, tvOfficerName, tvDutyPoint,
            tvChallanAmount, tvChallanStatus;
    RequestQueue mRequestQueue;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAnalytics mFirebaseAnalytics;
    private OnFragmentInteractionListener mListener;

    public ChallanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChallanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChallanFragment newInstance(String param1, String param2) {
        ChallanFragment fragment = new ChallanFragment();
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
        view = inflater.inflate(R.layout.fragment_challan, container, false);
        customActionBar();
        Bundle args = new Bundle(getArguments());
        tvChallanDate = (TextView) view.findViewById(R.id.tv_challan_date);
        tvChallanDistrict = (TextView) view.findViewById(R.id.tv_challan_district);
        tvDutyPoint = (TextView) view.findViewById(R.id.tv_duty_point);
        tvOfficerName = (TextView) view.findViewById(R.id.tv_officer_name);
        tvChallanAmount = (TextView) view.findViewById(R.id.tv_challan_amount);
        tvChallanStatus = (TextView) view.findViewById(R.id.tv_challan_status);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "5");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Challan Status Checked");
        mFirebaseAnalytics.logEvent("Challan_checked", bundle);

        tvChallanDate.setText(String.valueOf(args.getString("date")));
        tvOfficerName.setText(String.valueOf(args.get("name")));
        tvChallanDistrict.setText(String.valueOf(args.get("district")));
        tvChallanAmount.setText(String.valueOf(args.get("amount")));
        tvDutyPoint.setText(String.valueOf(args.get("dutyPoint")));
        String strChallanStatus = String.valueOf(args.get("status"));
        if (strChallanStatus.equals("Paid")) {
            tvChallanStatus.setTextColor(Color.parseColor("#13988a"));
            tvChallanStatus.setTextSize(20);
            tvChallanStatus.setText(String.valueOf(args.get("status")));

        }
        else{
            tvChallanStatus.setTextColor(Color.parseColor("#13988a"));
            tvChallanStatus.setTextSize(20);
            tvChallanStatus.setText("Unpaid");
            tvChallanStatus.setTextColor(Color.RED);
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void customActionBar() {
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Your challan status");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
