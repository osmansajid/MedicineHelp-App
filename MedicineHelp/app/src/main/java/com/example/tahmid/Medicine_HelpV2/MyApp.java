package com.example.tahmid.Medicine_HelpV2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyApp extends AppCompatActivity {

    ListView lvapp;
    MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_app);
        myDatabaseHelper=new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase=myDatabaseHelper.getWritableDatabase();

        lvapp=findViewById(R.id.listV);
        display();
    }
    public  void  display(){
        Cursor cursor=myDatabaseHelper.getAppointment();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"You don't have any appointment",Toast.LENGTH_LONG).show();
            return;

        }
        ArrayList<String> docname = new ArrayList<String>();
        ArrayList<String> apptdate = new ArrayList<String>();
        final ArrayList<String> appid = new ArrayList<String>();
        while(cursor.moveToNext()){
            docname.add(cursor.getString(0));
            apptdate.add(cursor.getString(1));
            appid.add(cursor.getString(2));
        }
        CustomAdapterDoc c = new CustomAdapterDoc(MyApp.this,docname,apptdate);
        lvapp.setAdapter(c);

        lvapp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int in=i;

                AlertDialog.Builder a_builder=new AlertDialog.Builder(MyApp.this);
                a_builder.setMessage("Do you want to turn off the alarm?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String from=appid.get(in);
                                int haha=Integer.parseInt(from);
                                AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                                Intent intent=new Intent(MyApp.this, Notification_receiver.class);
                                intent.putExtra("CNT",from);
                                intent.setAction("MY_NOTIFICATION_MESSAGE");
                                PendingIntent pendingIntent=PendingIntent.getBroadcast(MyApp.this,haha,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                                alarmManager.cancel(pendingIntent);
                                myDatabaseHelper.deleteapp(appid.get(in));

                                Cursor cursor=myDatabaseHelper.getAppointment();
                                if(cursor.getCount()==0){
                                    Toast.makeText(getApplicationContext(),"You don't have any appointment",Toast.LENGTH_LONG).show();

                                }
                                ArrayList<String> docname = new ArrayList<String>();
                                ArrayList<String> apptdate = new ArrayList<String>();
                                final ArrayList<String> appid = new ArrayList<String>();
                                while(cursor.moveToNext()){
                                    docname.add(cursor.getString(0));
                                    apptdate.add(cursor.getString(1));
                                    appid.add(cursor.getString(2));
                                }
                                CustomAdapterDoc c = new CustomAdapterDoc(MyApp.this,docname,apptdate);
                                lvapp.setAdapter(c);

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
            }
        });
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }
}
