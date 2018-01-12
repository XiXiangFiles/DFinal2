package com.example.user.dfinal2;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.annotation.Retention;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class select_bank extends AppCompatActivity {


    String U_Num="";
    String U_Name="";
    String keep="";
    String Email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank);

        Intent intent = this.getIntent();
        ArrayList<String>a=intent.getStringArrayListExtra("User");
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

        ArrayList<String>bank=new ArrayList<>();
        Button b=(Button)findViewById(R.id.check);
        b.setOnClickListener((view )->{
            int count=0;
            CheckBox bank1 = (CheckBox)findViewById(R.id.bank1);
            CheckBox bank2 = (CheckBox)findViewById(R.id.bank2);
            CheckBox bank3 = (CheckBox)findViewById(R.id.bank3);
            CheckBox bank4 = (CheckBox)findViewById(R.id.bank4);
            CheckBox bank5 = (CheckBox)findViewById(R.id.bank5);
            CheckBox bank6 = (CheckBox)findViewById(R.id.bank6);
            CheckBox bank7 = (CheckBox)findViewById(R.id.bank7);
            CheckBox bank8 = (CheckBox)findViewById(R.id.bank8);
            CheckBox bank9 = (CheckBox)findViewById(R.id.bank9);
            CheckBox bank10 = (CheckBox)findViewById(R.id.bank10);
            CheckBox bank11 = (CheckBox)findViewById(R.id.bank11);
            CheckBox bank12 = (CheckBox)findViewById(R.id.bank12);
            CheckBox bank13 = (CheckBox)findViewById(R.id.bank13);
            CheckBox bank14 = (CheckBox)findViewById(R.id.bank14);
            CheckBox bank15 = (CheckBox)findViewById(R.id.bank15);
            CheckBox bank16 = (CheckBox)findViewById(R.id.bank16);
            CheckBox bank17 = (CheckBox)findViewById(R.id.bank17);
            CheckBox bank18 = (CheckBox)findViewById(R.id.bank18);
            CheckBox bank19 = (CheckBox)findViewById(R.id.bank19);
            CheckBox bank20 = (CheckBox)findViewById(R.id.bank20);
            CheckBox bank21 = (CheckBox)findViewById(R.id.bank21);
            CheckBox bank22 = (CheckBox)findViewById(R.id.bank22);
            CheckBox bank23= (CheckBox)findViewById(R.id.bank23);
            CheckBox bank24 = (CheckBox)findViewById(R.id.bank24);
            CheckBox bank25 = (CheckBox)findViewById(R.id.bank25);

            if (bank1.isChecked())
            {bank.add(bank1.getText().toString());count++;}
            if (bank2.isChecked())
            {bank.add(bank2.getText().toString());count++;}
            if (bank3.isChecked())
            {bank.add(bank3.getText().toString());count++;}
            if (bank4.isChecked())
            {bank.add(bank4.getText().toString());count++;}
            if (bank5.isChecked())
            { bank.add(bank5.getText().toString());count++;}
            if (bank6.isChecked())
            {bank.add(bank6.getText().toString());count++;}
            if (bank7.isChecked())
            {bank.add(bank7.getText().toString());count++;}
            if (bank8.isChecked())
            {bank.add(bank8.getText().toString());count++;}
            if (bank9.isChecked())
            {bank.add(bank9.getText().toString());count++;}
            if (bank10.isChecked())
            {bank.add(bank10.getText().toString());count++;}
            if (bank11.isChecked())
            {bank.add(bank11.getText().toString());count++;}
            if (bank12.isChecked())
            {bank.add(bank12.getText().toString());count++;}
            if (bank13.isChecked())
            {bank.add(bank13.getText().toString());count++;}
            if (bank14.isChecked())
            {bank.add(bank14.getText().toString());count++;}
            if (bank15.isChecked())
            {bank.add(bank15.getText().toString());count++;}
            if (bank16.isChecked())
            {bank.add(bank16.getText().toString());count++;}
            if (bank17.isChecked())
            {bank.add(bank17.getText().toString());count++;}
            if (bank18.isChecked())
            {bank.add(bank18.getText().toString());count++;}
            if (bank19.isChecked())
            {bank.add(bank19.getText().toString());count++;}
            if (bank20.isChecked())
            {bank.add(bank20.getText().toString());count++;}
            if (bank21.isChecked())
            {bank.add(bank21.getText().toString());count++;}
            if (bank22.isChecked())
            {bank.add(bank22.getText().toString());count++;}
            if (bank23.isChecked())
            {bank.add(bank23.getText().toString());count++;}
            if (bank24.isChecked())
            {bank.add(bank24.getText().toString());count++;}
            if (bank25.isChecked())
            {bank.add(bank25.getText().toString());count++;}

            if(count>0){
                System.out.println(count);
                String B_Name="";
                for(int i=0;i<bank.toArray().length;i++){
                    if(i==bank.toArray().length){
                        B_Name+=bank.toArray()[i];
                    }else{
                        B_Name+=bank.toArray()[i]+";";
                    }

                }
                String url="http://140.119.163.195/DFapp/getcard.php";
//            System.out.println("B_Name"+B_Name);
                try {
                    post(url,B_Name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(select_bank.this,"請選擇銀行！！！",Toast.LENGTH_LONG).show();

            }


        });



    }

//    $U_Num=$_POST['U_Num'];
//    $B_Num=$_POST['B_Num'];
//    $C_Num=$_POST['C_Num'];
    private String post(String url1,String B_Name) throws Exception{
        String sData="";
        System.out.println("B_Name=\t"+B_Name);
        URL url=new URL(url1);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        sData+= URLEncoder.encode("B_Name", "UTF-8")+"="+URLEncoder.encode(B_Name, "UTF-8")+"&";

        String finalSData = sData;
        ExecutorService pool = Executors.newFixedThreadPool(10);

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

                ArrayList<String>a=new ArrayList<>();
                a.add("U_Num:"+this.U_Num);
                a.add("U_Name:"+this.U_Name);
                a.add("U_Card:"+ rlt);
                a.add("Email:"+ this.Email);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("User",a);
                runOnUiThread(()-> {
                    Intent intent = new Intent(select_bank.this, select_card.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                });



                reader.close();
            }
            catch(Exception ex)
            {   }
        });
        return "ok";
    }

}
