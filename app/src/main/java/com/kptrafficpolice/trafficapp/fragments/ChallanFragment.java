package com.kptrafficpolice.trafficapp.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.activities.MainDrawerActivity;
import com.kptrafficpolice.trafficapp.utilities.CheckNetwork;
import com.kptrafficpolice.trafficapp.utilities.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
    private Button search_again_btn;
    private Dialog dialog;
    private EditText etLicNumber;
    private Button btnShowRecord;
    private String strChallanTrackingID;
    Animation shake;
    private SweetAlertDialog pDialog;
    private String strChallanDistrict;
    private String strChallanStatus;
    private String strChallanAmount;
    private ChallanFragment fragment;

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
        search_again_btn = (Button) view.findViewById(R.id.search_again_btn);

        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setCancelable(false);
        mRequestQueue = Volley.newRequestQueue(getActivity());


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "5");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Challan Status Checked");
        mFirebaseAnalytics.logEvent("Challan_checked", bundle);

        tvChallanDate.setText(String.valueOf(args.getString("date")));
//        tvOfficerName.setText(String.valueOf(args.get("name")));
        tvOfficerName.setText("empty");
        tvChallanDistrict.setText(String.valueOf(args.get("district")));
        tvChallanAmount.setText(String.valueOf(args.get("amount")));
//        tvDutyPoint.setText(String.valueOf(args.get("dutyPoint")));
        tvDutyPoint.setText("empty");
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

        search_again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogChallanStatus();
            }
        });
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

    public void customDialogChallanStatus() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_input_dialog);
        dialog.setCancelable(true);
        ImageView ivInputIcon = (ImageView) dialog.findViewById(R.id.iv_input_dialog);
        ivInputIcon.setImageResource(R.drawable.search_challam_icon);
        etLicNumber = (EditText) dialog.findViewById(R.id.et_verify_license);
        Button btn_search_license_record_qr = (Button) dialog.findViewById(R.id.btn_search_license_record_qr);
        btn_search_license_record_qr.setVisibility(View.GONE);
        etLicNumber.setHint("Enter Challan Number");
        btnShowRecord = (Button) dialog.findViewById(R.id.btn_search_license_record);
        dialog.show();
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        inputValidationChallan();
    }

    public void inputValidationChallan() {
        btnShowRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strChallanTrackingID = etLicNumber.getText().toString();
                if (strChallanTrackingID.toString().equals("")
                        || strChallanTrackingID.toString().length() > 10) {
                    //  Log.d("zma else", strChallanTrackingID);
                    etLicNumber.startAnimation(shake);
                } else {
                    if (CheckNetwork.isInternetAvailable(getActivity())) {
                        apiCallChallan(strChallanTrackingID);
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("No Internet Connection")
                                .setConfirmText("Refresh")
                                .setCancelText("Cancel")
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();

                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        startActivity(new Intent(getActivity(), MainDrawerActivity.class));
                                        sweetAlertDialog.dismiss();
                                        getActivity().finish();

                                    }
                                })
                                .show();
                    }
                }

            }
        });
    }

    public void apiCallChallan(final String strChallanID) {
        pDialog.setTitleText("Verifying Challan ID");
        pDialog.show();
        String url = Configuration.END_POINT_LIVE_RabtaApp + "/GetChallanDetail?ChallanNo=" + strChallanID;
        Log.d("zma url", url);
        final Bundle args = new Bundle();
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(Configuration.TAG_APP,"onResponse MainFragment apiCallChallan "+response);
                        /*try {
                            JSONObject mainResponse = new JSONObject(response);
                            if (mainResponse.getBoolean("status")) {
                                pDialog.dismiss();
                                JSONObject data = mainResponse.getJSONObject("data");
                                strChallanDate = data.getString("date");
                                strChallanDistrict = data.getString("district");
                                strChallanName = data.getString("name");
                                strChallanDutyPoint = data.getString("duty_point");
                                String ticket_id = data.getString("ticket_id");
                                strChallanAmount = data.getString("amount");
                                strChallanStatus = data.getString("status");

                                args.putString("date", strChallanDate);
                                args.putString("district", strChallanDistrict);
                                args.putString("dutyPoint", strChallanDutyPoint);
                                args.putString("name", strChallanName);
                                args.putString("amount", strChallanAmount);
                                args.putString("status", strChallanStatus);
                                fragment = new ChallanFragment();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
                                fragment.setArguments(args);

                            } else {
                                pDialog.dismiss();
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Challan number not found")
                                        .show();
                            }
                        } catch (JSONException e) {
                            pDialog.dismiss();
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Invalid Challan number")
                                    .show();
                            e.printStackTrace();
                        }*/

                        try {
                            JSONObject mainJsonObject=new JSONObject(response);

                            String success=mainJsonObject.getString("Status");

                            if (success.matches("Success")){
                                pDialog.dismiss();
                                String CreationDate = mainJsonObject.getString("CreationDate");
                                strChallanDistrict =mainJsonObject.getString("District");
                                strChallanStatus =mainJsonObject.getString("ChallanStatus");
                                strChallanAmount =mainJsonObject.getString("Amount");

                                args.putString("date", CreationDate);
                                args.putString("district", strChallanDistrict);
//                                args.putString("dutyPoint", strChallanDutyPoint);
//                                args.putString("name", strChallanName);
                                args.putString("amount", strChallanAmount);
                                args.putString("status", strChallanStatus);
                                fragment = new ChallanFragment();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
                                fragment.setArguments(args);

                            }
                            else {
                                pDialog.dismiss();
                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText("Challan number not found")
                                        .show();

                            }
                            Log.d(Configuration.TAG_APP,"onResponse MainFragment success "+success);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener()

        {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Server Error")
                        .show();
                Log.d("zma error registration", String.valueOf(error));
            }
        });


        jsonObjRequest.setRetryPolicy(new

                DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        );
        mRequestQueue.add(jsonObjRequest);
    }



}
