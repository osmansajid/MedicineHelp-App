package com.example.tahmid.Medicine_HelpV2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="Medicine.db";
    private static final int VERSION_NO=4;
    private Context context;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null,VERSION_NO);
        this.context=context;
    }
// hello
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try{
            Toast.makeText(context,"On Create",Toast.LENGTH_LONG).show();
            sqLiteDatabase.execSQL("CREATE TABLE APPOINTMENT(Doctors_Name VARCHAR(1000),Appointment_Date VARCHAR(1000), App_ID VARCHAR(1000));");
            sqLiteDatabase.execSQL("CREATE TABLE HISTORY(Medicine_Name VARCHAR(1000),Doctors_Name VARCHAR(1000),Start_Date VARCHAR(1000),End_Date VARCHAR(1000),Morning_Time VARCHAR(1000),Afternoon_Time VARCHAR(1000),Night_Time VARCHAR(1000),FROMID NUMBER(100),TOID NUMBER(100));");

        }catch (Exception e)
        {
            Toast.makeText(context,"Something went wrong!",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try{
            Toast.makeText(context,"On Upgrade",Toast.LENGTH_LONG).show();
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS APPOINTMENT ;");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS HISTORY ;");
            onCreate(sqLiteDatabase);
        }catch (Exception e)
        {
            Toast.makeText(context,"Something went wrong!",Toast.LENGTH_LONG).show();
        }


    }

    public long inserintoAppointment(String Doctor,String Date,String ID){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Doctors_Name",Doctor);
        contentValues.put("Appointment_Date",Date);
        contentValues.put("App_ID",ID);
        return sqLiteDatabase.insert("APPOINTMENT",null,contentValues);
    }

    public Cursor getAppointment(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM APPOINTMENT ;",null);
    }

    public long insertintoHistory(String Med,String Doc,String Stdate,String Endate,String Mtime,String Atime,String Ntime,int fromid,int toid){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Medicine_Name",Med);
        contentValues.put("Doctors_Name",Doc);
        contentValues.put("Start_Date",Stdate);
        contentValues.put("End_Date",Endate);
        contentValues.put("Morning_Time",Mtime);
        contentValues.put("Afternoon_Time",Atime);
        contentValues.put("Night_Time",Ntime);
        contentValues.put("FROMID",fromid);
        contentValues.put("TOID",toid);
        return sqLiteDatabase.insert("HISTORY",null,contentValues);
    }

    public Cursor getHistory(){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM HISTORY ;",null);
    }

    public  boolean updatehis(String Med,String Doc,String Stdate,String Endate,String Mtime,String Atime,String Ntime,int fromid,int toid){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Medicine_Name",Med);
        contentValues.put("Doctors_Name",Doc);
        contentValues.put("Start_Date",Stdate);
        contentValues.put("End_Date",Endate);
        contentValues.put("Morning_Time",Mtime);
        contentValues.put("Afternoon_Time",Atime);
        contentValues.put("Night_Time",Ntime);
        contentValues.put("FROMID",-1);
        contentValues.put("TOID",toid);
        sqLiteDatabase.update("HISTORY",contentValues,"FROMID = ?",new String[]{fromid+""});
        return true;
    }

    public  int deleteapp(String ID){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        return  sqLiteDatabase.delete("APPOINTMENT","App_ID = ?",new String[]{ ID });
    }
    public  int deletehis(int ID){

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        String id=Integer.toString(ID);
        return  sqLiteDatabase.delete("HISTORY","FROMID = ?",new String[]{ id });
    }

}
