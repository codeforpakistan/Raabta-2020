package com.kptrafficpolice.trafficapp.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.Services.VolleyService;
import com.kptrafficpolice.trafficapp.utilities.PrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePassword extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    VolleyService volleyService;
    EditText newPassword,cnewPassword;
    Button changeNow;

    SweetAlertDialog pDialog;


    public ChangePassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePassword.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePassword newInstance(String param1, String param2) {
        ChangePassword fragment = new ChangePassword();
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
        View view= inflater.inflate(R.layout.fragment_change_password, container, false);
        newPassword=view.findViewById(R.id.newPassword);
        cnewPassword=view.findViewById(R.id.cnewPassword);
        changeNow=view.findViewById(R.id.changeNow);

        sharedPreferences = getActivity().getSharedPreferences("com.ghosttech.kptraffic", 0);

       final String userid= sharedPreferences.getString("strUserID2","");
       final String userpassword= sharedPreferences.getString("password","");
       Log.d("id and password",userid+" And "+ userpassword);

       volleyService=new VolleyService(getActivity());
       changeNow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (newPassword.getText().toString().isEmpty()){
                   Toast.makeText(getActivity(), "Please Enter Password", Toast.LENGTH_SHORT).show();
               }
               else if (cnewPassword.getText().toString().isEmpty()){
                   Toast.makeText(getActivity(), "Re-enter password cannot be empty", Toast.LENGTH_SHORT).show();
               }
               else {

                   //12897
                   pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                   pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
                   pDialog.setTitleText("Getting Login");
                   pDialog.setCancelable(false);
                   pDialog.show();
                   volleyService.ChangePassword("http://ptpkp.gov.pk/dashboard/signup/change_pass", userid, userpassword, newPassword.getText().toString().trim()
                           , cnewPassword.getText().toString().trim(), new VolleyService.VolleyResponseListener() {
                               @Override
                               public void onSuccess(String response) {
                                   pDialog.dismiss();
                                   Log.d("change password",response);
                                   try {
                                       JSONObject jsonObject=new JSONObject(response);
                                       boolean success=jsonObject.getBoolean("success");
                                       if (success){
                                           pDialog.dismiss();
                                           new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                                   .setTitleText("Successfull...")
                                                   .setContentText("Password Changed Successfully. .")
                                                   .show();
                                       }
                                       else {
                                           new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                                   .setTitleText("oops...")
                                                   .setContentText("Both password must be same")
                                                   .show();

                                       }
                                   } catch (JSONException e) {
                                       e.printStackTrace();
                                   }
                               }

                               @Override
                               public void onError(VolleyError error) {

                                   pDialog.dismiss();
                               }
                           });

               }
           }
       });

        return view;
    }
}