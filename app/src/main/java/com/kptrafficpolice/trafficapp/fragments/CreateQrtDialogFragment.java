package com.kptrafficpolice.trafficapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.kptrafficpolice.trafficapp.R;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class CreateQrtDialogFragment extends DialogFragment {

    private Context context;
    private SurfaceView mySurfaceView;
    Dialog dialog;
    private QREader qrEader;



    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        context=getActivity();

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.qr_reader);
        dialog.setCancelable(true);

        TextView text = v.findViewById(R.id.code_info);


        // Setup SurfaceView
        // -----------------
        mySurfaceView = v.findViewById(R.id.camera_view);

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

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
//        createEventInterface = (CreateEventInterface) getTargetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Dialog_Presentation);
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
