package com.example.tahmid.Medicine_HelpV2;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MedicineInfoActivity extends AppCompatActivity {

    private CardView photoSearch;
    private Button searchButton;
    private AutoCompleteTextView searchText;
    private ImageButton pht;
    private TextInputLayout med_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_info);

        photoSearch=findViewById(R.id.photoId);
        searchButton=findViewById(R.id.button);
        searchText=findViewById(R.id.editText3);//////----------------
        pht=findViewById(R.id.button2);
        med_name=findViewById(R.id.medicine_name);

        DatabaseAccess meddata=DatabaseAccess.getInstance(getApplicationContext());
        meddata.open();

        ArrayList<String> medlist= new ArrayList<String>();
        medlist=meddata.getAllmed();
        String array[] = new String[medlist.size()];
        for(int j =0;j<medlist.size();j++){
            array[j] = medlist.get(j);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        searchText.setAdapter(adapter);

        pht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MedicineInfoActivity.this,SearchByPhoto.class);
                startActivity(intent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseAccess databaseAccess=DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String name=searchText.getText().toString();//---------------------------
                name=name.toLowerCase();
                //String res=databaseAccess.getInfo(name);
                ArrayList<String> res=databaseAccess.getInfo(name);

                //String mednameintput=med_name.getEditText().getText().toString().trim();
               /* if (mednameintput.isEmpty()){
                    med_name.setError("Field can't be empty");
                    //return false;
                }
                else{
                    med_name.setError(null);
                    //return true;
                }*/

                if(res.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Sorry! No information found",Toast.LENGTH_SHORT).show();
                    databaseAccess.close();
                }else{

                    databaseAccess.close();

                    Intent intent=new Intent(MedicineInfoActivity.this,ShowInfo.class);
                    intent.putStringArrayListExtra("details",res);
                    startActivity(intent);
                }
            }
        });


    }






    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }
}
