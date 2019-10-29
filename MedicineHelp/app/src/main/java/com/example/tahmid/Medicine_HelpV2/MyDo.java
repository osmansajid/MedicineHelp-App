package com.example.tahmid.Medicine_HelpV2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyDo extends AppCompatActivity {

    ListView lvdoc;
    MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_do);
        lvdoc=findViewById(R.id.doclist);
        myDatabaseHelper=new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase=myDatabaseHelper.getWritableDatabase();
        //lvdoc=findViewById(R.id.doclist);
        display();
    }
    public  void  display(){
        Cursor cursor=myDatabaseHelper.getAppointment();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"You don't have any doctors",Toast.LENGTH_LONG).show();
            return;

        }

        ArrayList<String> docname = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        while(cursor.moveToNext()){
            set.add(cursor.getString(0));
        }

        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            String f = it.next();
            docname.add(f);
        }
        CustomAdapterMyDo c = new CustomAdapterMyDo(MyDo.this,docname);
        lvdoc.setAdapter(c);
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }
}
