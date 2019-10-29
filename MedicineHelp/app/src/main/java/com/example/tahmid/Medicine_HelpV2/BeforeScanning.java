package com.example.tahmid.Medicine_HelpV2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class BeforeScanning extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_scanning);

        textView=findViewById(R.id.touchMeId);

       textView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(),OCRforScan.class);
              // intent.putExtra("key","scan");
               startActivity(intent);
               overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
           }
       });


    }
}
