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

public class cardslistview extends ArrayAdapter<String> {
    ArrayList<Bitmap> lab;
    ArrayList<String>lab2;
    Activity context;
    public cardslistview(@NonNull Context context, ArrayList<Bitmap>lab,ArrayList<String>lab2) {
        super(context, R.layout.layout_cards,lab2);
        this.context= (Activity) context;
        this.lab=lab;
        this.lab2=lab2;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_cards, null, true);
        TextView  androidos = (TextView) listViewItem.findViewById(R.id.cardsinfos);
        androidos.setText(lab2.get(position));
        ImageView image = (ImageView) listViewItem.findViewById(R.id.cardimge);
        image.setImageBitmap(lab.get(position));
        ImageView image1 = (ImageView) listViewItem.findViewById(R.id.leftbar);
        image1.setImageDrawable(listViewItem.getResources().getDrawable(R.drawable.leftbar));
        ImageView image2 = (ImageView) listViewItem.findViewById(R.id.contentlogo);
        image2.setImageDrawable(listViewItem.getResources().getDrawable(R.drawable.cardinfo));
        return  listViewItem;
    }

}
