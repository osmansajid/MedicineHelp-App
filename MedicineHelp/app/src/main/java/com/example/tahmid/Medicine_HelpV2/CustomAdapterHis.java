package com.example.tahmid.Medicine_HelpV2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapterHis extends BaseAdapter {
    List<String> med,doc,stdate,endate,mtime,atime,ntime,id;
    List<Integer> toid,fromid;
    Context context;
    private LayoutInflater inflater;

    public CustomAdapterHis(Context c,List med,List doc,List stdate,List endate,List mtime,List atime,List ntime,List toid,List fromid) {
        this.context=c;
        this.med = med;
        this.doc=doc;
        this.stdate=stdate;
        this.endate=endate;
        this.mtime=mtime;
        this.atime=atime;
        this.ntime=ntime;
        this.toid=toid;
        this.fromid=fromid;
    }

    @Override
    public int getCount() {
        return med.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.samplehis,viewGroup,false);

        }

        TextView textView1=view.findViewById(R.id.tvmed);
        TextView textView2=view.findViewById(R.id.tvdoc);
        TextView textView3=view.findViewById(R.id.tvstdate);
        TextView textView4=view.findViewById(R.id.tvendate);
        TextView textView5=view.findViewById(R.id.tvmtime);
        TextView textView6=view.findViewById(R.id.tvatime);
        TextView textView7=view.findViewById(R.id.tvntime);

        textView1.setText("Medicine Name: "+med.get(i));
        textView2.setText("Doctor's Name: "+doc.get(i));
        textView3.setText("Start Date: "+stdate.get(i));
        textView4.setText("End Date: "+endate.get(i));
        textView5.setText("Morning Time: "+mtime.get(i));
        textView6.setText("Afternoon Time: "+atime.get(i));
        textView7.setText("Night Time: "+ntime.get(i));
        return  view;
    }
}
