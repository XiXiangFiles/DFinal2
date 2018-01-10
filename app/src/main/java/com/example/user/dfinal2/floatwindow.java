package com.example.user.dfinal2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class floatwindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floatwindow);

        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        getWindow().setLayout(displayMetrics.widthPixels,(int)(displayMetrics.heightPixels*0.12));
        WindowManager.LayoutParams p=getWindow().getAttributes();
        p.gravity= Gravity.TOP;
        getWindow().setAttributes(p);
        Intent intent = this.getIntent();
        String content=intent.getStringExtra("content");
        TextView textView=(TextView)findViewById(R.id.notificationcontent);
        textView.setText(content);
        Timer timer=new Timer();
        TimerTask task= new TimerTask() {
            @Override
            public void run() {

                finish();
            }
        };
        timer.schedule(task,3000);

    }
}
