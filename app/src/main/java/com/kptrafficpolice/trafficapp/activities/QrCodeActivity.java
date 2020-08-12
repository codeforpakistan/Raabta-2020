package com.kptrafficpolice.trafficapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kptrafficpolice.trafficapp.R;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class QrCodeActivity extends AppCompatActivity {

    private SurfaceView mySurfaceView;
    private Button qrbtn_txt;
    private QREader qrEader;
    ImageView qr_center_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
         qrbtn_txt = findViewById(R.id.code_info);
        qr_center_image = findViewById(R.id.qr_center_image);


        // Setup SurfaceView
        // -----------------
        mySurfaceView =findViewById(R.id.camera_view);

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
        qrEader = new QREader.Builder(this, mySurfaceView, new QRDataListener() {
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