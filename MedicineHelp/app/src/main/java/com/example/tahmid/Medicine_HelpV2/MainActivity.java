package com.example.tahmid.Medicine_HelpV2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {


      private  String name,info;



    private CardView medSearch,doses,reminder,appointment,scan;
    private CardView ambln;
    MyDatabaseHelper myDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences s= PreferenceManager.getDefaultSharedPreferences(this);
        String p=s.getString("val","");
        if(p.isEmpty()){
            s.edit().putString("val","0").commit();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        medSearch=findViewById(R.id.medInfoId);
        doses=findViewById(R.id.doseId);
        reminder=findViewById(R.id.reminderId);
        appointment=findViewById(R.id.appointmentId);
        ambln=(CardView)findViewById(R.id.ambulanceId);
        scan=findViewById(R.id.scanId);


        //kjhbghi



      //  Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();


        medSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,MedicineInfoActivity.class);
                intent.putExtra("key","medSearch");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }
        });

        ///Commit

        doses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DosesActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                //testdb();
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ReminderActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AppointmentActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });
        ambln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AmbulanceActitivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,BeforeScanning.class);
                intent.putExtra("key","scan");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });




    }

}
