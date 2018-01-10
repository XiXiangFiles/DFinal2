package com.example.user.dfinal2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//                finish();
                Map<String,String>map=new HashMap<>();
                try{
                    DBsqlite dBsqlite= new DBsqlite(MainActivity.this);
                    ArrayList a=dBsqlite.getAll(dBsqlite,map);
                    if(a.size()>0){
                        String ID_keep="";
                        String PWD_keep="";
                        String Keep="";
                        String str=a.toArray()[0].toString();
                        System.out.println("str=\t"+str);
                        String []str1=str.split(",");

                        for(int i=0;i<str1.length;i++){
                            String str3=str1[i];
                            if (str3.contains("Email")){
                                String []str4=str3.split("=");
                                ID_keep=str4[1];
                                System.out.println("ID_keep=\t"+ID_keep);
                            }
                            if (str3.contains("PWD")){
                                String []str4=str3.split("=");
                                PWD_keep=str4[1];
                                System.out.println("PWD=\t"+PWD_keep);
                            }
                            if (str3.contains("Keep")){
                                String []str4=str3.split("=");
                                Keep=str4[1];
                                System.out.println("Keep=\t"+str4[1]);
                            }
                        }
                        if(Keep.equals("yes")){
                            Map<String, String> x=new HashMap<>();
                            x.put("ID",ID_keep);
                            x.put("PWD",PWD_keep);
                            String url="http://140.119.163.195/DFapp/login.php";
                            try {
                                post(url,x);
                            } catch (Exception e) {
                                e.printStackTrace();
                                System.out.print("Response!!!"+e);
                            }
                        }

                    }else{
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }catch (Exception e){
                    System.out.println("Error=\t"+e);
                }


            }
        };

        timer.schedule(task, 2500);

    }
    private String post(String url1,Map<String,String> data) throws Exception{
        String sData="";
        String ID="";
        String PWD="";
        URL url=new URL(url1);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        for(Map.Entry<String,String > m :data.entrySet()){
            sData+= URLEncoder.encode(m.getKey(), "UTF-8")+"="+URLEncoder.encode(m.getValue(), "UTF-8")+"&";
            if(m.getKey().equals("ID")){
                ID=m.getValue();
            }else {
                PWD=m.getValue();
            }

        }
        String finalSData = sData;
        ExecutorService pool = Executors.newFixedThreadPool(10);
        String finalPWD = PWD;
        String finalID = ID;
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
                rlt=rlt.trim();
                String rlt1[]=rlt.split(";");
                System.out.println("rlt=\t"+rlt);
                if(rlt.equals("false")){
                    runOnUiThread(()-> {

//                        Toast.makeText(MainActivity.this,"帳號密碼錯誤",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                         startActivity(intent);

                    });

                }else{
                    String U_Name = rlt1[0];
                    Integer U_Num = Integer.valueOf(rlt1[1]);
                    ArrayList<String> a=new ArrayList<>();
                    a.add("U_Name:"+rlt1[0]);
                    a.add("U_Num:"+rlt1[1]);
                    a.add("Email:"+ finalID);
                    a.add("PWD:"+ finalPWD);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("User",a);
//                    CheckBox checkBox=(CheckBox) findViewById(R.id.Logincheck);

//                    if(checkBox.isChecked()){
//                        System.out.println("CLick Ckeckbox");
//                        System.out.println("rlt1[2]=\t"+rlt1[2]);
//                        if(rlt1[2].equals("nocard")){
//                            runOnUiThread(()-> {
//                                Toast toast = Toast.makeText(MainActivity.this,
//                                        "歡迎回來！！\t"+ U_Name, Toast.LENGTH_LONG);
//                                toast.show();
//                                a.add("Keep:"+"yes");
//                                Intent intent = new Intent(MainActivity.this, select_bank.class);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                this.finish();
//                            });
//                        }else{
//                            runOnUiThread(()-> {
//                                Toast toast = Toast.makeText(MainActivity.this,
//                                        "歡迎回來！！\t"+ U_Name, Toast.LENGTH_LONG);
//                                toast.show();
//                                a.add("Keep:"+"yes");
//                                Intent intent = new Intent(MainActivity.this, mainpage.class);
//                                intent.putExtras(bundle);
//                                startActivity(intent);
//                                this.finish();
//                            });
//                        }
//                    }else {

                        if(rlt1[2].equals("nocard")){
                            runOnUiThread(()-> {
                                Toast toast = Toast.makeText(MainActivity.this,
                                        "歡迎回來！！\t"+ U_Name, Toast.LENGTH_LONG);
                                toast.show();
                                a.add("Keep:"+"no");
                                Intent intent = new Intent(MainActivity.this, select_bank.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                this.finish();
                            });
                        }else{
                            runOnUiThread(()-> {
                                Toast toast = Toast.makeText(MainActivity.this,
                                        "歡迎回來！！\t"+ U_Name, Toast.LENGTH_LONG);
                                toast.show();
                                a.add("Keep:"+"no");
                                Intent intent = new Intent(MainActivity.this, mainpage.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                this.finish();
                            });
                        }

//                    }

                }
                System.out.println("Test=\t"+sb.toString());
                reader.close();
            }
            catch(Exception ex)
            {   }
        });
        return "ok";
    }
}
