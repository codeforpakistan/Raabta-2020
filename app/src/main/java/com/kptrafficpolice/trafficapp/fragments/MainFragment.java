package com.kptrafficpolice.trafficapp.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.MultiDex;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kptrafficpolice.trafficapp.Adapters.MyCustomPagerAdapter;
import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.activities.MainDrawerActivity;
import com.kptrafficpolice.trafficapp.activities.QrCodeActivity;
import com.kptrafficpolice.trafficapp.fragments.ETest.TestMainFragment;
import com.kptrafficpolice.trafficapp.utilities.CheckNetwork;
import com.kptrafficpolice.trafficapp.utilities.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;
import io.fabric.sdk.android.Fabric;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;

//raabta
//rabta

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static boolean SUCCESS_DIALOG = false;
    Fragment fragment;
    View view;
    TextView tvUserName;
    Dialog dialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RequestQueue mRequestQueue;
    EditText etLicNumber;
    Button btnShowRecord;
    double dblLat, dblLon;
    ImageView ivSettingButton, ivWebsiteButton;
    String strCityName, strChallanTrackingID, strCheckLatLon, strLicenseNumber, strCNIC, strResponseLicenseID, strResponseDLNumber,
            strResponseCNIC, strResponseLicHolderName, strResponseLicHolderFatherName, strResponseLicType,
            strResponseExpiryDate, strResponseLicHolderDistrict;
    String strChallanDate, strChallanDistrict, strChallanName, strChallanDutyPoint, strChallanAmount, strChallanStatus;
    TextView mTitleTextView;
    SweetAlertDialog pDialog;
    Animation shake;
    LinearLayout btnComplaintSystem, btnLiveTrafficUpdates, btnChallanTracking, btnTrafficEducation,
            btnLicenseVerification, btnOffenceList, btnTest,linear_layout_driving_shool_location,
            linear_layout_ofnce_contact_us,linear_layout_driving_license_procedure;
    SweetAlertDialog myDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private Tracker mTracker;
    private FirebaseAnalytics mFirebaseAnalytics;
    private String CreationDate;
    private Button btnShowRecordQr;
    private SurfaceView mySurfaceView;
    private QREader qrEader;

    boolean hasCameraPermission = false;

    ViewPager viewPager;
    int images[] = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4};
    MyCustomPagerAdapter myCustomPagerAdapter;
    private int page;
    private Handler handler=new Handler();
    private Runnable runnable;

    public MainFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(getActivity(), new Crashlytics());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_main, container, false);

        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 1);
        }
        myDialog = new SweetAlertDialog(getActivity());
        if (SUCCESS_DIALOG) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Complaint registered")
                    .show();
            SUCCESS_DIALOG = false;
        }
        setHasOptionsMenu(true);

        try {
            SmartLocation.with(getActivity()).location()
                    .start(new OnLocationUpdatedListener() {

                        @Override
                        public void onLocationUpdated(Location location) {
                            dblLat = location.getLatitude();
                            dblLon = location.getLongitude();
                            Log.d("Location : ", "" + dblLat + " " + dblLon);
                            Geocoder geoCoder = null;
                            try {


                                geoCoder = new Geocoder(getActivity(), Locale.getDefault());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            StringBuilder builder = new StringBuilder();

                            try {
                                List<Address> address = geoCoder.getFromLocation(dblLat, dblLon, 1);
                                int maxLines = address.get(0).getMaxAddressLineIndex();
                                for (int i = 0; i < maxLines; i++) {
                                    String addressStr = address.get(0).getAddressLine(i);
                                    String city = address.get(0).getLocality();
                                    String state = address.get(0).getAdminArea();
                                    String country = address.get(0).getCountryName();
                                    String postalCode = address.get(0).getPostalCode();
                                    String knownName = address.get(0).getFeatureName();
                                    String subadmin = address.get(0).getSubLocality();
                                    Log.d("zma city 2", "city " + city + "\nstate " + state + "\n country " +
                                            country + "\n postal code " + postalCode + "\nknow name " + knownName + "get sub admin area" + subadmin);
                                    builder.append(addressStr);
                                    builder.append(" ");
                                }

                                strCityName = builder.toString(); //This is the complete address.
                                mTitleTextView.setText(strCityName);
                                if (strCityName.equals("")) {
                                    mTitleTextView.setText("Our Services");
                                }
                                // Log.d("zma city", strCityName);
                                if (address.size() > 0) {
                                    System.out.println(address.get(0).getCountryName());
                                    System.out.println(address.get(0).getAdminArea());
                                    System.out.println(address.get(0).getSubLocality());
                                }
                            } catch (IOException e) {
                            } catch (NullPointerException e) {
                            }

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Main Module Screen");
        mFirebaseAnalytics.logEvent("Main_Screen", bundle);

        Fabric.with(getActivity(), new Crashlytics());

        tvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setCancelable(false);

        sharedPreferences = getActivity().getSharedPreferences("com.ghosttech.kptraffic", 0);
        String user_name_from_reg = sharedPreferences.getString("name", "");
        Log.d("zma reg name", user_name_from_reg);
        tvUserName.setText(user_name_from_reg);
        MultiDex.install(getActivity());
        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        mRequestQueue = Volley.newRequestQueue(getActivity());
        if (CheckNetwork.isInternetAvailable(getActivity())) {
            onButtonClick();
        } else {
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
        customActionBar();

        Bundle mBundle = new Bundle();
        mBundle = getArguments();
        boolean checkMyLicense=mBundle.getBoolean("key");
        if (checkMyLicense){
            pDialog.show();
            apiCallLicense("1430130677017");
        }


        viewPager = (ViewPager)view.findViewById(R.id.view_pager);

        myCustomPagerAdapter = new MyCustomPagerAdapter(getActivity(), images);



        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(24);
        viewPager.setPadding(48, 8, 48, 8);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(myCustomPagerAdapter);
        viewPager.setCurrentItem(1);

         runnable = new Runnable() {
            public void run() {
                if (myCustomPagerAdapter.getCount() == page) {
                    page = 0;
                } else {
                    page++;
                }
                viewPager.setCurrentItem(page, true);
                handler.postDelayed(this, 5000);
            }
        };

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

    public void onButtonClick() {

        btnChallanTracking = (LinearLayout) view.findViewById(R.id.linear_layout_ct);
        btnOffenceList = (LinearLayout) view.findViewById(R.id.linear_layout_ofnce_lv);
        btnTest = (LinearLayout) view.findViewById(R.id.linear_layout_test);
        linear_layout_ofnce_contact_us = (LinearLayout) view.findViewById(R.id.linear_layout_ofnce_contact_us);
        btnOffenceList.setEnabled(true);
        btnLicenseVerification = (LinearLayout) view.findViewById(R.id.linear_layout_lv);
        btnLiveTrafficUpdates = (LinearLayout) view.findViewById(R.id.linear_layout_lu);
        btnTrafficEducation = (LinearLayout) view.findViewById(R.id.linear_layout_te);
        linear_layout_driving_shool_location = (LinearLayout) view.findViewById(R.id.linear_layout_driving_shool_location);
        linear_layout_driving_license_procedure = (LinearLayout) view.findViewById(R.id.linear_layout_driving_license_procedure);
/* removed complaint functionality */

        /*

        btnComplaintSystem = (LinearLayout) view.findViewById(R.id.linear_layout_cs);
        btnComplaintSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mTracker.send(new HitBuilders.EventBuilder()
//                        .setCategory("Complaint Module")
//                        .setAction("Opening Complaint")
//                        .build());

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences
                        ("com.ghosttech.kptraffic", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String timer = sharedPreferences.getString("timer", "");

                if (timer.equals("yes")) {
                    Toast.makeText(getActivity(), "Wait for 30 mins to send another complaint", Toast.LENGTH_SHORT).show();
                } else {

                    fragment = new ComplaintRegistrationFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).
                            addToBackStack("tag").commit();

                }
            }
        });*/
        btnLiveTrafficUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new LiveUpdateResultFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).
                        addToBackStack("tag").commit();
            }
        });
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new TestMainFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).
                        addToBackStack("test").commit();
            }
        });
        btnLicenseVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogLicenseVerification();


            }
        });
        btnOffenceList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new OffenceListFragments();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).
                        addToBackStack("tag").commit();
            }
        });


        linear_layout_driving_shool_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new DrivingSchoolLocations();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).
                        addToBackStack("tag").commit();
            }
        });

        linear_layout_driving_license_procedure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new LicenceProcedure();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).
                        addToBackStack("tag").commit();
            }
        });
        btnTrafficEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetwork.isInternetAvailable(getActivity())) {
                    fragment = new TrafficEducationFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).
                            addToBackStack("tag").commit();
                } else {
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
        });
        btnChallanTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialogChallanStatus();
            }
        });

        /*contact us click*/
        linear_layout_ofnce_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });
        /*contact us click*/

    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 5000);

    }

    @Override
    public void onPause() {
        super.onPause();
        myDialog.dismiss();

    }

    public void customActionBar() {
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Our Services");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
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


    public void customDialogChallanStatus() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_input_dialog);
        dialog.setCancelable(true);
        ImageView ivInputIcon = (ImageView) dialog.findViewById(R.id.iv_input_dialog);
        ivInputIcon.setImageResource(R.drawable.search_challam_icon);
        etLicNumber = (EditText) dialog.findViewById(R.id.et_verify_license);
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
                                CreationDate =mainJsonObject.getString("CreationDate");
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private void openCreateEventDialog()
    {
        CreateQrtDialogFragment fragment = new CreateQrtDialogFragment();
        fragment.setTargetFragment(this, 0);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_in);
        fragment.show(ft, "CreateEventDialogFragment");
        fragment.setCancelable(false);
    }




}
