package com.example.tahmid.Medicine_HelpV2;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;



public class AppointmentActivity extends AppCompatActivity {

    private EditText etdoc;
   private Button btnsave;
   DatePickerDialog datePickerDialog;
   String selectedDate,Msg="Please select a date!";
   CalendarView cvappt;
    private PendingIntent pendingIntent;
    MyDatabaseHelper myDatabaseHelper;

    private  int day,mmonth,yyear,count=1000;

    private ProgressDialog  progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        myDatabaseHelper=new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase=myDatabaseHelper.getWritableDatabase();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        progressDialog=new ProgressDialog(this);
        btnsave=findViewById(R.id.butsave);
        cvappt=findViewById(R.id.cvappointmen);
        etdoc=findViewById(R.id.editText4);

        //buib

        cvappt.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                day=dayOfMonth;
                yyear=year;
                mmonth=month;

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //alarmMethod();

                String doc=etdoc.getText().toString();
                selectedDate= day +" / " + (mmonth+1) + " / " + yyear;
                Msg="MAppointment saved! \nDate is : " + day +" / " + (mmonth+1) + " / " + yyear;
                if(doc.equals(null)|| doc.equals("")){
                    Toast.makeText(getApplicationContext(),"Doctor's name is empty",Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences s= PreferenceManager.getDefaultSharedPreferences(AppointmentActivity.this);
                    String ppp=s.getString("val","");
                    long rowID=myDatabaseHelper.inserintoAppointment(doc,selectedDate,ppp);
                    if(rowID==-1){
                        Toast.makeText(getApplicationContext(),"Row not inserted",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Row successfully inserted",Toast.LENGTH_LONG).show();
                    }

                    count=Integer.parseInt(ppp);
                    Calendar calendar=Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,0);
                    calendar.set(Calendar.MINUTE,0);
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.DAY_OF_MONTH,day);
                    calendar.set(Calendar.MONTH,mmonth);
                    calendar.set(Calendar.YEAR,yyear);
                    Intent intent=new Intent(AppointmentActivity.this, Notification_receiver.class);
                    intent.putExtra("DOC",doc);
                    intent.putExtra("CNT",ppp);
                    intent.setAction("MY_NOTIFICATION_MESSAGE");
                    PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),count,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                    Toast.makeText(getApplicationContext(),Msg,Toast.LENGTH_LONG).show();
                    count++;
                    s.edit().putString("val",count+"").commit();
                }


            }

        });



    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }

}
