package com.example.tahmid.Medicine_HelpV2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class OCRforScan extends AppCompatActivity {

    private SurfaceView cameraView;
    private TextView textView;
    private CameraSource cameraSource;
    final int RequestCameraPermissionID=1001;
    private int count=0;
    private StringBuilder stringBuilder;
    private Button capture;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case RequestCameraPermissionID:
            {
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        return;
                    }

                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocrfor_scan);

        cameraView = findViewById(R.id.surface_view);
        textView = findViewById(R.id.text_view);
        capture=findViewById(R.id.captureId);

        final Bundle bundle=getIntent().getExtras();

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector recogniser is not yet available");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(cameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
        }

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                        ActivityCompat.requestPermissions(OCRforScan.this,
                                new String[]{Manifest.permission.CAMERA},
                                RequestCameraPermissionID);

                        return;
                    }
                    cameraSource.start(cameraView.getHolder());

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<TextBlock> detections) {

                final SparseArray<TextBlock> items=detections.getDetectedItems();

                if(items.size()!=0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            stringBuilder=new StringBuilder();

                            for(int i=0;i<items.size();i++){

                                TextBlock item=items.valueAt(i);
                                stringBuilder.append(item.getValue());
                                stringBuilder.append("\n");

                            }

                            textView.setText(stringBuilder.toString());


                        }
                    });
                }

            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    Intent intent = new Intent(OCRforScan.this, AfterScanning.class);
                    intent.putExtra("name", stringBuilder.toString());
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(OCRforScan.this,"Wait until prescription is scanned. You will see a preview of it.",Toast.LENGTH_LONG).show();
                }





            }
        });

    }
}
