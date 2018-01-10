package com.example.user.dfinal2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
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

public class NewChartDtail extends AppCompatActivity {
    Bundle bundle = new Bundle();
    String U_Name="";
    String U_Num="";
    String BL_Year="";
    String BL_Mon="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chart_dtail);

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
            if (str.contains("BL_Year")){
                this.BL_Year=items[1];
            }
            if (str.contains("BL_Mon")){
                this.BL_Mon=items[1];
            }
            map.put(items[0],items[1]);
        }
        Map<String,String>map1=new HashMap<>();
        map1.put("U_Num",U_Num);
        map1.put("BL_Year",this.BL_Year);
        map1.put("BL_Mon",this.BL_Mon);
        TextView year=(TextView)findViewById(R.id.detail_year1);
        year.setText(this.BL_Year+" 年");
        TextView mom=(TextView)findViewById(R.id.detail_mom1);
        mom.setText(this.BL_Mon+" 月");
        MyTask myTask=new MyTask(NewChartDtail.this,map,"http://140.119.163.195/DFapp/FindOfBillData.php");
        myTask.execute();
    }

    public class MyTask extends AsyncTask<Void,Void,Void> {
        Context c=null;
        Map<String,String> Data;
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
                            Toast toast = Toast.makeText(NewChartDtail.this,
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
                        ArrayList<String>lab=new ArrayList<>();
                        ArrayList<String>lab2=new ArrayList<>();
                        ArrayList<String>item=new ArrayList<>();
                        ArrayList<String>info=new ArrayList<>();
                        for(int i=0;i<rlt1.length;i++){

                            if(i%3 == 0){
                                System.out.println("rlt=\t"+rlt1[i]);
                                String []s=rlt1[i].split(":");
                                if(s[0].equals("食物")){
                                    food+=Integer.valueOf(rlt1[i+1]);
                                    lab.add("食物");
                                    lab2.add(s[1]);
                                    item.add(rlt1[i+1]);
                                    info.add(rlt1[i+2]);
                                }
                                if(s[0].equals("用品")){
                                    supplies+=Integer.valueOf(rlt1[i+1]);
                                    lab.add("用品");
                                    lab2.add(s[1]);
                                    item.add(rlt1[i+1]);
                                    info.add(rlt1[i+2]);
                                }
                                if(s[0].equals("學業")){
                                    study+=Integer.valueOf(rlt1[i+1]);
                                    lab.add("學業");
                                    lab2.add(s[1]);
                                    item.add(rlt1[i+1]);
                                    info.add(rlt1[i+2]);
                                }
                                if(s[0].equals("娛樂")){
                                    enteriment+=Integer.valueOf(rlt1[i+1]);
                                    lab.add("娛樂");
                                    lab2.add(s[1]);
                                    item.add(rlt1[i+1]);
                                    info.add(rlt1[i+2]);
                                }
                                if(s[0].equals("交通")){
                                    traffic+=Integer.valueOf(rlt1[i+1]);
                                    lab.add("交通");
                                    lab2.add(s[1]);
                                    item.add(rlt1[i+1]);
                                    info.add(rlt1[i+2]);
                                }
                                if(s[0].equals("其他")){
                                    other+=Integer.valueOf(rlt1[i+1]);
                                    lab.add("其他");
                                    lab2.add(s[1]);
                                    item.add(rlt1[i+1]);
                                    info.add(rlt1[i+2]);
                                }
                                if(s[0].equals("收入")){
                                    income+=Integer.valueOf(rlt1[i+1]);
                                    lab.add("收入");
                                    lab2.add(s[1]);
                                    item.add(rlt1[i+1]);
                                    info.add(rlt1[i+2]);
                                }
                            }
                        }
                        float total=food+supplies+study+enteriment+traffic+other+income;
                        List<PieEntry> list=new ArrayList<>();
                        ArrayList <String> containofitem=new ArrayList<>();
                        if(food!=0.0){
                            list.add(new PieEntry( food/total, "食物"));
                            containofitem.add("食物");
                        }
                        if(supplies!=0.0){
                            list.add(new PieEntry(supplies/total, "用品"));
                            containofitem.add("用品");
                        }
                        if(study!=0.0){
                            list.add(new PieEntry(study/total, "學業"));
                            containofitem.add("學業");
                        }
                        if(enteriment!=0.0){
                            list.add(new PieEntry(enteriment/total, "娛樂"));
                            containofitem.add("娛樂");
                        }
                        if(traffic!=0.0){
                            list.add(new PieEntry(traffic/total, "交通"));
                            containofitem.add("交通");
                        }
                        if(other!=0.0){
                            list.add(new PieEntry(other/total, "其他"));
                            containofitem.add("其他");
                        }
                        if(income!=0.0){
                            list.add(new PieEntry(income/total, "收入"));
                            containofitem.add("收入");
                        }

                        ArrayList<String> containofitemaddcolor=new ArrayList<>();
                        for (int h=0;h<containofitem.size();h++){
                            containofitemaddcolor.add(containofitem.get(h)+":"+ColorTemplate.COLORFUL_COLORS[h]);
                        }
                        runOnUiThread(()->{
                            PieDataSet dataSet=new PieDataSet(list,"本月花費狀況");
                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            dataSet.setValueTextSize(16);
                            PieData pieData=new PieData(dataSet);
                            PieChart pieChart=(PieChart)findViewById(R.id.chart3);
                            pieChart.setData(pieData);
                            pieChart.invalidate();


                            ListView listview=(ListView)findViewById(R.id.list_report1);
                            CustomList customList=new CustomList(NewChartDtail.this,lab,lab2,item,containofitemaddcolor,info);
                            listview.setAdapter(customList);
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

//            }

            return null;
        }
    }
}
