package com.example.tahmid.Medicine_HelpV2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterDoc extends BaseAdapter {
    List<String> doc,appt;
    Context context;
    private LayoutInflater inflater;

    public CustomAdapterDoc(Context c,List doc,List appt) {
        this.context=c;
        this.doc = doc;
        this.appt=appt;
    }

    @Override
    public int getCount() {
        return doc.size();
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
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.sampledoc,viewGroup,false);

        }

        TextView textView1=view.findViewById(R.id.tvdoc);
        TextView textView2=view.findViewById(R.id.tvdate);

        textView1.setText("Doctor's Name:\n"+doc.get(i));
        textView2.setText("Appointment Date:\n"+appt.get(i));
        return  view;
    }
}
