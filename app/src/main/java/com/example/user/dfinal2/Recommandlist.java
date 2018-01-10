package com.example.user.dfinal2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by USER on 2017/12/31.
 */

public class Recommandlist extends ArrayAdapter<String> {

    ArrayList<Bitmap> lab;
    ArrayList<String>lab2;
    ArrayList<String>item;
    Activity context;
    public Recommandlist(@NonNull Context context, ArrayList<Bitmap>lab,ArrayList<String>lab2,ArrayList<String>item) {
        super(context, R.layout.listview2,lab2);
        this.context= (Activity) context;
        this.lab=lab;
        this.lab2=lab2;
        this.item=item;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.listview2, null, true);
        TextView  androidos = (TextView) listViewItem.findViewById(R.id.Lcontent);
        TextView textView = (TextView) listViewItem.findViewById(R.id.Lttitle);
        textView.setText(item.get(position));
        androidos.setText(lab2.get(position));
        ImageView image = (ImageView) listViewItem.findViewById(R.id.liveiw);
        image.setImageBitmap(lab.get(position));
        return  listViewItem;
    }

}
