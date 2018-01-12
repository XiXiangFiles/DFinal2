package com.example.user.dfinal2;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by USER on 2017/12/27.
 */

public class CustomList extends ArrayAdapter<String> {

    ArrayList<String>lab;
    ArrayList<String>lab2;
    ArrayList<String>info;
    ArrayList<String>item;
    ArrayList<String>color;
    Activity context;
    public CustomList(@NonNull Context context, ArrayList<String>lab,ArrayList<String>lab2,ArrayList<String>item,ArrayList<String>color,ArrayList<String>info) {
        super(context, R.layout.listview1,lab);
        this.context= (Activity) context;
        this.lab=lab;
        this.lab2=lab2;
        this.item=item;
        this.info=info;
        this.color=color;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        holder holder=null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.listview1,null,true);
            holder=new holder(r);
            r.setTag(holder);
        }else{
            holder=(holder)r.getTag();
        }
        for(int i =0;i<this.color.size();i++){
            String []str=color.get(i).split(":");
            if(str[0].equals(lab.get(position))){
                holder.t1.setBackgroundColor(Integer.parseInt(str[1]));
            }
        }
        holder.t1.setText(lab.get(position));
        holder.t2.setText(lab2.get(position));
        holder.info.setText(info.get(position).replace("=",""));
        holder.t3.setText(item.get(position)+" 元");
        return r;
    }
    class holder{
        TextView t1;
        TextView t2;
        TextView info;
        TextView t3;

        holder(View v){
            t1=v.findViewById(R.id.label_list);
            t2=v.findViewById(R.id.type_list);
            info=v.findViewById(R.id.detailinfo_l1);
            t3=v.findViewById(R.id.content_list);
        }

    }
}
