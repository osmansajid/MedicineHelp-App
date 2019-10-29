package com.example.tahmid.Medicine_HelpV2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.*;

public class AmbulanceActitivity extends AppCompatActivity {

    private ListView listView;
    String items[] = new String[]{"01912404863", "01992364749", "01992368990"};
    String[] ambulanceNames;
    String[] ambulanceNumbers;
  String m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_actitivity);

        listView = findViewById(R.id.ambulanceListId);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, items);
        //l1.setAdapter(adapter);

        ambulanceNames=getResources().getStringArray(R.array.name);
        ambulanceNumbers=getResources().getStringArray(R.array.numbers);

        CustomAdapter adapter=new CustomAdapter(AmbulanceActitivity.this,ambulanceNames,ambulanceNumbers);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent callintent = new Intent(Intent.ACTION_CALL);
                callintent.setData(Uri.parse("tel:"+ambulanceNumbers[i]));
                startActivity(callintent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }


}
