package com.example.user.dfinal2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class select_card extends AppCompatActivity {
    String U_Num="";
    String U_Name="";
    String U_Card="";
    String Email="";
    LinearLayout layout;
    CheckBox checkBox;
    ImageView imageView;

    Map<String,Integer> col=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_card);

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
            if(user.getKey().equals("U_Card")){
                this.U_Card=user.getValue();
            }
            if(user.getKey().equals("Email")){
                this.Email=user.getValue();
            }
        }
        System.out.println("U_Card=\t"+U_Card);
        ArrayList<String>arrNum=new ArrayList<>();
        ArrayList<String>arrName=new ArrayList<>();
        String []str=U_Card.split(";");
        for (int i=0;i<str.length-1;i++){
            String []str2=str[i].split(",");
            arrNum.add(str2[0]);
            arrName.add(str2[1]);

        }

        layout=(LinearLayout)findViewById(R.id.inputcard);

        for (int j=0;j<arrName.size();j++)
            try {
                URL url = new URL("http://www.cardori.com.tw/resources/img/creditCards/innerPage/large/" + arrNum.get(j) + ".png");
                System.out.println("TEST URL=\t" + "http://www.cardori.com.tw/resources/img/creditCards/innerPage/large/" + arrNum.get(j) + ".png");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                int finalJX=j+100;
                int finalJ = j;
                Thread t = new Thread(() -> {
                    try {
                        conn.connect();
                        InputStream input = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(input);

                        int finalJ1 = finalJ;
                        runOnUiThread(() -> {
                            checkBox = new CheckBox(this);
                            checkBox.setId(Integer.parseInt(arrNum.get(finalJ1)));
                            checkBox.setText(arrName.get(finalJ));
                            checkBox.setOnClickListener(getOnClickDoSomething(checkBox));
                            imageView = new ImageView(this);
                            imageView.setId(finalJX);
                            imageView.setImageBitmap(bitmap);
                            layout.addView(checkBox);
                            layout.addView(imageView);
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                t.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        Button log = findViewById(R.id.submitcard);
        log.setOnClickListener((View)->{
            String num="";
            for(Map.Entry<String,Integer> serial:col.entrySet()){
                if(serial.getValue()%2 ==1){
                    num=num+serial.getKey()+";";
                }
            }
            if(num.equals("")){
                Toast toast = Toast.makeText(select_card.this,
                        "請選擇卡片！！\t"+ U_Name, Toast.LENGTH_LONG);
                toast.show();
            }else{
                try{
                    post("http://140.119.163.195/DFapp/Insertcard.php",this.U_Num,num);
                }catch (Exception e ){

                }
            }

        });


    }

    View.OnClickListener getOnClickDoSomething(final Button button){
        return new View.OnClickListener() {
            int i=0;
            @Override
            public void onClick(View v) {
                col.put(String.valueOf(button.getId()),++i);
                Log.d("ON_CLICK", "CheckBox ID: " + button.getId() + " Text: " + button.getText().toString());
            }
        };
    }

    private String post(String url1,String U_Num ,String C_Num) throws Exception{
        String sData="";
        System.out.println("U_Num=\t"+U_Num);
        System.out.println("C_Num=\t"+C_Num);

        URL url=new URL(url1);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        sData+= URLEncoder.encode("U_Num", "UTF-8")+"="+URLEncoder.encode(U_Num, "UTF-8")+"&";
        sData+= URLEncoder.encode("C_Num", "UTF-8")+"="+URLEncoder.encode(C_Num, "UTF-8");

        String finalSData = sData;
        ExecutorService pool = Executors.newFixedThreadPool(5);

        pool.execute(()->{
            try
            {
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
                System.out.println("rlt=\t"+rlt);

                String jud=rlt.trim();
                if(jud.contains("true")){
                    ArrayList<String>a=new ArrayList<>();
                    a.add("U_Num:"+this.U_Num);
                    a.add("U_Name:"+this.U_Name);
                    a.add("Email:"+this.Email);

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("User",a);
                    runOnUiThread(()-> {
                        Toast toast = Toast.makeText(select_card.this,
                                "新增成功！！\t"+ U_Name, Toast.LENGTH_LONG);
                        toast.show();

                        Intent intent = new Intent(select_card.this, mainpage.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    });
                }else{
                    ArrayList<String>a=new ArrayList<>();
                    a.add("U_Num:"+this.U_Num);
                    a.add("U_Name:"+this.U_Name);
                    a.add("Email:"+this.Email);

                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("User",a);
                    runOnUiThread(()-> {
                        Toast toast = Toast.makeText(select_card.this,
                                "新增失敗！！\t"+ U_Name, Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(select_card.this, mainpage.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    });
                }


                reader.close();
            }
            catch(Exception ex)
            {   }
        });
        return "ok";
    }


}
