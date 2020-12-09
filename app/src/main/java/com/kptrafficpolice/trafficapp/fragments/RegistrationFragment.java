package com.kptrafficpolice.trafficapp.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.MultiDex;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.kptrafficpolice.trafficapp.R;
import com.kptrafficpolice.trafficapp.activities.MainActivity;

import com.kptrafficpolice.trafficapp.utilities.CheckNetwork;
import com.kptrafficpolice.trafficapp.utilities.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
//raabta
//rabta

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    String Activity_Name=" :RegistrationFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static RequestQueue mRequestQueue;
    View view;
    Button btnSubmit;
    Fragment fragment;
    String strEmail, strCNIC, strPhoneNumber, strName, strPassword;
    EditText etName, etPhoneNumber, etCNIC, etEmail, etPassword;
    Animation shake;
    SweetAlertDialog pDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    // ProgressDialog dialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    private String verificationCode;
    String checkFormat;



    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        view = inflater.inflate(R.layout.fragment_registration, container, false);
            mRequestQueue = Volley.newRequestQueue(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("com.ghosttech.kptraffic", 0);
        editor = sharedPreferences.edit();
        shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        MultiDex.install(getActivity());
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#179e99"));
        pDialog.setTitleText("Wait a while");
        pDialog.setCancelable(false);
        onSubmitButton();
        customActionBar();
        // Inflate the layout for this fragment
        StartFirebaseLogin();


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

    public void onSubmitButton() {
        btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                formValidation();


            }
        });
    }

    public void formValidation() {
        etName = (EditText) view.findViewById(R.id.et_name);
        etCNIC = (EditText) view.findViewById(R.id.et_cnic);
        etEmail = (EditText) view.findViewById(R.id.et_email);
        etPassword = (EditText) view.findViewById(R.id.et_pass);
        etPhoneNumber = (EditText) view.findViewById(R.id.et_phone_number);
        String formatedPhoneNumber=etPhoneNumber.getText().toString();
        if (formatedPhoneNumber.length()<3) {
            etPhoneNumber.startAnimation(shake);

        }
        checkFormat = formatedPhoneNumber.substring(0, 2);

        strName = etName.getText().toString().trim();
        strEmail = etEmail.getText().toString().trim();
        strCNIC = etCNIC.getText().toString().trim();
        strPhoneNumber = etPhoneNumber.getText().toString().trim();
        strPassword = etPassword.getText().toString().trim();

        if (strName.equals("") || strName.length() < 3) {
            etName.startAnimation(shake);
        } else if (strPhoneNumber.equals("") || strPhoneNumber.length() < 5 || strPhoneNumber.length()>12) {
            etPhoneNumber.startAnimation(shake);
        }

        else if (!checkFormat.matches("92")){
            etPhoneNumber.setText("");
            etPhoneNumber.startAnimation(shake);
            etPhoneNumber.setHint("Format 923xxxxxxxxx");
            etPhoneNumber.setHintTextColor(getResources().getColor(R.color.red_btn_bg_color));


        }
        else if (strPassword.equals("") ) {
            etPassword.startAnimation(shake);
        } else if (strCNIC.equals("") || strCNIC.length() < 13 || strCNIC.contains("-")) {
            etCNIC.startAnimation(shake);
        } else if ((!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches())) {
            etEmail.startAnimation(shake);
        } else {
            Log.d("zma data", strName + "\n" + strEmail + "\n" + strPhoneNumber + "\n" + strCNIC);
            if (CheckNetwork.isInternetAvailable(getActivity())) {
                apiCall();



            } else {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops!")
                        .setContentText("You don't have internet connection")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                getActivity().finish();
                            }
                        })
                        .show();
            }
        }
    }

    public void apiCall() {
        pDialog.show();
        String url = Configuration.END_POINT_LIVE + "signup/signup";
        Log.d("zma url", url);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean status = response.contains("true");
                        if (status) {

                            generateOtp();




                            Log.d("Zma response", response);

                        } else if (response.contains("false")) {
                            pDialog.dismiss();
                            etCNIC.setText("");
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("CNIC already exists!")
                                    .show();
                        }
                        pDialog.dismiss();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Server Error!")
                        .show();
                Log.d("zma error registration", String.valueOf(error));
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded;charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", strEmail);
                params.put("name", strName);
                params.put("cnic", strCNIC);
                params.put("password", strPassword);
                params.put("phone_no", strPhoneNumber);
                editor.putString("user_name", strName).commit();
                Log.d("zma reg name",strName);
                return params;
            }

        };

        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(200000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjRequest);
    }

    public void customActionBar() {
        ActionBar mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText("Sign Up");
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class ViewDialog {

        public void showDialog(Activity activity, String msg){


            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.opt_dialog);

            TextView tv_instruction = (TextView) dialog.findViewById(R.id.tv_instruction);
            final EditText et_opt = (EditText) dialog.findViewById(R.id.et_opt);
            tv_instruction.setText("Please type the verification code sent to "+etPhoneNumber.getText().toString().trim());

            Button btn_confirm_otp = (Button) dialog.findViewById(R.id.btn_confirm_otp);
            Button btn_try_other = (Button) dialog.findViewById(R.id.btn_try_other);
            btn_confirm_otp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String otp=et_opt.getText().toString().trim();
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);

                    SigninWithPhone(credential);
                }
            });
            btn_try_other.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }



    }
    public void generateOtp(){
        String phoneNumber=etPhoneNumber.getText().toString();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+phoneNumber,                     // Phone number to verify
                60,                           // Timeout duration
                TimeUnit.SECONDS,                // Unit of timeout
                getActivity(),        // Activity (for callback binding)
                mCallback);                      // OnVerificationStateChangedCallbacks
    }


    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(getActivity(),"verification completed",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getActivity(),"verification fialed",Toast.LENGTH_SHORT).show();
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.d(Configuration.TAG_APP+Activity_Name," onVerificationFailed "+e.getMessage());
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
                Log.d(Configuration.TAG_APP+Activity_Name," onVerificationFailed "+e.getMessage());
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                ViewDialog otpViewDialog=new ViewDialog();
                otpViewDialog.showDialog(getActivity(),"");
                verificationCode = s;
                Toast.makeText(getActivity(),"Code sent", Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pDialog.dismiss();
                        if (task.isSuccessful()) {
                            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Success")
                                    .setContentText("You have been registered")
                                    .show();
                            fragment = new LoginFragment();
                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                            etCNIC.setText("");
                            etEmail.setText("");
                            etPhoneNumber.setText("");
                            etName.setText("");
                        } else {
                            Toast.makeText(getActivity(),"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
