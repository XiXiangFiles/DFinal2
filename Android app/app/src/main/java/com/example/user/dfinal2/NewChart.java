package com.example.user.dfinal2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewChart extends AppCompatActivity {
    Bundle bundle = new Bundle();
    String U_Name="";
    String U_Num="";
    ArrayList<String> a1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chart);
        Intent intent = this.getIntent();
        ArrayList<String> a=intent.getStringArrayListExtra("User");
        this.bundle.putStringArrayList("User",a);
        this.a1=a;
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

        Spinner B_year = (Spinner)findViewById(R.id.ch_y1);
        String[] years = {"2017", "2018", "2019", "2020", "2021", "2022", "2023"};
        ArrayAdapter<String> yearsA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,years);
        B_year.setAdapter(yearsA);


        Spinner B_m = (Spinner)findViewById(R.id.ch_m1);
        String[] mons = {"1", "2", "3", "4", "5", "6", "7","8","9","10","11","12"};
        ArrayAdapter<String> monA = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,mons);
        B_m.setAdapter(monA);

        Spinner year = (Spinner) findViewById(R.id.ch_y1);
        Spinner mon = (Spinner)findViewById(R.id.ch_m1);
        year.setSelection(1);
//        mon.setSelection(1);

        year.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                Spinner mon1 = (Spinner)findViewById(R.id.ch_m1);
                Map<String,String>map=new HashMap<>();
                map.put("U_Num",U_Num);
                map.put("BL_Year",years[position]);
                map.put("BL_Mon",mon1.getSelectedItem().toString());
                MyTask myTask=new MyTask(NewChart.this,map,"http://140.119.163.195/DFapp/FindOfBillData.php");
                myTask.execute();
            }
        });
        mon.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                Spinner year2 = (Spinner)findViewById(R.id.ch_y1);
                Map<String,String>map=new HashMap<>();
                map.put("U_Num",U_Num);
                map.put("BL_Year",year2.getSelectedItem().toString());
                map.put("BL_Mon",mons[position]);
                MyTask myTask=new MyTask(NewChart.this,map,"http://140.119.163.195/DFapp/FindOfBillData.php");
                myTask.execute();
            }
        });
        Button BC_submit=(Button)findViewById(R.id.BC_submit1);
        BC_submit.setOnClickListener((View)->{
            Spinner year3 = (Spinner)findViewById(R.id.ch_y1);
            Spinner mon2 = (Spinner) findViewById(R.id.ch_m1);
            this.a1.add("BL_Year:"+year3.getSelectedItem().toString());
            this.a1.add("BL_Mon:"+mon2.getSelectedItem().toString());
            Bundle bundle1 = new Bundle();
            bundle1.putStringArrayList("User",a1);
            Intent intent1 = new Intent(this,NewChartDtail.class);
            intent1.putExtras(bundle);
            startActivity(intent1);
//            FragmentManager FMSec=getFragmentManager();
//            FragmentTransaction FMSecFT=FMSec.beginTransaction();
//            System.out.println("Click!!");
//            chartdetail chD=new chartdetail();
//            chD.setArguments(bundle1);
//            FMSecFT.replace(R.id.f_container,chD);
//            FMSecFT.commit();
        });

    }
    public class MyTask extends AsyncTask<Void,Void,Void> {
        Context c=null;
        Map<String,String>Data;
        String url1="";
        public MyTask(Context c, Map<String ,String> map, String url){
            this.c=c;
            this.Data=map;
            this.url1=url;
        };

        @Override
        protected Void doInBackground(Void... voids) {
            String U_Num="";
            String BL_Year="";
            String BL_Mon="";
            for(Map.Entry<String ,String > m:Data.entrySet()){
                if(m.getKey().equals("U_Num")){
                    U_Num=m.getValue();
                }
                if(m.getKey().equals("BL_Year")){
                    BL_Year=m.getValue();
                }
                if(m.getKey().equals("BL_Mon")){
                    BL_Mon=m.getValue();
                }
            }
//        synchronized (this){

            String sData="";
            try {
                URL url=new URL(url1);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                System.out.println("U_Num=\t"+U_Num);
                System.out.println("BL_Year=\t"+BL_Year);
                System.out.println("BL_Mon=\t"+BL_Mon);
                sData+= URLEncoder.encode("U_Num", "UTF-8")+"="+URLEncoder.encode(U_Num, "UTF-8")+"&";
                sData+= URLEncoder.encode("BL_Year", "UTF-8")+"="+URLEncoder.encode(BL_Year, "UTF-8")+"&";
                sData+= URLEncoder.encode("BL_Mon", "UTF-8")+"="+URLEncoder.encode(BL_Mon, "UTF-8");
                try {
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write( sData );
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
                    System.out.println("Test=\t"+sb.toString());




                    if(rlt.equals("false")){
                        runOnUiThread(()->{
                            Toast toast = Toast.makeText(NewChart.this,
                                    "無資料！！\t" , Toast.LENGTH_LONG);
                            toast.show();
                        });
                    }else{
                        float food=0;
                        float supplies=0;
                        float study=0;
                        float enteriment=0;
                        float traffic=0;
                        float other=0;
                        float income=0;
                        Map<String,String> map=new HashMap<>();
                        for(int i=0;i<rlt1.length;i++){

                            if(i%3 == 0){
                                System.out.println("rlt=\t"+rlt1[i]);
                                String []s=rlt1[i].split(":");
                                if(s[0].equals("食物")){
                                    food+=Integer.valueOf(rlt1[i+1]);
//                                    System.out.println("測試食物＝\t"+food);
                                }
                                if(s[0].equals("用品")){
                                    supplies+=Integer.valueOf(rlt1[i+1]);
//                                    System.out.println("測試用品＝\t"+supplies);
                                }
                                if(s[0].equals("學業")){
                                    study+=Integer.valueOf(rlt1[i+1]);
                                }
                                if(s[0].equals("娛樂")){
                                    enteriment+=Integer.valueOf(rlt1[i+1]);
                                }
                                if(s[0].equals("交通")){
                                    traffic+=Integer.valueOf(rlt1[i+1]);
                                }
                                if(s[0].equals("其他")){
                                    other+=Integer.valueOf(rlt1[i+1]);
                                }
                                if(s[0].equals("收入")){
                                    income+=Integer.valueOf(rlt1[i+1]);
                                }
                            }
                        }

                        float total=food+supplies+study+enteriment+traffic+other+income;
                        List<PieEntry> list=new ArrayList<>();
                        if(food!=0.0)
                            list.add(new PieEntry( food/total, "食物"));
                        if(supplies!=0.0)
                            list.add(new PieEntry(supplies/total, "用品"));
                        if(study!=0.0)
                            list.add(new PieEntry(study/total, "學業"));
                        if(enteriment!=0.0)
                            list.add(new PieEntry(enteriment/total, "娛樂"));
                        if(traffic!=0.0)
                            list.add(new PieEntry(traffic/total, "交通"));
                        if(other!=0.0)
                            list.add(new PieEntry(other/total, "其他"));
                        if(income!=0.0)
                            list.add(new PieEntry(income/total, "收入"));

                        System.out.println("Total=\t"+total);
                        runOnUiThread(()->{
                            PieDataSet dataSet=new PieDataSet(list,"本月花費狀況");
                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            dataSet.setValueTextSize(16);
                            PieData pieData=new PieData(dataSet);
                            PieChart pieChart=(PieChart)findViewById(R.id.chart1);
                            pieChart.setData(pieData);
                            pieChart.invalidate();
                        });
                    }

                    reader.close();
                }
                catch(Exception ex)
                {   }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
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
