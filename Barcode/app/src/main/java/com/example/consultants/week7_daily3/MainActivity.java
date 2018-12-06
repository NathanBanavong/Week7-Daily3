package com.example.consultants.week7_daily3;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    SurfaceView svCamera;
    CameraSource cameraSource;
    private TextView tvMain;
    BarcodeDetector barcodeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        plant the timber log 'tree'
        Timber.plant();

        onBindView();

//        set the barcode detector
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();
//        set the camera source
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480).build();

        svCamera.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Timber.v("surfaceCreated, permissions allowed");
                    return;
                }
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Timber.v("Entered a surface changed");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                Timber.v("End the program when done");
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCode = detections.getDetectedItems();
                Timber.v("noticed barcode");
                if (qrCode.size() != 0) {
                    tvMain.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator) getApplicationContext()
                                    .getSystemService(Context.VIBRATOR_SERVICE);

                            vibrator.vibrate(1000);
                            tvMain.setText(qrCode.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });
    }

    public void onBindView() {
        Timber.v("onBindView views");
        svCamera = findViewById(R.id.svCamera);
        tvMain = findViewById(R.id.tvMain);
    }
}
