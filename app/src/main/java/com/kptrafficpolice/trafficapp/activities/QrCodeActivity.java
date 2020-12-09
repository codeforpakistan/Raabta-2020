package com.kptrafficpolice.trafficapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
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
import com.kptrafficpolice.trafficapp.fragments.LicenseFragment;
import com.kptrafficpolice.trafficapp.utilities.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class QrCodeActivity extends AppCompatActivity {

        private SurfaceView mySurfaceView;
        private Button qrbtn_txt;
        private QREader qrEader;
        ImageView qr_center_image;
        SweetAlertDialog pDialog;
        int i=0;

    String strCityName, strChallanTrackingID, strCheckLatLon, strLicenseNumber, strCNIC, strResponseLicenseID, strResponseDLNumber,
            strResponseCNIC, strResponseLicHolderName, strResponseLicHolderFatherName, strResponseLicType,
            strResponseExpiryDate, strResponseLicHolderDistrict;
    RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setCancelable(false);
        mRequestQueue = Volley.newRequestQueue(this);

        qrbtn_txt = findViewById(R.id.code_info);


        // Setup SurfaceView
        // -----------------
        mySurfaceView =findViewById(R.id.camera_view);

        setupQREader();
                qrEader.initAndStart(mySurfaceView);

                if (qrEader.isCameraRunning()) {
                    mySurfaceView.setVisibility(View.INVISIBLE);
                    qrEader.stop();
                } else {
                    mySurfaceView.setVisibility(View.VISIBLE);

                    qrEader.start();
                }
            }





    void setupQREader() {
        // Init QREader
        // ------------
        qrEader = new QREader.Builder(this, mySurfaceView, new QRDataListener() {


            @Override
            public void onDetected(final String data) {

                Log.d("QREader", "Value : " + data);
                if (i==0){

                    apiCallLicense(data);
                    i++;
                }


            }
        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mySurfaceView.getHeight())
                .width(mySurfaceView.getWidth())
                .build();
    }



    public void apiCallLicense(final String cnic) {
        pDialog.setTitleText("Verifying Your License");


        String url = Configuration.LICENSE_URL + cnic+"&type=qr";

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
                                startActivity(new Intent(QrCodeActivity.this,LicenseVerificationQr.class)
                                .putExtras(args));
                                finish();
                            } else {
                                new SweetAlertDialog(QrCodeActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("CNIC not found.")
                                        .setContentText("")
                                        .show();
                            }
                            //TODO extract data from jsonarray data

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pDialog.dismiss();
                            Log.d("zma status lic", String.valueOf(response));
                            new SweetAlertDialog(QrCodeActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("CNIC not found.")
                                    .setContentText("")
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                new SweetAlertDialog(QrCodeActivity.this, SweetAlertDialog.WARNING_TYPE)
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(QrCodeActivity.this,MainActivity.class));
        finish();
    }
}