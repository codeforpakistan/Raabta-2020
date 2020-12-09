package com.kptrafficpolice.trafficapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.fragments.LicenseFragment;

public class LicenseVerificationQr extends AppCompatActivity {

    TextView tvLicHolderName, tvLicHolderFatherName, tvLicHolderDistrict, tvLicType, tvLicIssueDate,
            tvLisExpiryDate,tvCNICNumber,tvLicenseNumber;
    private LicenseFragment.OnFragmentInteractionListener mListener;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_license);

        tvLicHolderName = (TextView) findViewById(R.id.tv_lh_name);
        tvLicHolderFatherName = (TextView) findViewById(R.id.tv_lh_fname);
        tvLicType = (TextView) findViewById(R.id.tv_license_type);
        tvLicHolderDistrict = (TextView) findViewById(R.id.tv_lh_district);
        tvLisExpiryDate = (TextView) findViewById(R.id.tv_expiry_date);
        tvLicenseNumber = (TextView)findViewById(R.id.tv_license_number);
        tvCNICNumber = (TextView)findViewById(R.id.tv_lh_cnic_number);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "4");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "License Status Checked");
        mFirebaseAnalytics.logEvent("License_checked", bundle);


        Bundle args = getIntent().getExtras();
        tvLicHolderName.setText(String.valueOf(args.get("name")));
        tvLicHolderDistrict.setText(String.valueOf(args.get("district")));
        tvLicHolderFatherName.setText(String.valueOf(args.get("f_name")));
        tvLisExpiryDate.setText(String.valueOf(args.get("expiry_date")));
        tvLicType.setText(String.valueOf(args.get("lic_type")));
        tvCNICNumber.setText(String.valueOf(args.get("cnic")));
        tvLicenseNumber.setText(String.valueOf(args.get("license_number")));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
