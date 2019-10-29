package com.example.tahmid.Medicine_HelpV2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyMe extends AppCompatActivity {





    ListView lvmed;
    CardView cv;
    MyDatabaseHelper mydatabasehelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_me);
        lvmed=findViewById(R.id.medlist);
        //cv=findViewById(R.id.CardId);
        mydatabasehelper=new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase=mydatabasehelper.getWritableDatabase();
        display();
        //onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }
    public  void  display() {
        Cursor cursor = mydatabasehelper.getHistory();
        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "You don't have any history", Toast.LENGTH_LONG).show();
            return;

        }


        final ArrayList<String> med = new ArrayList<String>();
        final ArrayList<String> doc = new ArrayList<String>();
        final ArrayList<String> stdate = new ArrayList<String>();
        final ArrayList<String> endate = new ArrayList<String>();
        final ArrayList<String> mtime = new ArrayList<String>();
        final ArrayList<String> atime = new ArrayList<String>();
        final ArrayList<String> ntime = new ArrayList<String>();
        final ArrayList<Integer> fromid = new ArrayList<Integer>();
        final ArrayList<Integer> toid=new ArrayList<Integer>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String curdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());


        while(cursor.moveToNext()){
            try{
             String enddate=cursor.getString(3);
             Date date1=formatter.parse(enddate);
             Date date2=formatter.parse(curdate);
                if (date1.compareTo(date2)<0 || cursor.getInt(7)==-1)
                {
                    System.out.println("date2 is Greater than my date1");
                }
                else
                {
                    med.add(cursor.getString(0));
                    doc.add(cursor.getString(1));
                    stdate.add(cursor.getString(2));
                    endate.add(cursor.getString(3));
                    mtime.add(cursor.getString(4));
                    atime.add(cursor.getString(5));
                    ntime.add(cursor.getString(6));
                    fromid.add(cursor.getInt(7));
                    toid.add(cursor.getInt(8));
                }
            }catch(ParseException e){
               e.printStackTrace();
            }

        }



        CustomAdapterHis c = new CustomAdapterHis(MyMe.this,med,doc,stdate,endate,mtime,atime,ntime,fromid,toid);
        lvmed.setAdapter(c);

        lvmed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int in=i;

                AlertDialog.Builder a_builder=new AlertDialog.Builder(MyMe.this);
                a_builder.setMessage("Do you want to turn off the alarm?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int from=fromid.get(in);
                        int to=toid.get(in);
                        for(int j=from;j<=to;j++){
                            AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                            Intent intent=new Intent(MyMe.this, Notification_receiver_med.class);
                            intent.putExtra("CNT",j);
                            intent.setAction("MY_NOTIFICATION_MESSAGE_");
                            PendingIntent pendingIntent=PendingIntent.getBroadcast(MyMe.this,j,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            alarmManager.cancel(pendingIntent);
                            boolean ok=mydatabasehelper.updatehis(med.get(in),doc.get(in),stdate.get(in),endate.get(in),mtime.get(in),atime.get(in),ntime.get(in),fromid.get(in),toid.get(in));
                            if(ok==true){


                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Failed to cancel alarm", Toast.LENGTH_LONG).show();
                            }
                        }
                        Cursor cursor = mydatabasehelper.getHistory();
                        if (cursor.getCount() == 0) {
                            Toast.makeText(getApplicationContext(), "You don't have any history", Toast.LENGTH_LONG).show();
                            return;

                        }


                        final ArrayList<String> med = new ArrayList<String>();
                        final ArrayList<String> doc = new ArrayList<String>();
                        final ArrayList<String> stdate = new ArrayList<String>();
                        final ArrayList<String> endate = new ArrayList<String>();
                        final ArrayList<String> mtime = new ArrayList<String>();
                        final ArrayList<String> atime = new ArrayList<String>();
                        final ArrayList<String> ntime = new ArrayList<String>();
                        final ArrayList<Integer> fromid = new ArrayList<Integer>();
                        final ArrayList<Integer> toid=new ArrayList<Integer>();

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String curdate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());


                        while(cursor.moveToNext()){
                            try{
                                String enddate=cursor.getString(3);
                                Date date1=formatter.parse(enddate);
                                Date date2=formatter.parse(curdate);
                                if (date1.compareTo(date2)<0 || cursor.getInt(7)==-1)
                                {
                                    System.out.println("date2 is Greater than my date1");
                                }
                                else
                                {
                                    med.add(cursor.getString(0));
                                    doc.add(cursor.getString(1));
                                    stdate.add(cursor.getString(2));
                                    endate.add(cursor.getString(3));
                                    mtime.add(cursor.getString(4));
                                    atime.add(cursor.getString(5));
                                    ntime.add(cursor.getString(6));
                                    fromid.add(cursor.getInt(7));
                                    toid.add(cursor.getInt(8));
                                }
                            }catch(ParseException e){
                                e.printStackTrace();
                            }

                        }



                        CustomAdapterHis c = new CustomAdapterHis(MyMe.this,med,doc,stdate,endate,mtime,atime,ntime,fromid,toid);
                        lvmed.setAdapter(c);

                        //display();
                        Toast.makeText(getApplicationContext(), "Alarm Canceled", Toast.LENGTH_LONG).show();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.cancel();
                            }
                        });
                AlertDialog alert=a_builder.create();
                alert.setTitle("CANCEL ALARM");
                alert.show();

             //
            }
        });

    }

}
