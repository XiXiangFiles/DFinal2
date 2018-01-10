package com.example.user.dfinal2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cardsinfodetail extends AppCompatActivity {
    Bundle bundle = new Bundle();
    String U_Name="";
    String U_Num="";
    String C_Num="";
    String C_Name="";
    ArrayList<String> a1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardsinfodetail);
        Intent intent = this.getIntent();
        ArrayList<String> a=intent.getStringArrayListExtra("User");
        this.bundle.putStringArrayList("User",a);
//        this.a1=a;
        Map<String,String> map=new HashMap<>();

        for (String str: a) {
            System.out.println("DATA=\t"+str);
            String []items=str.split(":");
            if (str.contains("U_Name")){
                this.U_Name=items[1];
                this.a1.add(str);
            }
            if (str.contains("U_Num")){
                this.U_Num=items[1];
                this.a1.add(str);
            }
            if (str.contains("Email")){

                this.a1.add(str);
            }
            if (str.contains("C_Num")){
                this.C_Num=items[1];
            }
            if (str.contains("C_Name")){
                this.C_Name=items[1];
            }
            map.put(items[0],items[1]);
        }

        TextView cardsdetail=(TextView)findViewById(R.id.cardsdetail);
        cardsdetail.setText(this.C_Name);
        MyTask myTask=new MyTask(this,"http://140.119.163.195/DFapp/findDiscount.php",this.C_Num);
        myTask.execute();


        Button backhome=(Button)findViewById(R.id.backhome);
        backhome.setOnClickListener((View)->{
            Intent intent1 = new Intent(cardsinfodetail.this, mainpage.class);
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("User",this.a1);
            intent1.putExtras(bundle);
            startActivity(intent1);
            finish();
        });




    }
    public class MyTask extends AsyncTask<Void,Void,Void> {
        Context c=null;
        String url1;
        String C_Num;
        public MyTask(Context c ,String url,String C_Num){
            this.c=c;
            this.url1=url;
            this.C_Num=C_Num;
        };

        @Override
        protected Void doInBackground(Void... voids) {
            String eatingScore="";
            String movieScore="";
            String insuranceScore="";
            String publicScore="";
            String shoppingScore="";
            String firstSwipeScore="";
            String smartpayScore="";
            String mileageScore="";
            String otherScore="";

            TableLayout table1=(TableLayout)findViewById(R.id.detailtable1);
            LinearLayout Linear1=(LinearLayout)findViewById(R.id.detailLinear1);
            TableLayout table2=(TableLayout)findViewById(R.id.detailtable2);
            LinearLayout Linear2=(LinearLayout)findViewById(R.id.detailLinear2);
            TableLayout table3=(TableLayout)findViewById(R.id.detailtable3);
            LinearLayout Linear3=(LinearLayout)findViewById(R.id.detailLinear3);
            TableLayout table4=(TableLayout)findViewById(R.id.detailtable4);
            LinearLayout Linear4=(LinearLayout)findViewById(R.id.detailLinear4);
            TableLayout table5=(TableLayout)findViewById(R.id.detailtable5);
            LinearLayout Linear5=(LinearLayout)findViewById(R.id.detailLinear5);
            TableLayout table6=(TableLayout)findViewById(R.id.detailtable6);
            LinearLayout Linear6=(LinearLayout)findViewById(R.id.detailLinear6);
            TableLayout table7=(TableLayout)findViewById(R.id.detailtable7);
            LinearLayout Linear7=(LinearLayout)findViewById(R.id.detailLinear7);
            TableLayout table8=(TableLayout)findViewById(R.id.detailtable8);
            LinearLayout Linear8=(LinearLayout)findViewById(R.id.detailLinear8);
            TableLayout table9=(TableLayout)findViewById(R.id.detailtable9);
            LinearLayout Linear9=(LinearLayout)findViewById(R.id.detailLinear9);

            TextView detailtitle1=(TextView)findViewById(R.id.detailtitle1);
            TextView detailtitle2=(TextView)findViewById(R.id.detailtitle2);
            TextView detailtitle3=(TextView)findViewById(R.id.detailtitle3);
            TextView detailtitle4=(TextView)findViewById(R.id.detailtitle4);
            TextView detailtitle5=(TextView)findViewById(R.id.detailtitle5);
            TextView detailtitle6=(TextView)findViewById(R.id.detailtitle6);
            TextView detailtitle7=(TextView)findViewById(R.id.detailtitle7);
            TextView detailtitle8=(TextView)findViewById(R.id.detailtitle8);
            TextView detailtitle9=(TextView)findViewById(R.id.detailtitle9);

            TextView detailcontent1=(TextView) findViewById(R.id.detailcontent1);
            TextView detailcontent2=(TextView) findViewById(R.id.detailcontent2);
            TextView detailcontent3=(TextView) findViewById(R.id.detailcontent3);
            TextView detailcontent4=(TextView) findViewById(R.id.detailcontent4);
            TextView detailcontent5=(TextView) findViewById(R.id.detailcontent5);
            TextView detailcontent6=(TextView) findViewById(R.id.detailcontent6);
            TextView detailcontent7=(TextView) findViewById(R.id.detailcontent7);
            TextView detailcontent8=(TextView) findViewById(R.id.detailcontent8);
            TextView detailcontent9=(TextView) findViewById(R.id.detailcontent9);

            ImageButton detailimage1=(ImageButton)findViewById(R.id.detailimage1);
            ImageButton detailimage2=(ImageButton)findViewById(R.id.detailimage2);
            ImageButton detailimage3=(ImageButton)findViewById(R.id.detailimage3);
            ImageButton detailimage4=(ImageButton)findViewById(R.id.detailimage4);
            ImageButton detailimage5=(ImageButton)findViewById(R.id.detailimage5);
            ImageButton detailimage6=(ImageButton)findViewById(R.id.detailimage6);
            ImageButton detailimage7=(ImageButton)findViewById(R.id.detailimage7);
            ImageButton detailimage8=(ImageButton)findViewById(R.id.detailimage8);
            ImageButton detailimage9=(ImageButton)findViewById(R.id.detailimage9);
            ImageButton detailimage10=(ImageButton)findViewById(R.id.detailimage10);


            ArrayList<String> title=new ArrayList<>();
            int count=0;
            try{
                URL url2 = new URL("http://www.cardori.com.tw/resources/img/creditCards/innerPage/large/" + this.C_Num + ".png");
                System.out.println("TEST URL=\t" + "http://www.cardori.com.tw/resources/img/creditCards/innerPage/large/" + this.C_Num + ".png");
                HttpURLConnection conn1 = (HttpURLConnection) url2.openConnection();
                conn1.setDoInput(true);
                conn1.connect();
                InputStream input = conn1.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                runOnUiThread(()->{
                    ImageView cardimg=(ImageView)findViewById(R.id.Detailcardimage);
                    cardimg.setImageBitmap(bitmap);
                });
                String sData="";
                URL url=new URL(url1);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                sData+= URLEncoder.encode("C_Num", "UTF-8")+"="+URLEncoder.encode(this.C_Num, "UTF-8");
                String finalSData = sData;
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( finalSData );
                wr.flush();
                // Get the server response
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    sb.append(line + "\n");
                }
                String rlt=sb.toString();
                rlt=rlt.trim();
                String rlt1[]=rlt.split(";");
                System.out.println("rlt=\t"+rlt);
                if(rlt.equals("false")){

                }else{
                    for (int i=0 ; i<rlt1.length ; i++){
                        String []sort=rlt1[i].split(",",2);
//                        System.out.println("test sort[0]=\t"+sort[0]);
                        if(sort[0].equals("eatingScore")){
                            String lab="▣";
                            eatingScore+=lab+" "+sort[1].replace(","," : ")+"\n";

//                            System.out.println("test eatingScore=\t"+eatingScore);
                        }
                        if(sort[0].equals("movieScore")){
                            String lab="▣";
                            movieScore+=lab+" "+sort[1].replace(","," : ")+"\n";
//                            lab++;
                        }
                        if(sort[0].equals("insuranceScore")){
                            String lab="▣";
                            insuranceScore+=lab+" "+sort[1].replace(","," : ")+"\n";
//                            lab++;
                        }
                        if(sort[0].equals("publicScore")){
                            String lab="▣";
                            publicScore+=lab+" "+sort[1].replace(","," : ")+"\n";
//                            lab++;
                        }
                        if(sort[0].equals("shoppingScore")){
                            String lab="▣";
                            shoppingScore+=lab+" "+sort[1].replace(","," : ")+"\n";
//                            lab++;
                        }
                        if(sort[0].equals("firstSwipeScore")){
                            String lab="▣";
                            firstSwipeScore+=lab+" "+sort[1].replace(","," : ")+"\n";
//                            lab++;
                        }
                        if(sort[0].equals("smartpayScore")){
                            String lab="▣";
                            smartpayScore+=lab+" "+sort[1].replace(","," : ")+"\n";
//                            lab++;
                        }
                        if(sort[0].equals("mileageScore")){
                            String lab="▣";
                            mileageScore+=lab+" "+sort[1].replace(","," : ")+"\n";
//                            lab++;
                        }
                        if(sort[0].equals("otherScore")){
                            String lab="▣";
                            otherScore+=lab+" "+sort[1].replace(","," : ")+"\n";
//                            lab++;
                        }

                    }
                    if (!eatingScore.equals("")){
                        count++;
                        title.add("餐飲優惠;"+eatingScore);
                    }
                    if (!movieScore.equals("")){
                        count++;
                        title.add("電影優惠;"+movieScore);
                    }
                    if (!insuranceScore.equals("")){
                        count++;
                        title.add("旅遊相關優惠;"+insuranceScore);
                    }
                    if (!publicScore.equals("")){
                        count++;
                        title.add("大眾運輸優惠;"+publicScore);
                    }
                    if (!shoppingScore.equals("")){
                        count++;
                        title.add("購物優惠;"+shoppingScore);
                    }
                    if (!firstSwipeScore.equals("")){
                        count++;
                        title.add("首刷優惠;"+firstSwipeScore);
                    }
                    if (!smartpayScore.equals("")){
                        count++;
                        title.add("感應消費優惠;"+smartpayScore);
                    }
                    if (!mileageScore.equals("")){
                        count++;
                        title.add("機場相關優惠;"+mileageScore);

                    }
                    if (!otherScore.equals("")){
                        count++;
                        title.add("其他優惠;"+otherScore);
                    }
                    System.out.println("Count=\t"+count);
                    int finalCount = count;
                    runOnUiThread(()->{
                        switch (finalCount){
                            case 1:{
                                table1.setVisibility(View.VISIBLE);Linear1.setVisibility(View.VISIBLE);
                                table2.setVisibility(View.GONE);Linear2.setVisibility(View.GONE);
                                table3.setVisibility(View.GONE);Linear3.setVisibility(View.GONE);
                                table4.setVisibility(View.GONE);Linear4.setVisibility(View.GONE);
                                table5.setVisibility(View.GONE);Linear5.setVisibility(View.GONE);
                                table6.setVisibility(View.GONE);Linear6.setVisibility(View.GONE);
                                table7.setVisibility(View.GONE);Linear7.setVisibility(View.GONE);
                                table8.setVisibility(View.GONE);Linear8.setVisibility(View.GONE);
                                table9.setVisibility(View.GONE);Linear9.setVisibility(View.GONE);
                                String []str1=title.get(0).split(";");
                                detailtitle1.setText(str1[0]);
                                detailcontent1.setText(str1[1]);

                            }
                            break;
                            case 2:{
                                table1.setVisibility(View.VISIBLE);Linear1.setVisibility(View.VISIBLE);
                                table2.setVisibility(View.VISIBLE);Linear2.setVisibility(View.VISIBLE);
                                table3.setVisibility(View.GONE);Linear3.setVisibility(View.GONE);
                                table4.setVisibility(View.GONE);Linear4.setVisibility(View.GONE);
                                table5.setVisibility(View.GONE);Linear5.setVisibility(View.GONE);
                                table6.setVisibility(View.GONE);Linear6.setVisibility(View.GONE);
                                table7.setVisibility(View.GONE);Linear7.setVisibility(View.GONE);
                                table8.setVisibility(View.GONE);Linear8.setVisibility(View.GONE);
                                table9.setVisibility(View.GONE);Linear9.setVisibility(View.GONE);
                                String []str1=title.get(0).split(";");
                                String []str2=title.get(1).split(";");
                                detailtitle1.setText(str1[0]);
                                detailtitle2.setText(str2[0]);
                                detailcontent1.setText(str1[1]);
                                detailcontent2.setText(str2[1]);
                            }
                            break;
                            case 3:{
                                table1.setVisibility(View.VISIBLE);Linear1.setVisibility(View.VISIBLE);
                                table2.setVisibility(View.VISIBLE);Linear2.setVisibility(View.VISIBLE);
                                table3.setVisibility(View.VISIBLE);Linear3.setVisibility(View.VISIBLE);
                                table4.setVisibility(View.GONE);Linear4.setVisibility(View.GONE);
                                table5.setVisibility(View.GONE);Linear5.setVisibility(View.GONE);
                                table6.setVisibility(View.GONE);Linear6.setVisibility(View.GONE);
                                table7.setVisibility(View.GONE);Linear7.setVisibility(View.GONE);
                                table8.setVisibility(View.GONE);Linear8.setVisibility(View.GONE);
                                table9.setVisibility(View.GONE);Linear9.setVisibility(View.GONE);
                                String []str1=title.get(0).split(";");
                                String []str2=title.get(1).split(";");
                                String []str3=title.get(2).split(";");
                                detailtitle1.setText(str1[0]);
                                detailtitle2.setText(str2[0]);
                                detailtitle3.setText(str3[0]);
                                detailcontent1.setText(str1[1]);
                                detailcontent2.setText(str2[1]);
                                detailcontent3.setText(str3[1]);
                            }
                            break;
                            case 4:{
                                table1.setVisibility(View.VISIBLE);Linear1.setVisibility(View.VISIBLE);
                                table2.setVisibility(View.VISIBLE);Linear2.setVisibility(View.VISIBLE);
                                table3.setVisibility(View.VISIBLE);Linear3.setVisibility(View.VISIBLE);
                                table4.setVisibility(View.VISIBLE);Linear4.setVisibility(View.VISIBLE);
                                table5.setVisibility(View.GONE);Linear5.setVisibility(View.GONE);
                                table6.setVisibility(View.GONE);Linear6.setVisibility(View.GONE);
                                table7.setVisibility(View.GONE);Linear7.setVisibility(View.GONE);
                                table8.setVisibility(View.GONE);Linear8.setVisibility(View.GONE);
                                table9.setVisibility(View.GONE);Linear9.setVisibility(View.GONE);
                                String []str1=title.get(0).split(";");
                                String []str2=title.get(1).split(";");
                                String []str3=title.get(2).split(";");
                                String []str4=title.get(3).split(";");
                                detailtitle1.setText(str1[0]);
                                detailtitle2.setText(str2[0]);
                                detailtitle3.setText(str3[0]);
                                detailtitle4.setText(str4[0]);
                                detailcontent1.setText(str1[1]);
                                detailcontent2.setText(str2[1]);
                                detailcontent3.setText(str3[1]);
                                detailcontent4.setText(str4[1]);


                            }
                            break;
                            case 5:{
                                table1.setVisibility(View.VISIBLE);Linear1.setVisibility(View.VISIBLE);
                                table2.setVisibility(View.VISIBLE);Linear2.setVisibility(View.VISIBLE);
                                table3.setVisibility(View.VISIBLE);Linear3.setVisibility(View.VISIBLE);
                                table4.setVisibility(View.VISIBLE);Linear4.setVisibility(View.VISIBLE);
                                table5.setVisibility(View.VISIBLE);Linear5.setVisibility(View.VISIBLE);
                                table6.setVisibility(View.GONE);Linear6.setVisibility(View.GONE);
                                table7.setVisibility(View.GONE);Linear7.setVisibility(View.GONE);
                                table8.setVisibility(View.GONE);Linear8.setVisibility(View.GONE);
                                table9.setVisibility(View.GONE);Linear9.setVisibility(View.GONE);
                                String []str1=title.get(0).split(";");
                                String []str2=title.get(1).split(";");
                                String []str3=title.get(2).split(";");
                                String []str4=title.get(3).split(";");
                                String []str5=title.get(4).split(";");
                                detailtitle1.setText(str1[0]);
                                detailtitle2.setText(str2[0]);
                                detailtitle3.setText(str3[0]);
                                detailtitle4.setText(str4[0]);
                                detailtitle5.setText(str5[0]);
                                detailcontent1.setText(str1[1]);
                                detailcontent2.setText(str2[1]);
                                detailcontent3.setText(str3[1]);
                                detailcontent4.setText(str4[1]);
                                detailcontent5.setText(str5[1]);
                            }
                            break;
                            case 6:{
                                table1.setVisibility(View.VISIBLE);Linear1.setVisibility(View.VISIBLE);
                                table2.setVisibility(View.VISIBLE);Linear2.setVisibility(View.VISIBLE);
                                table3.setVisibility(View.VISIBLE);Linear3.setVisibility(View.VISIBLE);
                                table4.setVisibility(View.VISIBLE);Linear4.setVisibility(View.VISIBLE);
                                table5.setVisibility(View.VISIBLE);Linear5.setVisibility(View.VISIBLE);
                                table6.setVisibility(View.VISIBLE);Linear6.setVisibility(View.VISIBLE);
                                table7.setVisibility(View.GONE);Linear7.setVisibility(View.GONE);
                                table8.setVisibility(View.GONE);Linear8.setVisibility(View.GONE);
                                table9.setVisibility(View.GONE);Linear9.setVisibility(View.GONE);
                                String []str1=title.get(0).split(";");
                                String []str2=title.get(1).split(";");
                                String []str3=title.get(2).split(";");
                                String []str4=title.get(3).split(";");
                                String []str5=title.get(4).split(";");
                                String []str6=title.get(5).split(";");
                                detailtitle1.setText(str1[0]);
                                detailtitle2.setText(str2[0]);
                                detailtitle3.setText(str3[0]);
                                detailtitle4.setText(str4[0]);
                                detailtitle5.setText(str5[0]);
                                detailtitle6.setText(str6[0]);
                                detailcontent1.setText(str1[1]);
                                detailcontent2.setText(str2[1]);
                                detailcontent3.setText(str3[1]);
                                detailcontent4.setText(str4[1]);
                                detailcontent5.setText(str5[1]);
                                detailcontent6.setText(str6[1]);
                            }
                            break;
                            case 7:{
                                table1.setVisibility(View.VISIBLE);Linear1.setVisibility(View.VISIBLE);
                                table2.setVisibility(View.VISIBLE);Linear2.setVisibility(View.VISIBLE);
                                table3.setVisibility(View.VISIBLE);Linear3.setVisibility(View.VISIBLE);
                                table4.setVisibility(View.VISIBLE);Linear4.setVisibility(View.VISIBLE);
                                table5.setVisibility(View.VISIBLE);Linear5.setVisibility(View.VISIBLE);
                                table6.setVisibility(View.VISIBLE);Linear6.setVisibility(View.VISIBLE);
                                table7.setVisibility(View.VISIBLE);Linear7.setVisibility(View.VISIBLE);
                                table8.setVisibility(View.GONE);Linear8.setVisibility(View.GONE);
                                table9.setVisibility(View.GONE);Linear9.setVisibility(View.GONE);
                                String []str1=title.get(0).split(";");
                                String []str2=title.get(1).split(";");
                                String []str3=title.get(2).split(";");
                                String []str4=title.get(3).split(";");
                                String []str5=title.get(4).split(";");
                                String []str6=title.get(5).split(";");
                                String []str7=title.get(6).split(";");
                                detailtitle1.setText(str1[0]);
                                detailtitle2.setText(str2[0]);
                                detailtitle3.setText(str3[0]);
                                detailtitle4.setText(str4[0]);
                                detailtitle5.setText(str5[0]);
                                detailtitle6.setText(str6[0]);
                                detailtitle7.setText(str7[0]);
                                detailcontent1.setText(str1[1]);
                                detailcontent2.setText(str2[1]);
                                detailcontent3.setText(str3[1]);
                                detailcontent4.setText(str4[1]);
                                detailcontent5.setText(str5[1]);
                                detailcontent6.setText(str6[1]);
                                detailcontent7.setText(str7[1]);
                            }
                            break;
                            case 8:{
                                table1.setVisibility(View.VISIBLE);Linear1.setVisibility(View.VISIBLE);
                                table2.setVisibility(View.VISIBLE);Linear2.setVisibility(View.VISIBLE);
                                table3.setVisibility(View.VISIBLE);Linear3.setVisibility(View.VISIBLE);
                                table4.setVisibility(View.VISIBLE);Linear4.setVisibility(View.VISIBLE);
                                table5.setVisibility(View.VISIBLE);Linear5.setVisibility(View.VISIBLE);
                                table6.setVisibility(View.VISIBLE);Linear6.setVisibility(View.VISIBLE);
                                table7.setVisibility(View.VISIBLE);Linear7.setVisibility(View.VISIBLE);
                                table8.setVisibility(View.VISIBLE);Linear8.setVisibility(View.VISIBLE);
                                table9.setVisibility(View.GONE);Linear9.setVisibility(View.GONE);
                                String []str1=title.get(0).split(";");
                                String []str2=title.get(1).split(";");
                                String []str3=title.get(2).split(";");
                                String []str4=title.get(3).split(";");
                                String []str5=title.get(4).split(";");
                                String []str6=title.get(5).split(";");
                                String []str7=title.get(6).split(";");
                                String []str8=title.get(7).split(";");
                                detailtitle1.setText(str1[0]);
                                detailtitle2.setText(str2[0]);
                                detailtitle3.setText(str3[0]);
                                detailtitle4.setText(str4[0]);
                                detailtitle5.setText(str5[0]);
                                detailtitle6.setText(str6[0]);
                                detailtitle7.setText(str7[0]);
                                detailtitle8.setText(str8[0]);
                                detailcontent1.setText(str1[1]);
                                detailcontent2.setText(str2[1]);
                                detailcontent3.setText(str3[1]);
                                detailcontent4.setText(str4[1]);
                                detailcontent5.setText(str5[1]);
                                detailcontent6.setText(str6[1]);
                                detailcontent7.setText(str7[1]);
                                detailcontent8.setText(str8[1]);
                            }
                            break;
                            case 9:{
                                table1.setVisibility(View.VISIBLE);Linear1.setVisibility(View.VISIBLE);
                                table2.setVisibility(View.VISIBLE);Linear2.setVisibility(View.VISIBLE);
                                table3.setVisibility(View.VISIBLE);Linear3.setVisibility(View.VISIBLE);
                                table4.setVisibility(View.VISIBLE);Linear4.setVisibility(View.VISIBLE);
                                table5.setVisibility(View.VISIBLE);Linear5.setVisibility(View.VISIBLE);
                                table6.setVisibility(View.VISIBLE);Linear6.setVisibility(View.VISIBLE);
                                table7.setVisibility(View.VISIBLE);Linear7.setVisibility(View.VISIBLE);
                                table8.setVisibility(View.VISIBLE);Linear8.setVisibility(View.VISIBLE);
                                table9.setVisibility(View.VISIBLE);Linear9.setVisibility(View.VISIBLE);
                                String []str1=title.get(0).split(";");
                                String []str2=title.get(1).split(";");
                                String []str3=title.get(2).split(";");
                                String []str4=title.get(3).split(";");
                                String []str5=title.get(4).split(";");
                                String []str6=title.get(5).split(";");
                                String []str7=title.get(6).split(";");
                                String []str8=title.get(7).split(";");
                                String []str9=title.get(8).split(";");
                                detailtitle1.setText(str1[0]);
                                detailtitle2.setText(str2[0]);
                                detailtitle3.setText(str3[0]);
                                detailtitle4.setText(str4[0]);
                                detailtitle5.setText(str5[0]);
                                detailtitle6.setText(str6[0]);
                                detailtitle7.setText(str7[0]);
                                detailtitle8.setText(str8[0]);
                                detailtitle9.setText(str9[0]);
                                detailcontent1.setText(str1[1]);
                                detailcontent2.setText(str2[1]);
                                detailcontent3.setText(str3[1]);
                                detailcontent4.setText(str4[1]);
                                detailcontent5.setText(str5[1]);
                                detailcontent6.setText(str6[1]);
                                detailcontent7.setText(str7[1]);
                                detailcontent8.setText(str8[1]);
                                detailcontent9.setText(str9[1]);
                            }
                            break;
                        }
                        detailimage1.setOnClickListener((View)->{
                            detailcontent1.setVisibility(View.VISIBLE);
                            detailcontent2.setVisibility(View.GONE);
                            detailcontent3.setVisibility(View.GONE);
                            detailcontent4.setVisibility(View.GONE);
                            detailcontent5.setVisibility(View.GONE);
                            detailcontent6.setVisibility(View.GONE);
                            detailcontent7.setVisibility(View.GONE);
                            detailcontent8.setVisibility(View.GONE);
                            detailcontent9.setVisibility(View.GONE);
                        });
                        detailimage2.setOnClickListener((View)->{
                            detailcontent1.setVisibility(View.GONE);
                            detailcontent2.setVisibility(View.VISIBLE);
                            detailcontent3.setVisibility(View.GONE);
                            detailcontent4.setVisibility(View.GONE);
                            detailcontent5.setVisibility(View.GONE);
                            detailcontent6.setVisibility(View.GONE);
                            detailcontent7.setVisibility(View.GONE);
                            detailcontent8.setVisibility(View.GONE);
                            detailcontent9.setVisibility(View.GONE);
                        });
                        detailimage3.setOnClickListener((View)->{
                            detailcontent1.setVisibility(View.GONE);
                            detailcontent2.setVisibility(View.GONE);
                            detailcontent3.setVisibility(View.VISIBLE);
                            detailcontent4.setVisibility(View.GONE);
                            detailcontent5.setVisibility(View.GONE);
                            detailcontent6.setVisibility(View.GONE);
                            detailcontent7.setVisibility(View.GONE);
                            detailcontent8.setVisibility(View.GONE);
                            detailcontent9.setVisibility(View.GONE);
                        });
                        detailimage4.setOnClickListener((View)->{
                            detailcontent1.setVisibility(View.GONE);
                            detailcontent2.setVisibility(View.GONE);
                            detailcontent3.setVisibility(View.GONE);
                            detailcontent4.setVisibility(View.VISIBLE);
                            detailcontent5.setVisibility(View.GONE);
                            detailcontent6.setVisibility(View.GONE);
                            detailcontent7.setVisibility(View.GONE);
                            detailcontent8.setVisibility(View.GONE);
                            detailcontent9.setVisibility(View.GONE);
                        });
                        detailimage5.setOnClickListener((View)->{
                            detailcontent1.setVisibility(View.GONE);
                            detailcontent2.setVisibility(View.GONE);
                            detailcontent3.setVisibility(View.GONE);
                            detailcontent4.setVisibility(View.GONE);
                            detailcontent5.setVisibility(View.VISIBLE);
                            detailcontent6.setVisibility(View.GONE);
                            detailcontent7.setVisibility(View.GONE);
                            detailcontent8.setVisibility(View.GONE);
                            detailcontent9.setVisibility(View.GONE);
                        });
                        detailimage6.setOnClickListener((View)->{
                            detailcontent1.setVisibility(View.GONE);
                            detailcontent2.setVisibility(View.GONE);
                            detailcontent3.setVisibility(View.GONE);
                            detailcontent4.setVisibility(View.GONE);
                            detailcontent5.setVisibility(View.GONE);
                            detailcontent6.setVisibility(View.VISIBLE);
                            detailcontent7.setVisibility(View.GONE);
                            detailcontent8.setVisibility(View.GONE);
                            detailcontent9.setVisibility(View.GONE);
                        });
                        detailimage7.setOnClickListener((View)->{
                            detailcontent1.setVisibility(View.GONE);
                            detailcontent2.setVisibility(View.GONE);
                            detailcontent3.setVisibility(View.GONE);
                            detailcontent4.setVisibility(View.GONE);
                            detailcontent5.setVisibility(View.GONE);
                            detailcontent6.setVisibility(View.GONE);
                            detailcontent7.setVisibility(View.VISIBLE);
                            detailcontent8.setVisibility(View.GONE);
                            detailcontent9.setVisibility(View.GONE);
                        });
                        detailimage8.setOnClickListener((View)->{
                            detailcontent1.setVisibility(View.GONE);
                            detailcontent2.setVisibility(View.GONE);
                            detailcontent3.setVisibility(View.GONE);
                            detailcontent4.setVisibility(View.GONE);
                            detailcontent5.setVisibility(View.GONE);
                            detailcontent6.setVisibility(View.GONE);
                            detailcontent7.setVisibility(View.GONE);
                            detailcontent8.setVisibility(View.VISIBLE);
                            detailcontent9.setVisibility(View.GONE);
                        });
                        detailimage9.setOnClickListener((View)->{
                            detailcontent1.setVisibility(View.GONE);
                            detailcontent2.setVisibility(View.GONE);
                            detailcontent3.setVisibility(View.GONE);
                            detailcontent4.setVisibility(View.GONE);
                            detailcontent5.setVisibility(View.GONE);
                            detailcontent6.setVisibility(View.GONE);
                            detailcontent7.setVisibility(View.GONE);
                            detailcontent8.setVisibility(View.GONE);
                            detailcontent9.setVisibility(View.VISIBLE);
                        });

                    });
                }





            }catch (Exception e){

            }



            return null;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
