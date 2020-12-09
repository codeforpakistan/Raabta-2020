package com.kptrafficpolice.trafficapp.fragments.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.kptrafficpolice.trafficapp.R;

import java.util.Objects;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class QrFragment extends DialogFragment {

    private SurfaceView mySurfaceView;
    private Button qrbtn_txt;
    private QREader qrEader;
    ImageView qr_center_image;
    private Context context;
    private QrFragment qrFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_qr_code, container, false);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        qrFragment = (QrFragment) getTargetFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Dialog_Presentation);
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        qrbtn_txt = v.findViewById(R.id.code_info);


        // Setup SurfaceView
        // -----------------
        mySurfaceView =v.findViewById(R.id.camera_view);

        setupQREader();

        qrbtn_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrEader.initAndStart(mySurfaceView);

                if (qrEader.isCameraRunning()) {
                    qrbtn_txt.setText("Start QREader");
                    mySurfaceView.setVisibility(View.INVISIBLE);
                    qr_center_image.setVisibility(View.VISIBLE);
                    qrEader.stop();
                } else {
                    qrbtn_txt.setText("Stop QREader");
                    mySurfaceView.setVisibility(View.VISIBLE);
                    qr_center_image.setVisibility(View.INVISIBLE);

                    qrEader.start();
                }
            }
        });

    }

    void setupQREader() {
        // Init QREader
        // ------------
        qrEader = new QREader.Builder(context, mySurfaceView, new QRDataListener() {
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new Dialog(Objects.requireNonNull(getActivity()), getTheme()) {
            @Override
            public void onBackPressed() {
                // On backpress, do your stuff here.

                dismiss();
            }
        };


    }
}
