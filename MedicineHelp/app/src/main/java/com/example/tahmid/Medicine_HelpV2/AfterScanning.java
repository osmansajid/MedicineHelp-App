package com.example.tahmid.Medicine_HelpV2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AfterScanning extends AppCompatActivity {

    MyDatabaseHelper myDatabaseHelper;
    private TextView textView;
    ProgressDialog progressDialog;
    public String doctorName;
    public String nextAppointment;
    public int appDay,appMonth,appYear;  //Next appointment day,month,year
    //This arraylist holds objects of 'PrescriptionDetails' class for every medicine_name
    String morning,afternoon,night;
    StringBuilder sb=new StringBuilder(); //This StringBuilder was used just for testing purpose
    int count,dday,mmonth,yyear;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_scanning);
        ArrayList<PrescriptionDetails> pd=new ArrayList<>();
        myDatabaseHelper=new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase=myDatabaseHelper.getWritableDatabase();

        textView=findViewById(R.id.tvId);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Scanning..");
        progressDialog.show();


        try{

            Bundle bundle=getIntent().getExtras();

            if(bundle!=null){
                String s=bundle.getString("name");
                //textView.setText(s);

                String lines[]=s.split("\\r?\\n"); //The whole prescription(came as a string) is splitted by newline as delimeter
                String[] temp1=lines[0].split(":");
                doctorName=temp1[1].trim();  //This string holds the doctors's name

                String temp2[]=lines[lines.length-1].split(":");
                nextAppointment=temp2[1].trim(); //This string holds next appointment date(day/month/year)

                setAppointmentDate(); //This function splits 'nextAppointment' string into day,month and year

                for(int i=1;i<lines.length-1;i+=3){
                    String name=getName(lines[i]); //This string holds medicine name,returned by getName() function.
                    setDoses(lines[i+1]); //This function parse doses for morning, noon and night and set corresponding strings as 1/0
                    String duration=getDuration(lines[i+2]); //This function returns duration for that medicine
                    PrescriptionDetails prescriptionDetails=new PrescriptionDetails(name,morning,afternoon,night,duration); //For every medicine, an object is created by it's name,doses and duration.
                    pd.add(prescriptionDetails); //The object of every medicine is added in this arraylist.

                }

                //sb.append(doctorName+"   "+nextAppointment+"\n");



                for(int i=0;i<pd.size();i++){
                    String str=pd.get(i).getName();
                    sb.append(str+"\n");
                    sb.append(pd.get(i).getMorning()+"+ "+pd.get(i).getAfternoon()+" +"+pd.get(i).getNight()+"\n"+pd.get(i).getDuration()+"\n");
                }

                sb.append(Integer.toString(appDay)+" "+Integer.toString(appMonth)+" "+Integer.toString(appYear)+"\n");

                textView.setText(sb.toString());
                progressDialog.cancel();


            }
            String day,mon,year;
            day=nextAppointment.substring(0,1)+nextAppointment.substring(1,2);
            mon=nextAppointment.substring(3,4)+nextAppointment.substring(4,5);
            year=nextAppointment.substring(6,7)+nextAppointment.substring(7,8)+nextAppointment.substring(8,9)+nextAppointment.substring(9,10);
            SharedPreferences s= PreferenceManager.getDefaultSharedPreferences(AfterScanning.this);
            String ppp=s.getString("val","");
            count=Integer.parseInt(ppp);
            dday=Integer.parseInt(day);
            mmonth=Integer.parseInt(mon);
            yyear=Integer.parseInt(year);
            String selectedDate=dday+" / "+mmonth+" / "+yyear+"";

            long rowID=myDatabaseHelper.inserintoAppointment(doctorName,selectedDate,ppp);
            if(rowID!=-1){
                try{
                    Calendar calendar=Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,0);
                    calendar.set(Calendar.MINUTE,0);
                    calendar.set(Calendar.SECOND,0);
                    calendar.set(Calendar.DAY_OF_MONTH,dday);
                    calendar.set(Calendar.MONTH,mmonth-1);
                    calendar.set(Calendar.YEAR,yyear);
                    Intent intent=new Intent(AfterScanning.this, Notification_receiver.class);
                    intent.putExtra("DOC",doctorName);
                    intent.putExtra("CNT",ppp);
                    intent.setAction("MY_NOTIFICATION_MESSAGE");
                    PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),count,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                    //Toast.makeText(getApplicationContext(),ppp,Toast.LENGTH_LONG).show();
                    count++;
                    s.edit().putString("val",count+"").commit();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Something went wrong!\nPlease try again to take the photo",Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(),"Something went wrong!\nPlease try again to take the photo",Toast.LENGTH_LONG).show();
            }
            try {
                for(int ii=0;ii<pd.size();ii++){
                    String xx=pd.get(ii).duration;
                    String wow=pd.get(ii).name;
                    String doc=doctorName;
                    Calendar cal=Calendar.getInstance();
                    cal.add(Calendar.DAY_OF_MONTH,(1));
                    String stdate=cal.get(Calendar.DAY_OF_MONTH)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.YEAR);
                    String endate="";
                    String mtime="N/A";
                    String atime="N/A";
                    String ntime="N/A";
                    int mm=Integer.parseInt(pd.get(ii).morning);
                    int aa=Integer.parseInt(pd.get(ii).afternoon);
                    int nn=Integer.parseInt(pd.get(ii).night);
                    String pp=s.getString("val","");
                    count=Integer.parseInt(pp);
                    int fromid=count;
                    int len=Integer.parseInt(xx);
                    for(int i=1;i<=len;i++){

                        String cnt;
                        if(mm==1){
                            String pppp=s.getString("val","");
                            count=Integer.parseInt(pppp);
                            mtime="8:00";
                            cnt=Integer.toString(count);

                            Calendar calendar=Calendar.getInstance();
                            calendar.add(Calendar.DAY_OF_MONTH,(i));
                            calendar.set(Calendar.HOUR_OF_DAY,8);
                            calendar.set(Calendar.MINUTE,0);
                            calendar.set(Calendar.SECOND,0);
                            endate=calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);

                            // Toast.makeText(getApplicationContext(),wow+mhour+":"+mmin+" and "+ ahour +":"+ amin+" and "+calendar.get(Calendar.DAY_OF_MONTH)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR),Toast.LENGTH_LONG).show();


                            Intent intent=new Intent(AfterScanning.this, Notification_receiver_med.class);
                            intent.putExtra("MED",wow);
                            intent.putExtra("CNT",cnt);
                            intent.setAction("MY_NOTIFICATION_MESSAGE_");
                            PendingIntent pendingIntent=PendingIntent.getBroadcast(AfterScanning.this,count,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                            //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                            //Toast.makeText(getApplicationContext(),selectedDate,Toast.LENGTH_LONG).show();

                            count++;
                            //Toast.makeText(getApplicationContext(),count+"",Toast.LENGTH_LONG).show();
                            s.edit().putString("val",count+"").commit();
                        }

                        if(aa==1){
                            String pppp=s.getString("val","");
                            count=Integer.parseInt(pppp);
                            atime="14:00";
                            cnt=Integer.toString(count);
                            //Toast.makeText(getApplicationContext(),mhour+":"+mmin+" and "+dday+"/"+(mmonth+1)+"/"+yyear,Toast.LENGTH_LONG).show();
                            Calendar ccalendar=Calendar.getInstance();
                            ccalendar.add(Calendar.DAY_OF_MONTH,(i));
                            ccalendar.set(Calendar.HOUR_OF_DAY,14);
                            ccalendar.set(Calendar.MINUTE,0);
                            ccalendar.set(Calendar.SECOND,0);
                            endate=ccalendar.get(Calendar.DAY_OF_MONTH)+"/"+(ccalendar.get(Calendar.MONTH)+1)+"/"+ccalendar.get(Calendar.YEAR);

                            Intent iintent=new Intent(AfterScanning.this, Notification_receiver_med.class);
                            iintent.putExtra("MED",wow);
                            iintent.putExtra("CNT",cnt);
                            iintent.setAction("MY_NOTIFICATION_MESSAGE_");
                            PendingIntent ppendingIntent=PendingIntent.getBroadcast(AfterScanning.this,count,iintent,PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager aalarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                            //aalarmManager.setRepeating(AlarmManager.RTC_WAKEUP,ccalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,ppendingIntent);
                            //Toast.makeText(getApplicationContext(),selectedDate,Toast.LENGTH_LONG).show();
                            aalarmManager.set(AlarmManager.RTC_WAKEUP,ccalendar.getTimeInMillis(),ppendingIntent);
                            //intentArray2.add(ppendingIntent);
                            count++;
                            s.edit().putString("val",count+"").commit();
                        }

                        if(nn==1){
                            String pppp=s.getString("val","");
                            count=Integer.parseInt(pppp);
                            ntime="20:00";
                            cnt=Integer.toString(count);
                            //Toast.makeText(getApplicationContext(),ntime+" and "+dday+"/"+(mmonth+1)+"/"+yyear,Toast.LENGTH_LONG).show();
                            Calendar cccalendar=Calendar.getInstance();
                            cccalendar.add(Calendar.DAY_OF_MONTH,(i));
                            cccalendar.set(Calendar.HOUR_OF_DAY,20);
                            cccalendar.set(Calendar.MINUTE,0);
                            cccalendar.set(Calendar.SECOND,0);
                            endate=cccalendar.get(Calendar.DAY_OF_MONTH)+"/"+(cccalendar.get(Calendar.MONTH)+1)+"/"+cccalendar.get(Calendar.YEAR);

                            Intent iiintent=new Intent(AfterScanning.this, Notification_receiver_med.class);
                            iiintent.putExtra("MED",wow);
                            iiintent.putExtra("CNT",cnt);
                            iiintent.setAction("MY_NOTIFICATION_MESSAGE_");
                            PendingIntent pppendingIntent=PendingIntent.getBroadcast(AfterScanning.this,count,iiintent,PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager aaalarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
                            //aaalarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cccalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pppendingIntent);
                            //Toast.makeText(getApplicationContext(),selectedDate,Toast.LENGTH_LONG).show();
                            aaalarmManager.set(AlarmManager.RTC_WAKEUP,cccalendar.getTimeInMillis(),pppendingIntent);
                            //intentArray3.add(pppendingIntent);
                            count++;
                            s.edit().putString("val",count+"").commit();
                        }
                    }
                    int toid=count-1;
                    //Toast.makeText(getApplicationContext(),ntime,Toast.LENGTH_LONG).show();
                    rowID=myDatabaseHelper.insertintoHistory(wow,doc,stdate,endate,mtime,atime,ntime,fromid,toid);
                    if(rowID==-1){
                        Toast.makeText(getApplicationContext(),"Something went wrong!\nPlease try again to take the photo",Toast.LENGTH_LONG).show();
                    }
                    else{
                        //Toast.makeText(getApplicationContext(),"Row successfully inserted",Toast.LENGTH_LONG).show();

                    }

                    //   Toast.makeText(getApplicationContext(),"Reminder saved",Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Something went wrong!\nPlease try again to take the photo",Toast.LENGTH_LONG).show();
            }


        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Something went wrong!\nPlease try again to take the photo",Toast.LENGTH_LONG).show();
            progressDialog.cancel();
        }

       // Toast.makeText(getApplicationContext(),Calendar.getInstance().toString(),Toast.LENGTH_LONG).show();


    }

    String getName(String s)
    {
        String n[]=s.split("\\.");
        String s1=n[1].trim();
        //sb.append(s1+"\n");
        return  s1;

    }

    void setDoses(String s)
    {
        String[] n=s.split(":");
        String s1=n[1].trim();
        //sb.append(s1+"\n");
        String temp[]=s1.split("\\+");
        //sb.append(temp[0]+" "+temp[1]+" "+temp[2]+"\n");

        morning=temp[0];
        afternoon=temp[1];
        night=temp[2];
    }

    String getDuration(String s)
    {
        String [] n=s.split(":");
        String s1=n[1].trim();
        //sb.append(s1+"\n");
        return  s1;
    }


    void setAppointmentDate()
    {
        String tokens[]=nextAppointment.split("/");
        appDay=Integer.parseInt(tokens[0]);
        appMonth=Integer.parseInt(tokens[1]);
        appYear=Integer.parseInt(tokens[2]);
        //sb.append(tokens[0]+" "+tokens[1]+" "+tokens[2]);
    }


}
