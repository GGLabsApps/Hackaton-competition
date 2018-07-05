package com.gglabs.materna.Helper;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GG on 17/03/2018.
 */

public class BarcodeReader implements Detector.Processor<Barcode> {

    private final int REQUEST_CODE_PERMISSION_CAMERA = 50;

    private OnDetectListener detectListener;

    private int accuracy = 3;
    private AppCompatActivity activity;
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private BarcodeDetector detector;

    private List<String> barcodesDetected;

    public void setOnDetectListener(OnDetectListener detectListener) {
        this.detectListener = detectListener;
    }

    public BarcodeReader(AppCompatActivity activity, SurfaceView surfaceView, int accuracy) {
        this.activity = activity;
        this.surfaceView = surfaceView;
        this.accuracy = accuracy;
        init();
    }

    public BarcodeReader(AppCompatActivity activity, SurfaceView surfaceView) {
        this(activity, surfaceView, 3);
    }

    private void init() {
        barcodesDetected = new ArrayList<>();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            // The event that detects the barcode from camera
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (ActivityCompat.checkSelfPermission(activity,
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{android.Manifest.permission.CAMERA},
                            REQUEST_CODE_PERMISSION_CAMERA);
                } else {
                    try {
                        cameraSource.start(holder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //
            }
        });

        detector = new BarcodeDetector.Builder(activity)
                .setBarcodeFormats(Barcode.EAN_13)
                .build();

        if (!detector.isOperational()) {
            //Toast.makeText(this, "Could not setup barcode detector", Toast.LENGTH_LONG).show();
            return;
        }
        detector.setProcessor(this);

        cameraSource = new CameraSource.Builder(activity, detector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24f)
                .setAutoFocusEnabled(true)
                .build();
    }

    @Override
    public void release() {
        detector.release();
    }

    @Override
    public void receiveDetections(Detector.Detections<Barcode> detections) {
        if (detections == null) return;
        //Toast.makeText(ScannerActivity.this, "receiveDetections():\n" + detections.toString(), Toast.LENGTH_SHORT).show();

        final SparseArray<Barcode> barcodes = detections.getDetectedItems();

        if (barcodes.size() > 0) {
            for (int i = 0; i < barcodes.size(); i++)
                barcodesDetected.add(barcodes.valueAt(i).displayValue);

            if (barcodesDetected.size() < accuracy) return;

            int match = 0;
            for (int i = 1; i < barcodesDetected.size(); i++) {

                String curr = barcodesDetected.get(i);
                String prev = barcodesDetected.get(i - 1);
                if (curr.equals(prev)) {
                    match++;
                    if (match >= accuracy - 1) {
                        detectListener.onDetect(curr);
                        barcodesDetected.clear();
                        return;
                    }
                }
            }
        }
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public CameraSource getCameraSource() {
        return this.cameraSource;
    }

}
