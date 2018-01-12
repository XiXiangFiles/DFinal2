package com.example.user.dfinal2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.user.dfinal2.R.drawable.logo;

public class mainpage extends Activity {

    String U_Num="";
    String U_Name="";
    String keep="";
    String Email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        Intent intent = this.getIntent();
        ArrayList<String> a=intent.getStringArrayListExtra("User");
        Map<String,String> map=new HashMap<>();

        for (String str: a) {
            System.out.println("DATA=\t"+str);
            String []items=str.split(":");
            map.put(items[0],items[1]);
        }
        for(Map.Entry<String,String> user:map.entrySet()){
            if(user.getKey().equals("U_Num")){
                this.U_Num=user.getValue();
            }
            if(user.getKey().equals("U_Name")){
                this.U_Name=user.getValue();
            }
            if(user.getKey().equals("Email")){
                this.Email=user.getValue();
            }
            if(user.getKey().equals("Keep")){
                this.keep=user.getValue();
            }
        }
        System.out.println("U_Num=\t"+this.U_Num);
        System.out.println("U_Name\t"+this.U_Name);

        ArrayList<String>data=new ArrayList<>();
        data.add("U_Name:"+this.U_Name);
        data.add("U_Num:"+this.U_Num);
        data.add("Email:"+Email);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("User",data);


        try {
            DBsqlite db=new DBsqlite(this);

            DBsqlite dBsqlite= new DBsqlite(this);
            ArrayList a1=dBsqlite.getAll(dBsqlite,map);
            if(a1.size()==0){
                db.insert(db,map);
            }else{
                if (keep.equals("no")){
                    System.out.println("Keep=\t"+keep);
                    System.out.println("Email=\t"+Email);
                    db.delete(db,Email);
                }
            }
        }catch (Exception e){

        }



        FragmentManager FM=getFragmentManager();
        FragmentTransaction FMT=FM.beginTransaction();
        Account account=new Account();
        FMT.replace(R.id.f_container,account);
        account.setArguments(bundle);
        FMT.commit();



