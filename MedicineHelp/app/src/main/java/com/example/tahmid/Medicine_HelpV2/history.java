package com.example.tahmid.Medicine_HelpV2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class history extends AppCompatActivity {

//jnjs
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        AutoCompleteTextView edit=findViewById(R.id.medd);
        DatabaseAccess meddata=DatabaseAccess.getInstance(getApplicationContext());
        meddata.open();

        ArrayList<String> medlist= new ArrayList<String>();
        medlist=meddata.getAllmed();
        String array[] = new String[medlist.size()];
        for(int j =0;j<medlist.size();j++){
            array[j] = medlist.get(j);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        edit.setAdapter(adapter);
        // Toast.makeText(getApplicationContext(),array[0]+array[1],Toast.LENGTH_LONG).show();
    }
}
