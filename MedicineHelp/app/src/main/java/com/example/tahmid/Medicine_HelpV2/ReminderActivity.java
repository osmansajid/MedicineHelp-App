package com.example.tahmid.Medicine_HelpV2;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReminderActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView dateView,timeView1,timeView2,timeView3;
    private EditText medname,noofdays,docname;
    private DatePickerDialog datePickerDialog;
    private Button btnsave;
    private TextInputLayout doc_name,med_name,duration;
    CalendarView cvappt;
    private int dday=-1,mmonth,yyear,mhour=-1,mmin,ahour=-1,amin,nmin,nhour=-1,count;
    ArrayList<PendingIntent> intentArray = new ArrayList<PendingIntent>();
    ArrayList<PendingIntent> intentArray2 = new ArrayList<PendingIntent>();
    ArrayList<PendingIntent> intentArray3 = new ArrayList<PendingIntent>();
    MyDatabaseHelper myDatabaseHelper;
    String sstdate,eendate,mmtime,aatime,nntime;
    int diff=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        myDatabaseHelper=new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase=myDatabaseHelper.getWritableDatabase();
        doc_name=findViewById(R.id.inputfullname);
        med_name=findViewById(R.id.medicine_layout);
        duration=findViewById(R.id.durationlayout);

        dateView=findViewById(R.id.datePickerId);
        timeView1=findViewById(R.id.timePicker1Id);
        timeView2=findViewById(R.id.timePicker2Id);
        timeView3=findViewById(R.id.timePicker3Id);
        btnsave=findViewById(R.id.buttsave);
        medname=findViewById(R.id.medNameRemId);
        noofdays=findViewById(R.id.ethowmanydays);
        docname=findViewById(R.id.etdocname);
        dateView.setOnClickListener(this);
        timeView1.setOnClickListener(this);
        timeView2.setOnClickListener(this);
        timeView3.setOnClickListener(this);
    }
    private boolean validmedname(){
        String mednameintput=med_name.getEditText().getText().toString().trim();
        if (mednameintput.isEmpty()){
          med_name.setError("Field can't be empty");
          return false;
        }
        else{
            med_name.setError(null);
            return true;
        }

    }
    private boolean validdocname(){
        String docnameintput=doc_name.getEditText().getText().toString().trim();
        if (docnameintput.isEmpty()){
            doc_name.setError("Field can't be empty");
            return false;
        }
        else{
            doc_name.setError(null);
            return true;
        }

    }

    private boolean validduration(){
        String durationintput=duration.getEditText().getText().toString().trim();
        if (durationintput.isEmpty()){
            duration.setError("Field can't be empty");
            return false;
        }
        else{
            duration.setError(null);
            return true;
        }

    }

    public void confirmInput(View v){
        if(!validmedname() | !validdocname() | !validduration()){
            return;
        }

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.datePickerId){

            Calendar calendar=Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);



            datePickerDialog=new DatePickerDialog(ReminderActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    dday=dayOfMonth;
                    yyear=year;
                    mmonth=month;
                    try {
                        Calendar c=Calendar.getInstance();
                        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
                        sstdate=dayOfMonth+"/"+(month+1)+"/"+year;
                        Date date1=dates.parse(sstdate);
                        Date date2=c.getTime();
                        long ddiff=(date1.getTime()-date2.getTime())/(24 * 60 * 60 * 1000);
                        diff=(int)ddiff;
                        //Toast.makeText(getApplicationContext(),diff+"",Toast.LENGTH_LONG).show();

                    }
                    catch (Exception e){

                    }



                }
            },year,month,day);
            datePickerDialog.show();

        }

        if(v.getId()==R.id.timePicker1Id){

            final int hour,min;
            TimePicker timePicker=new TimePicker(ReminderActivity.this);
            hour=timePicker.getCurrentHour();
            min=timePicker.getCurrentMinute();


            TimePickerDialog timePickerDialog=new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mmin=minute;
                    mhour=hourOfDay;
                    mmtime=hourOfDay+":"+minute+"";

                    Toast.makeText(getApplicationContext(),mmtime,Toast.LENGTH_LONG).show();
                }
            },min,hour,false);
            timePickerDialog.show();
        }

        if(v.getId()==R.id.timePicker2Id){


            int hour,min;
            TimePicker timePicker=new TimePicker(ReminderActivity.this);
            hour=timePicker.getCurrentHour();
            min=timePicker.getCurrentMinute();


            TimePickerDialog timePickerDialog=new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    amin=minute;
                    ahour=hourOfDay;
                    aatime=hourOfDay+":"+minute+"";

                    Toast.makeText(getApplicationContext(),aatime,Toast.LENGTH_LONG).show();
                }
            },min,hour,false);
            timePickerDialog.show();
            //commit

        }

        if(v.getId()==R.id.timePicker3Id){

            int hour,min;
            TimePicker timePicker=new TimePicker(ReminderActivity.this);
            hour=timePicker.getCurrentHour();
            min=timePicker.getCurrentMinute();


            TimePickerDialog timePickerDialog=new TimePickerDialog(ReminderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    nmin=minute;
                    nhour=hourOfDay;
                    nntime=nhour+":"+nmin+"";

                    Toast.makeText(getApplicationContext(),nntime,Toast.LENGTH_LONG).show();
                }
            },min,hour,false);
            timePickerDialog.show();

        }

        btnsave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //alarmMethod();
                //confirminput();
                String xx=noofdays.getText().toString();
                String wow=medname.getText().toString();
                String doc=docname.getText().toString();
                String stdate=sstdate;
                String endate="";
                String mtime="N/A";
                String atime="N/A";
                String ntime="N/A";
               if(xx.equals(null)|| xx.equals("") || wow.equals(null) || wow.equals("") || doc.equals(null)|| doc.equals("")){
                   Toast.makeText(getApplicationContext(),"Please insert all values",Toast.LENGTH_LONG).show();
               }
               else if(dday==-1){
                   Toast.makeText(getApplicationContext(),"Please select a start date",Toast.LENGTH_LONG).show();
               }
               else if(mhour==-1 && nhour==-1 && ahour==-1){
                   Toast.makeText(getApplicationContext(),"No time selected",Toast.LENGTH_LONG).show();
               }
               else{
                   SharedPreferences s= PreferenceManager.getDefaultSharedPreferences(ReminderActivity.this);

                   String pp=s.getString("val","");
                   count=Integer.parseInt(pp);
                    int fromid=count;
                   int len=Integer.parseInt(xx);
                   for(int i=0;i<len;i++){

                       String cnt;
                       if(mhour!=-1){
                           String ppp=s.getString("val","");
                           count=Integer.parseInt(ppp);
                           mtime=mmtime;
                           cnt=Integer.toString(count);

                           Calendar calendar=Calendar.getInstance();
                           calendar.add(Calendar.DAY_OF_MONTH,(i+diff));
                           calendar.set(Calendar.HOUR_OF_DAY,mhour);
                           calendar.set(Calendar.MINUTE,mmin);
                           calendar.set(Calendar.SECOND,0);
                           endate=calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);

                           // Toast.makeText(getApplicationContext(),wow+mhour+":"+mmin+" and "+ ahour +":"+ amin+" and "+calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR),Toast.LENGTH_LONG).show();


                           Intent intent=new Intent(ReminderActivity.this, Notification_receiver_med.class);
                           intent.putExtra("MED",wow);
                           intent.putExtra("CNT",cnt);
                           intent.setAction("MY_NOTIFICATION_MESSAGE_");
                           PendingIntent pendingIntent=PendingIntent.getBroadcast(ReminderActivity.this,count,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                           AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                           //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                           alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                           //Toast.makeText(getApplicationContext(),selectedDate,Toast.LENGTH_LONG).show();
                           intentArray.add(pendingIntent);
                           count++;
                           //Toast.makeText(getApplicationContext(),count+"",Toast.LENGTH_LONG).show();
                           s.edit().putString("val",count+"").commit();
                       }

                       if(ahour!=-1){
                           String ppp=s.getString("val","");
                           count=Integer.parseInt(ppp);
                           atime=aatime;
                           cnt=Integer.toString(count);
                           //Toast.makeText(getApplicationContext(),mhour+":"+mmin+" and "+dday+"/"+(mmonth+1)+"/"+yyear,Toast.LENGTH_LONG).show();
                           Calendar ccalendar=Calendar.getInstance();
                           ccalendar.add(Calendar.DAY_OF_MONTH,(i+diff));
                           ccalendar.set(Calendar.HOUR_OF_DAY,ahour);
                           ccalendar.set(Calendar.MINUTE,amin);
                           ccalendar.set(Calendar.SECOND,0);
                           endate=ccalendar.get(Calendar.DAY_OF_MONTH)+"/"+(ccalendar.get(Calendar.MONTH)+1)+"/"+ccalendar.get(Calendar.YEAR);

                           Intent iintent=new Intent(ReminderActivity.this, Notification_receiver_med.class);
                           iintent.putExtra("MED",wow);
                           iintent.putExtra("CNT",cnt);
                           iintent.setAction("MY_NOTIFICATION_MESSAGE_");
                           PendingIntent ppendingIntent=PendingIntent.getBroadcast(ReminderActivity.this,count,iintent,PendingIntent.FLAG_UPDATE_CURRENT);
                           AlarmManager aalarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                           //aalarmManager.setRepeating(AlarmManager.RTC_WAKEUP,ccalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,ppendingIntent);
                           //Toast.makeText(getApplicationContext(),selectedDate,Toast.LENGTH_LONG).show();
                           aalarmManager.set(AlarmManager.RTC_WAKEUP,ccalendar.getTimeInMillis(),ppendingIntent);
                           intentArray2.add(ppendingIntent);
                           count++;
                           s.edit().putString("val",count+"").commit();
                       }

                       if(nhour!=-1){
                           String ppp=s.getString("val","");
                           count=Integer.parseInt(ppp);
                           ntime=nhour+":"+nmin+"";
                           cnt=Integer.toString(count);
                            //Toast.makeText(getApplicationContext(),ntime+" and "+dday+"/"+(mmonth+1)+"/"+yyear,Toast.LENGTH_LONG).show();
                           Calendar cccalendar=Calendar.getInstance();
                           cccalendar.add(Calendar.DAY_OF_MONTH,(i+diff));
                           cccalendar.set(Calendar.HOUR_OF_DAY,nhour);
                           cccalendar.set(Calendar.MINUTE,nmin);
                           cccalendar.set(Calendar.SECOND,0);
                           endate=cccalendar.get(Calendar.DAY_OF_MONTH)+"/"+(cccalendar.get(Calendar.MONTH)+1)+"/"+cccalendar.get(Calendar.YEAR);

                           Intent iiintent=new Intent(ReminderActivity.this, Notification_receiver_med.class);
                           iiintent.putExtra("MED",wow);
                           iiintent.putExtra("CNT",cnt);
                           iiintent.setAction("MY_NOTIFICATION_MESSAGE_");
                           PendingIntent pppendingIntent=PendingIntent.getBroadcast(ReminderActivity.this,count,iiintent,PendingIntent.FLAG_UPDATE_CURRENT);
                           AlarmManager aaalarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                           //aaalarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cccalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pppendingIntent);
                           //Toast.makeText(getApplicationContext(),selectedDate,Toast.LENGTH_LONG).show();
                           aaalarmManager.set(AlarmManager.RTC_WAKEUP,cccalendar.getTimeInMillis(),pppendingIntent);
                           intentArray3.add(pppendingIntent);
                           count++;
                           s.edit().putString("val",count+"").commit();
                       }
                   }
                   int toid=count-1;
                   //Toast.makeText(getApplicationContext(),ntime,Toast.LENGTH_LONG).show();
                   long rowID=myDatabaseHelper.insertintoHistory(wow,doc,stdate,endate,mtime,atime,ntime,fromid,toid);
                   if(rowID==-1){
                       Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
                   }
                   else{
                       //Toast.makeText(getApplicationContext(),"Row successfully inserted",Toast.LENGTH_LONG).show();

                   }

                   Toast.makeText(getApplicationContext(),"Reminder saved",Toast.LENGTH_LONG).show();
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