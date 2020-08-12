package com.kptrafficpolice.trafficapp.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.activities.MainDrawerActivity;
import com.kptrafficpolice.trafficapp.utilities.CheckNetwork;
import com.kptrafficpolice.trafficapp.utilities.Configuration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
//raabta
//rabta
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainEmergencyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainEmergencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainEmergencyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    ImageView ivHealthEmergency, ivMechanicsEmergency, ivRescueEmergency, ivHighOfficer, ivHomeButton, ivSettingButton, ivWebsiteButton;
    Fragment fragment;
    double dblLat, dblLon;
    String strRescue, strHealth, strMechanics, strHighwayOfficers;
    LinearLayout lLCallView;
    Bundle bundle;
    SweetAlertDialog pDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public MainEmergencyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainEmergencyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainEmergencyFragment newInstance(String param1, String param2) {
        MainEmergencyFragment fragment = new MainEmergencyFragment();
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
        view = inflater.inflate(R.layout.fragment_main_emergency, container, false);
        sharedPreferences = getActivity().getSharedPreferences("com.ghosttech.kptraffic", 0);
        editor = sharedPreferences.edit();

        ivHealthEmergency = (ImageView) view.findViewById(R.id.iv_emergency_health);
        ivHighOfficer = (ImageView) view.findViewById(R.id.iv_emergency_highway_officer);
        ivMechanicsEmergency = (ImageView) view.findViewById(R.id.iv_emergency_mechanics);
        ivRescueEmergency = (ImageView) view.findViewById(R.id.iv_emergency_rescue_1122);
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setTitleText("Sending complaint");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 1);
        }
        bundle = new Bundle();
        onEmergencyButtonClick();

        SmartLocation.with(getActivity()).location()
                .start(new OnLocationUpdatedListener() {

                    @Override
                    public void onLocationUpdated(Location location) {
                        dblLat = location.getLatitude();
                        dblLon = location.getLongitude();
                        Log.d("zma Location : ", "" + dblLat + " " + dblLon);
                    }
                });
        customActionBar();
        Log.d("zma Location main: ", "" + dblLat + " " + dblLon);
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

    public void onEmergencyButtonClick() {

        fragment = new EmergencyFragmentList();
        ivRescueEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "1122"));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                getActivity().startActivity(callIntent);

//                strRescue = "1";
//                bundle.putString("emergency_id",strRescue);
//                getData(strRescue,String.valueOf(dblLat),String.valueOf(dblLon));

            }
        });
        ivHealthEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pDialog.show();
                strHealth = "2";
                bundle.putString("emergency_id", strHealth);
                if (CheckNetwork.isInternetAvailable(getActivity())) {
                    editor.putString("clicked_item","health").commit();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).
                            addToBackStack("tag").commit();
                    fragment.setArguments(bundle);
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
                // getData(strHealth, String.valueOf(dblLat), String.valueOf(dblLon));


            }
        });
        ivMechanicsEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strMechanics = "3";
                // pDialog.show();
                bundle.putString("emergency_id", strMechanics);
                editor.putString("clicked_item","mechanics").commit();
                if (CheckNetwork.isInternetAvailable(getActivity())) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
                    fragment.setArguments(bundle);
                }else {
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
        ivHighOfficer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strHighwayOfficers = "4";
                bundle.putString("emergency_id", strHighwayOfficers);
                editor.putString("clicked_item","officer").commit();
                if (CheckNetwork.isInternetAvailable(getActivity())) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
                    fragment.setArguments(bundle);
                }else {
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

    }

    public void getData(final String strCategoryID, final String latitude, final String longitude) {
        final String url = Configuration.END_POINT_LIVE + "emergency_contacts/getEmergencyContact?category_id=" + strCategoryID + "&latitude=" + latitude + "&longitude=" + longitude;
        Log.d("zma url", url);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Log.d("zma url emer main", url + "\n" + response.getBoolean("status") + "\n" + String.valueOf(response));
                    // list.clear();

                    if (response.getBoolean("status")) {
                        pDialog.dismiss();
                        JSONArray data = response.getJSONArray("data");
                        Log.d("zma response emer true", String.valueOf(data));
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack("tag").commit();
                        fragment.setArguments(bundle);
                    } else {
                        pDialog.dismiss();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText("No data found around your location")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        Fragment fragment = new MainEmergencyFragment();
                                        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                                        sweetAlertDialog.dismiss();

                                    }
                                }).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  pDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(getActivity(), "Something went wrong, try later", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("category_id", strCategoryID);
                params.put("latitude", latitude);
                params.put("longitude", longitude);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

    }

    public void customActionBar() {
     ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Emergency Contacts");
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