        ImageButton mainpage_bt=(ImageButton)findViewById(R.id.mainpage_bt);
        mainpage_bt.setOnClickListener((View v)->{
            FragmentManager FMfirst=getFragmentManager();
            FragmentTransaction FMfistFT=FMfirst.beginTransaction();
            Account account1=new Account();
            account1.setArguments(bundle);
            FMfistFT.replace(R.id.f_container,account1);
            FMfistFT.commit();
            ImageButton mainpage_bt1=(ImageButton)findViewById(R.id.mainpage_bt);
            Resources res = getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.account1);
            mainpage_bt1.setImageBitmap(bmp);

            ImageButton mainpage_bt2=(ImageButton)findViewById(R.id.Cards);
            Bitmap bmp2 = BitmapFactory.decodeResource(res, R.drawable.card2);
            mainpage_bt2.setImageBitmap(bmp2);
            ImageButton mainpage_bt3=(ImageButton)findViewById(R.id.Location);
            Bitmap bmp3 = BitmapFactory.decodeResource(res, R.drawable.location1);
            mainpage_bt3.setImageBitmap(bmp3);

            ImageButton mainpage_bt4=(ImageButton)findViewById(R.id.Setting);
            Bitmap bmp4 = BitmapFactory.decodeResource(res, R.drawable.setting1);
            mainpage_bt4.setImageBitmap(bmp4);


        });


        ImageButton cards=(ImageButton)findViewById(R.id.Cards);
        cards.setOnClickListener((View v)->{
            FragmentManager FMfirst=getFragmentManager();
            FragmentTransaction FMfistFT=FMfirst.beginTransaction();
            Cards cards1=new Cards();
            cards1.setArguments(bundle);
            FMfistFT.replace(R.id.f_container,cards1);
            FMfistFT.commit();
            ImageButton mainpage_bt1=(ImageButton)findViewById(R.id.mainpage_bt);
            Resources res = getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.account2);
            mainpage_bt1.setImageBitmap(bmp);
            ImageButton mainpage_bt2=(ImageButton)findViewById(R.id.Cards);
            Bitmap bmp2 = BitmapFactory.decodeResource(res, R.drawable.card1);
            mainpage_bt2.setImageBitmap(bmp2);
            ImageButton mainpage_bt3=(ImageButton)findViewById(R.id.Location);
            Bitmap bmp3 = BitmapFactory.decodeResource(res, R.drawable.location1);
            mainpage_bt3.setImageBitmap(bmp3);
            ImageButton mainpage_bt4=(ImageButton)findViewById(R.id.Setting);
            Bitmap bmp4 = BitmapFactory.decodeResource(res, R.drawable.setting1);
            mainpage_bt4.setImageBitmap(bmp4);

        });

        ImageButton locate=(ImageButton)findViewById(R.id.Location);
        locate.setOnClickListener((View v)->{
            FragmentManager FMfirst=getFragmentManager();
            FragmentTransaction FMfistFT=FMfirst.beginTransaction();
            maptest loc=new maptest();
            loc.setArguments(bundle);
            FMfistFT.replace(R.id.f_container,loc);
            FMfistFT.commit();
            ImageButton mainpage_bt1=(ImageButton)findViewById(R.id.mainpage_bt);
            Resources res = getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.account2);
            mainpage_bt1.setImageBitmap(bmp);
            ImageButton mainpage_bt2=(ImageButton)findViewById(R.id.Cards);
            Bitmap bmp2 = BitmapFactory.decodeResource(res, R.drawable.card2);
            mainpage_bt2.setImageBitmap(bmp2);
            ImageButton mainpage_bt3=(ImageButton)findViewById(R.id.Location);
            Bitmap bmp3 = BitmapFactory.decodeResource(res, R.drawable.location2);
            mainpage_bt3.setImageBitmap(bmp3);
            ImageButton mainpage_bt4=(ImageButton)findViewById(R.id.Setting);
            Bitmap bmp4 = BitmapFactory.decodeResource(res, R.drawable.setting1);
            mainpage_bt4.setImageBitmap(bmp4);

        });

        ImageButton setting=(ImageButton)findViewById(R.id.Setting);
        setting.setOnClickListener((View v)->{
            FragmentManager FMSec=getFragmentManager();
            FragmentTransaction FMSecFT=FMSec.beginTransaction();
            System.out.println("Click!!");
            Setting set=new Setting();
            FMSecFT.replace(R.id.f_container,set);
            set.setArguments(bundle);
            FMSecFT.commit();
            ImageButton mainpage_bt1=(ImageButton)findViewById(R.id.mainpage_bt);
            Resources res = getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.account2);
            mainpage_bt1.setImageBitmap(bmp);
            ImageButton mainpage_bt2=(ImageButton)findViewById(R.id.Cards);
            Bitmap bmp2 = BitmapFactory.decodeResource(res, R.drawable.card2);
            mainpage_bt2.setImageBitmap(bmp2);

            ImageButton mainpage_bt3=(ImageButton)findViewById(R.id.Location);
            Bitmap bmp3 = BitmapFactory.decodeResource(res, R.drawable.location1);
            mainpage_bt3.setImageBitmap(bmp3);

            ImageButton mainpage_bt4=(ImageButton)findViewById(R.id.Setting);
            Bitmap bmp4 = BitmapFactory.decodeResource(res, R.drawable.setting2);
            mainpage_bt4.setImageBitmap(bmp4);
        });

        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //要做的事情
                try {
                    subscribe("tcp://140.119.163.195","1883","DFinal");
                }catch (Exception e){

                }

//                handler.postDelayed(this, 10000);
            }
        };
        handler.postDelayed(runnable, 0);
    }
    public void subscribe(String Uri,String port,String Toptic) {
        Random ran = new Random();
//        System.out.println(ran.nextInt(100000)+1);
        String clientId = Email+ran.nextInt(100000)+1;
        MemoryPersistence persistence = new MemoryPersistence();
        try{
            MqttAsyncClient sampleClient = new MqttAsyncClient(Uri+":"+port, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {

                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    System.out.println(mqttMessage.toString());
                    String []str=mqttMessage.toString().trim().split(":");
                    if (str[0].equals(Email)||str[0].equals("all")){
                        runOnUiThread(()->{
                            Bitmap largeIcon = BitmapFactory.decodeResource(
                                    getResources(), R.drawable.logo);
                            NotificationManager manager = (NotificationManager)
                                    getSystemService(Context.NOTIFICATION_SERVICE);
                            NotificationCompat.Builder builder =
                                    new NotificationCompat.Builder(mainpage.this);
                            builder.setSmallIcon(R.drawable.dollarcoinmoney)
                                    .setLargeIcon(largeIcon)
                                    .setWhen(System.currentTimeMillis())
                                    .setContentTitle("智能理財小幫手 ：")
                                    .setContentText(str[1])
                                    .setDefaults(Notification.DEFAULT_VIBRATE);
                            manager.notify(1, builder.build());
                            Intent intent=new Intent(mainpage.this,floatwindow.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("content",str[1]);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        });
                    }else{

                    }


                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
            IMqttToken conToken = sampleClient.connect(connOpts);
            conToken.waitForCompletion();
            IMqttToken subToken = sampleClient.subscribe(Toptic, 0, null, null);
            subToken.waitForCompletion();

        }catch (Exception e){
//            subscribe(Uri,port,Toptic);Topti
        }

//        sampleClient.disconnect();
    }



}
