package com.example.tahmid.Medicine_HelpV2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    Context context;
    String[] ambulanceNames;
    String[] ambulanceNumbers;
    LayoutInflater inflater;

    public CustomAdapter(Context context, String[] ambulanceNames, String[] ambulanceNumbers) {
        this.context = context;
        this.ambulanceNames = ambulanceNames;
        this.ambulanceNumbers = ambulanceNumbers;
    }

    @Override
    public int getCount() {
        return ambulanceNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view==null){

            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view= inflater.inflate(R.layout.sample_ambulance,viewGroup,false);

        }

        TextView textView1= view.findViewById(R.id.nameId);
        TextView textView2=view.findViewById(R.id.numberId);

        textView1.setText(ambulanceNames[i]);
        textView2.setText(ambulanceNumbers[i]);

        return view;
    }
}
