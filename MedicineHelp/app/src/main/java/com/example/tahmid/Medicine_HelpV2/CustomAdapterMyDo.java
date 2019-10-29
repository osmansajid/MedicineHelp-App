package com.example.tahmid.Medicine_HelpV2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterMyDo extends BaseAdapter {

    List<String> doc;
    Context context;
    private LayoutInflater inflater;

    public CustomAdapterMyDo(Context c,List doc) {
        this.context=c;
        this.doc = doc;
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
            view=inflater.inflate(R.layout.samplemydoc,viewGroup,false);

        }

        TextView textView1=view.findViewById(R.id.tvmdoc);

        textView1.setText("Doctor's Name:\n"+doc.get(i));
        return  view;
    }
}
