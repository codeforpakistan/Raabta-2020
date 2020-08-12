package com.kptrafficpolice.trafficapp.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.activities.MainDrawerActivity;
import com.kptrafficpolice.trafficapp.activities.QrCodeActivity;
import com.kptrafficpolice.trafficapp.utilities.CheckNetwork;
import com.kptrafficpolice.trafficapp.utilities.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyLicenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyLicenseFragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Dialog dialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText etLicNumber;
    private Button btnShowRecord;
    private Button btnShowRecordQr;
    private String strLicenseNumber;
    private Animation shake;
    private String strCNIC;
    SweetAlertDialog pDialog;

    String strCityName, strChallanTrackingID, strCheckLatLon, strResponseLicenseID, strResponseDLNumber,
            strResponseCNIC, strResponseLicHolderName, strResponseLicHolderFatherName, strResponseLicType,
            strResponseExpiryDate, strResponseLicHolderDistrict;
    private LicenseFragment fragment;
    RequestQueue mRequestQueue;
    private SurfaceView mySurfaceView;
    private QREader qrEader;


    public MyLicenseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyLicenseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyLicenseFragment newInstance(String param1, String param2) {
        MyLicenseFragment fragment = new MyLicenseFragment();
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
                View view=inflater.inflate(R.layout.fragment_my_license, container, false);
        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        apiCallLicense("1430130677017");
        return view;

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
                pDialog.dismiss();

                startActivity(new Intent(getActivity(), QrCodeActivity.class));
            }
        });

    }



    public void apiCallLicense(final String cnic) {
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

                                args.putString("name", strResponseLicHolderName);
                                args.putString("f_name", strResponseLicHolderFatherName);
                                args.putString("cnic", cnic);
                                args.putString("license_number", strResponseDLNumber);
                                args.putString("lic_type", strResponseLicType);
                                args.putString("expiry_date", strResponseExpiryDate);
                                args.putString("district", strResponseLicHolderDistrict);
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


    public void customDialogQrLicenseVerification() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.qr_reader);
        dialog.setCancelable(true);

        TextView text = dialog.findViewById(R.id.code_info);


        // Setup SurfaceView
        // -----------------
        mySurfaceView = dialog.findViewById(R.id.camera_view);


        setupQREader();

        dialog.show();
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        qrEader.initAndStart(mySurfaceView);

        if (qrEader.isCameraRunning()) {
            text.setText("Start QREader");
            qrEader.stop();
        } else {
            text.setText("Stop QREader");
            qrEader.start();
        }


//        inputValidationLicense();


    }

    void setupQREader() {
        // Init QREader
        // ------------
        qrEader = new QREader.Builder(getActivity(), mySurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                Log.d("QREader", "Value : " + data);

            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mySurfaceView.getHeight())
                .width(mySurfaceView.getWidth())
                .build();
    }
}