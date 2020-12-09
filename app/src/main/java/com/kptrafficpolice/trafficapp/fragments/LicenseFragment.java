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

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
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
import com.kptrafficpolice.trafficapp.activities.QrCodeActivity;
import com.kptrafficpolice.trafficapp.utilities.CheckNetwork;
import com.kptrafficpolice.trafficapp.utilities.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

//raabta
//rabta
public class LicenseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tvLicHolderName, tvLicHolderFatherName, tvLicHolderDistrict, tvLicType, tvLicIssueDate,
            tvLisExpiryDate,tvCNICNumber,tvLicenseNumber,tv_issue_date,tv_initial_issue_date;
    private OnFragmentInteractionListener mListener;
    private FirebaseAnalytics mFirebaseAnalytics;

    String strCityName, strChallanTrackingID, strCheckLatLon, strLicenseNumber, strCNIC, strResponseLicenseID, strResponseDLNumber,
            strResponseCNIC, strResponseLicHolderName, strResponseLicHolderFatherName, strResponseLicType,
            strResponseExpiryDate, strResponseLicHolderDistrict;
    private String strResponseLicHolderIssueDate;
    private String strResponseLicHolderExpiry_date;

    EditText etLicNumber;

    SweetAlertDialog pDialog;
    private LicenseFragment fragment;
    RequestQueue mRequestQueue;
    private Dialog dialog;
    Button btnShowRecord;
    private Button btnShowRecordQr,search_again_btn;
    private Animation shake;


    public LicenseFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LicenseFragment newInstance(String param1, String param2) {
        LicenseFragment fragment = new LicenseFragment();
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
        view = inflater.inflate(R.layout.fragment_license, container, false);
        tvLicHolderName = (TextView) view.findViewById(R.id.tv_lh_name);
        tvLicHolderFatherName = (TextView) view.findViewById(R.id.tv_lh_fname);
        tvLicType = (TextView) view.findViewById(R.id.tv_license_type);
        tvLicHolderDistrict = (TextView) view.findViewById(R.id.tv_lh_district);
        tvLisExpiryDate = (TextView) view.findViewById(R.id.tv_expiry_date);
        tvLicenseNumber = (TextView)view.findViewById(R.id.tv_license_number);
        tvCNICNumber = (TextView)view.findViewById(R.id.tv_lh_cnic_number);
        tv_issue_date = (TextView)view.findViewById(R.id.tv_issue_date);
        tv_initial_issue_date = (TextView)view.findViewById(R.id.tv_initial_issue_date);
        search_again_btn = (Button) view.findViewById(R.id.search_again_btn);

        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setCancelable(false);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "4");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "License Status Checked");
        mFirebaseAnalytics.logEvent("License_checked", bundle);


        Bundle args = new Bundle(getArguments());
        tvLicHolderName.setText(String.valueOf(args.get("name")));
        tvLicHolderDistrict.setText(String.valueOf(args.get("district")));
        tvLicHolderFatherName.setText(String.valueOf(args.get("f_name")));
        tvLisExpiryDate.setText(String.valueOf(args.get("expiry_date")));
        tvLicType.setText(String.valueOf(args.get("lic_type")));
        tvCNICNumber.setText(String.valueOf(args.get("cnic")));
        tvLicenseNumber.setText(String.valueOf(args.get("license_number")));
        tv_issue_date.setText(String.valueOf(args.get("issue_date")));
        tv_initial_issue_date.setText(String.valueOf(args.get("lic_type")+"/"+args.get("initial_issue_date")));


        search_again_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogLicenseVerification();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void customDialogLicenseVerification() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_input_dialog);
        dialog.setCancelable(true);
        etLicNumber = (EditText) dialog.findViewById(R.id.et_verify_license);
        btnShowRecord = (Button) dialog.findViewById(R.id.btn_search_license_record);
        btnShowRecordQr= (Button) dialog.findViewById(R.id.btn_search_license_record_qr);
        ImageView ivInputIcon = (ImageView) dialog.findViewById(R.id.iv_input_dialog);
        ivInputIcon.setImageResource(R.drawable.search_license_icon);
        dialog.show();
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        inputValidationLicense();
    }

    public void inputValidationLicense() {
        btnShowRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strLicenseNumber = etLicNumber.getText().toString();
                if (strLicenseNumber.toString().equals("")
                        || strLicenseNumber.toString().length() < 13) {
                    //  Log.d("zma else", strLicenseNumber);
                    etLicNumber.startAnimation(shake);
                } else if (strLicenseNumber.toString().length() == 13) {
                    strCNIC = strLicenseNumber;
                    if (CheckNetwork.isInternetAvailable(getActivity())) {
                        apiCallLicense(strCNIC);
                        pDialog.show();
                        dialog.dismiss();
                    } else {
                        pDialog.dismiss();
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


        btnShowRecordQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();
                startActivity(new Intent(getActivity(), QrCodeActivity.class));
                getActivity().finish();
            }
        });

    }

    public void apiCallLicense(final String cnic) {
        pDialog.dismiss();
        pDialog.setTitleText("Verifying Your License");


        String url = Configuration.LICENSE_URL + cnic;
        final Bundle args = new Bundle();
        final StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            pDialog.dismiss();
                            JSONObject jsonResponseObject = new JSONObject(response);
                            String fullResponseObject = jsonResponseObject.get("LICENSE_DATA").toString();

                            Log.e("jObject", response);
                            Log.e("string", fullResponseObject);

                            if (jsonResponseObject.get("error").equals("0")) {

                                JSONObject obj = new JSONObject(fullResponseObject);

                                strResponseLicHolderName = obj.getString("name");
                                strResponseDLNumber = obj.getString("license_no");
                                strResponseLicHolderFatherName = obj.getString("father_name");
                                strResponseLicType = obj.getString("license_type");
                                strResponseExpiryDate = obj.getString("expiry_date");
                                strResponseLicHolderDistrict = obj.getString("district");
                                strResponseLicHolderIssueDate = obj.getString("issue_date");
                                strResponseLicHolderExpiry_date = obj.getString("initial_issue_date");

                                args.putString("name", strResponseLicHolderName);
                                args.putString("f_name", strResponseLicHolderFatherName);
                                args.putString("cnic", cnic);
                                args.putString("license_number", strResponseDLNumber);
                                args.putString("lic_type", strResponseLicType);
                                args.putString("expiry_date", strResponseExpiryDate);
                                args.putString("district", strResponseLicHolderDistrict);
                                args.putString("issue_date", strResponseLicHolderIssueDate);
                                args.putString("initial_issue_date", strResponseLicHolderExpiry_date);
                                fragment = new LicenseFragment();
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
                                fragment.setArguments(args);
                            } else {

                                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("CNIC not found.")
                                        .setContentText("")
                                        .show();
                            }
                            //TODO extract data from jsonarray data

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.d("zma status lic", String.valueOf(response));
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("CNIC not found.")
                                    .setContentText("")
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Server Not Responding.")
                        .setContentText("")
                        .show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cnic", cnic);
                Log.d("zma params", String.valueOf(params));
                return params;
            }

        };
        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjRequest);
    }


}
