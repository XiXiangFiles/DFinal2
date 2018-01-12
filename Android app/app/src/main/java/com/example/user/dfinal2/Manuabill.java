package com.example.user.dfinal2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manuabill extends AppCompatActivity {


    Bundle bundle = new Bundle();
    String U_Name="";
    String U_Num="";
    String BL_Date="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manuabill);

        Intent intent = this.getIntent();
        ArrayList<String> a=intent.getStringArrayListExtra("User");
        this.bundle.putStringArrayList("User",a);
        Map<String,String> map=new HashMap<>();

        for (String str: a) {
            System.out.println("DATA=\t"+str);
            String []items=str.split(":");
            if (str.contains("U_Name")){
                this.U_Name=items[1];
            }
            if (str.contains("U_Num")){
                this.U_Num=items[1];
            }
            map.put(items[0],items[1]);
        }

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        TextView t = (TextView)findViewById(R.id.M_date1);
        t.setText(date);
        this.BL_Date=date;

        Spinner m_type = (Spinner)findViewById(R.id.m_type1);
        String[] types = {"食物", "用品", "學業", "娛樂", "交通", "其他"};
        String[] items=null;
        ArrayAdapter<String> typeAD = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,types);
        m_type.setAdapter(typeAD);
        m_type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                String[] S_items=null;
                String[] items = new String[0];
                switch (position){
                    case 0:
                    {items=new String[]{"早餐","午餐","晚餐","飲料","其它"};}
                    break;
                    case 1:
                    { items= new String[]{"衣服", "其它"};}
                    break;
                    case 2:
                    { items=new String[]{"書籍","文具","其它"};}
                    break;
                    case 3:
                    { items=new String[]{"旅行","運動","藝文","其它"};}
                    break;
                    case 4:
                    { items=new String[]{"交通",};}
                    break;
                    case 5:
                    { items=new String[]{"其它"};}
                    break;
//                    case 6:
//                    {items=new String[]{"薪水","生活費","其它"};}
//                    break;
                }
                S_items=items;
                Spinner m_item = (Spinner) findViewById(R.id.m_item1);
                ArrayAdapter<String> itemList = new ArrayAdapter<String>(Manuabill.this, android.R.layout.simple_spinner_dropdown_item,S_items);
                m_item.setAdapter(itemList);
            }
        });
        Button Manual=(Button) findViewById(R.id.submit_manu11);
        Manual.setOnClickListener((View v)->{
            Spinner m_item = (Spinner) findViewById(R.id.m_item1);
            TextView Tprice=(TextView)findViewById(R.id.price_m11);
            EditText M_info=(EditText)findViewById(R.id.munu_info1) ;
            String type=m_type.getSelectedItem().toString();
            String item= m_item.getSelectedItem().toString();
            String price_m=Tprice.getText().toString();
            try {
                post("http://140.119.163.195/DFapp/InsertBill.php",this.U_Num,this.BL_Date,type+":"+item,price_m,M_info.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ImageButton m_cal=(ImageButton)findViewById(R.id.m_calandar1);
        m_cal.setOnClickListener((View)->{
            TableRow tableRow=(TableRow)findViewById(R.id.Calander1);
            tableRow.setVisibility(View.VISIBLE);
            TableLayout content=(TableLayout)findViewById(R.id.manu_content1);
            content.setVisibility(View.GONE);
            TableLayout but=(TableLayout)findViewById(R.id.manu_button1);
            but.setVisibility(View.GONE);
        });
//        android.view.View
        Button chose_c=(Button)findViewById(R.id.chose_c1);
        chose_c.setOnClickListener((View View) ->{
            TableRow tableRow=(TableRow)findViewById(R.id.Calander1);
            tableRow.setVisibility(View.GONE);
            TableLayout content=(TableLayout)findViewById(R.id.manu_content1);
            content.setVisibility(View.VISIBLE);
            TableLayout but=(TableLayout)findViewById(R.id.manu_button1);
            but.setVisibility(View.VISIBLE);
        });

        CalendarView calendar=(CalendarView)findViewById(R.id.calendarView1);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {//監聽當日期改變

            public void onSelectedDayChange(CalendarView view,
                                            int year, int month, int dayOfMonth) {
                String date2=year+"-"+(month+1)+"-"+dayOfMonth;
                TextView t2 = (TextView)findViewById(R.id.M_date1);
                t.setText(date2);
                BL_Date=date2;
            }
        });
    }

    private String post(String url1,String U_Num,String BL_Date,String Bi_Item,String Bi_Price,String Bi_Info) throws Exception{
        int count=0;
        String sData="";
        URL url=new URL(url1);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        sData+= URLEncoder.encode("U_Num", "UTF-8")+"="+URLEncoder.encode(U_Num, "UTF-8")+"&";
        sData+= URLEncoder.encode("BL_Date", "UTF-8")+"="+URLEncoder.encode(BL_Date, "UTF-8")+"&";
        sData+= URLEncoder.encode("Bi_Item", "UTF-8")+"="+URLEncoder.encode(Bi_Item, "UTF-8")+"&";
        sData+= URLEncoder.encode("Bi_Price", "UTF-8")+"="+URLEncoder.encode(Bi_Price, "UTF-8")+"&";
        sData+= URLEncoder.encode("Bi_Info", "UTF-8")+"="+URLEncoder.encode(Bi_Info, "UTF-8");
        System.out.println("U_Num=\t"+U_Num);
        System.out.println("BL_Date=\t"+BL_Date);
        System.out.println("Bi_Item=\t"+Bi_Item);
        System.out.println("Bi_Price=\t"+Bi_Price);
        System.out.println("Bi_Info=\t"+Bi_Info);

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
                rlt=rlt.trim();
                String rlt1[]=rlt.split(";");
                System.out.println("Bill=\t"+rlt);
                if(rlt.equals("false")){
                    this.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(this, "插入失敗！！\t" + this.U_Name, Toast.LENGTH_LONG);
                        toast.show();
                    });
                }else{
                    this.runOnUiThread(() -> {
                        Toast toast = Toast.makeText(this,
                                "成功！！\t" + this.U_Name, Toast.LENGTH_LONG);
                        toast.show();
                        TextView Tprice=(TextView)findViewById(R.id.price_m11);
                        EditText M_info=(EditText)findViewById(R.id.munu_info1) ;
                        M_info.setText("");
                        Tprice.setText("0");
                        AlertDialog alertDialog=new AlertDialog.Builder(this)
                                .setTitle("請問還要繼續輸入嗎？")//設定視窗標題
                                .setIcon(getResources().getDrawable(R.drawable.logo2))//設定對話視窗圖示
                                .setNegativeButton("跳出", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent a = new Intent(Manuabill.this,mainpage.class);
                                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        a.putExtras(bundle);
                                        startActivity(a);
                                        finish();
                                    }
                                })
                                .setPositiveButton("繼續記帳",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        onRestart();
                                    }
                                })//設定結束的子視窗
                                .show();//呈現對話視窗

                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(68,114,202));
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(146,180,244));


                    });
                }
                reader.close();
            }
            catch(Exception ex)
            {   }
        });
        return "ok";
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent a = new Intent(this,mainpage.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            a.putExtras(bundle);
            startActivity(a);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
