package com.example.tahmid.Medicine_HelpV2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowMedicines extends AppCompatActivity {

    private ListView listView;
    private String[] medNames=new String[8]; //Medicine names are kept in this
    private ArrayList<String> medicieNames=new ArrayList<>();
    String pres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicines);

        listView=findViewById(R.id.medListId);


        try {

            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {

                pres = bundle.getString("name");



                String[] lines = pres.split("\\r?\\n");

                if(lines.length==1){
                    medicieNames.add(pres);

                }

                int j = 0;
                //StringBuilder sb=new StringBuilder();

                for (int i = 1; i < lines.length - 1; i += 3) {

                    if(lines.length==1){
                        medicieNames.add(pres);
                        break;
                    }

                    String s = lines[i];
                    String n[] = s.split("\\.");
                    String s1 = n[1].trim();
                    medNames[j++] = s1;
                    medicieNames.add(s1);
                    //sb.append(s1+"\n");
                    //System.out.println(s1);

                }


                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.sample_show_med_list, R.id.textViewId, medicieNames);
                listView.setAdapter(adapter);

            }
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Please wait until the whole prescription is shown on preview",Toast.LENGTH_LONG).show();

        }



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //String name=medNames[i];
                    String  name=(String) listView.getItemAtPosition(i);
                    name=name.trim();
                    name=name.toLowerCase();

                    DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();
                    //Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();

                    //String name=editText.getText().toString();
                    ArrayList<String> res=databaseAccess.getInfo(name);

                    if(res.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Sorry! No information found",Toast.LENGTH_SHORT).show();
                        databaseAccess.close();
                    }else{

                        databaseAccess.close();

                        Intent intent=new Intent(ShowMedicines.this,ShowInfo.class);
                        intent.putStringArrayListExtra("details",res);
                        startActivity(intent);
                    }




                }
            });







    }
}
