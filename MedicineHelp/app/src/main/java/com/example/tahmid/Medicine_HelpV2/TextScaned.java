package com.example.tahmid.Medicine_HelpV2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class TextScaned extends AppCompatActivity {

    private TextView textView;
    private int j=0;
    private String[] medNames=new String[8]; //Medicine names are kept in this array
    private Button buttonSearch;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_scaned);

        textView=findViewById(R.id.textId);
        buttonSearch=findViewById(R.id.searchBtnId);
        editText=findViewById(R.id.TextsearchId);

        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){

            String pres=bundle.getString("name");


            String [] lines=pres.split("\\r?\\n");

            int j=0;
            StringBuilder sb=new StringBuilder();

            for(int i=1;i<lines.length-1;i+=3){
                String s=lines[i];
                String n[]=s.split("\\.");
                String s1=n[1].trim();
                medNames[j++]=s1;
                sb.append(s1+"\n");
                //System.out.println(s1);

            }

            for (int i = 0; i < lines.length; i++) {
                textView.setText(sb.toString());


            }






        }

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String name=editText.getText().toString();
                //String res=databaseAccess.getInfo(name);
                ArrayList<String> res=databaseAccess.getInfo(name);
                databaseAccess.close();

                Intent intent=new Intent(TextScaned.this,ShowInfo.class);
                intent.putStringArrayListExtra("details",res);
                startActivity(intent);

            }
        });

        //Commit

    }
}
