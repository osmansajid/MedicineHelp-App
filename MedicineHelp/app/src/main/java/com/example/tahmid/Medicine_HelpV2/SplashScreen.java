package com.example.tahmid.Medicine_HelpV2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    private ProgressBar progress;
    private int h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        progress=findViewById(R.id.welc);
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                dowork();
                startapp();
            }
        });
        thread.start();
    }
    void dowork(){
        for(h=0 ;h<=100; h=h+1) {
            try {
                Thread.sleep(10);
                progress.setProgress(h);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    void startapp(){
        Intent intent=new Intent(SplashScreen.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        finish();

    }
}
