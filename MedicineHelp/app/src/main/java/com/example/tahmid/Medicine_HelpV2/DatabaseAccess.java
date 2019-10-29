package com.example.tahmid.Medicine_HelpV2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c=null;

    public DatabaseAccess(Context context){
        this.openHelper=new DataOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){

        if(instance==null){
            instance=new DatabaseAccess(context);
        }
        return instance;
    }

    public void open()
    {
        this.db=openHelper.getWritableDatabase();
    }

    public void close()
    {
        if(db!=null){
            this.db.close();
        }
    }

    public ArrayList<String> getInfo(String name)
    {
        c=db.rawQuery("SELECT * FROM MEDINFO WHERE LOWER(Brand_Name)='"+name+"'",new String[]{});
        StringBuffer buffer=new StringBuffer();
        ArrayList<String> str=new ArrayList<>();

        while (c.moveToNext()){
            String s1=c.getString(0);
            buffer.append("Brand Name: "+s1);
            buffer.append("\n\n");
            str.add(s1);

            String s2=c.getString(1);
            buffer.append("Generic Name: "+s2);
            buffer.append("\n\n");
            str.add(s2);

            String s3=c.getString(2);
            buffer.append("Indications: "+s3);
            buffer.append("\n\n");
            str.add(s3);

            String s4=c.getString(3);
            buffer.append("Doses: "+s4);
            buffer.append("\n\n");
            str.add(s4);

            String s5=c.getString(4);
            buffer.append("Side Effects: "+s5);
            buffer.append("\n\n");
            str.add(s5);
        }

        return (str);
    }

    public ArrayList<String> getAllmed(){
        ArrayList<String> medlist = new ArrayList<String>();
        c=db.rawQuery("SELECT * FROM MEDINFO",new String[]{} );
        while(c.moveToNext()){
            medlist.add(c.getString(0));
        }
        return  medlist;
    }

}
