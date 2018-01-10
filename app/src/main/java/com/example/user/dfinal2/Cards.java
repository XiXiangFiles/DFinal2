package com.example.user.dfinal2;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cards extends Fragment {
//    Bundle bundle = new Bundle();
    String U_Name="";
    String U_Num="";
    String BL_Date="";
    ArrayList <String>User=new ArrayList<>();

    public Cards() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();
        ArrayList<String> a=bundle.getStringArrayList("User");
        this.User=a;
//        this.bundle.putStringArrayList("User",a);
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
        return inflater.inflate(R.layout.fragment_cards, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
//        http://140.119.163.195/DFapp/findUserCard.php
        try {
            MyTask myTask=new MyTask(getActivity(),"http://140.119.163.195/DFapp/findUserCard.php",this.U_Num);
            myTask.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyTask extends AsyncTask<Void,Void,Void> {
        Context c=null;
        String url1;
        String U_Num;
        public MyTask(Context c ,String url,String U_Num){
            this.c=c;
            this.url1=url;
            this.U_Num=U_Num;
        };

        @Override
        protected Void doInBackground(Void... voids) {
            String sData="";
                try
                {
                    URL url=new URL(url1);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    sData+= URLEncoder.encode("U_Num", "UTF-8")+"="+URLEncoder.encode(U_Num, "UTF-8");
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
                        ArrayList <Bitmap>lab1=new ArrayList<>();
                        ArrayList <String> lab2=new ArrayList<>();
                        ArrayList <String> cardsnum=new ArrayList<>();
                        for (int i=0 ; i<rlt1.length ; i++){
                            String []data=rlt1[i].split(",");
                            try {
                                URL url2 = new URL("http://www.cardori.com.tw/resources/img/creditCards/innerPage/large/" + data[0] + ".png");
                                System.out.println("TEST URL=\t" + "http://www.cardori.com.tw/resources/img/creditCards/innerPage/large/" + data[0] + ".png");
                                HttpURLConnection conn1 = (HttpURLConnection) url2.openConnection();
                                conn1.setDoInput(true);
                            Thread t = new Thread(() -> {
                                try {
                                    conn1.connect();
                                    InputStream input = conn1.getInputStream();
                                    Bitmap bitmap = BitmapFactory.decodeStream(input);
//                                    ArrayList <Bitmap>lab1=new ArrayList<>();
//                                    ArrayList <String> lab2=new ArrayList<>();
//                                    ArrayList <String> cardsnum=new ArrayList<>();
                                    lab1.add(bitmap);
                                    lab2.add(data[1]);
                                    cardsnum.add(data[0]);
                                    try {
                                        getActivity().runOnUiThread(() -> {
                                            System.out.println("data[1]="+data[1]);
                                            try {
                                                ListView listview=(ListView) getView().findViewById(R.id.ListviewCards);
                                                cardslistview customList=new cardslistview(getActivity(),lab1,lab2);
                                                listview.setAdapter(customList);
                                                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                                        User.add("C_Num:"+cardsnum.get(i));
                                                        User.add("C_Name:"+lab2.get(i));
                                                        Bundle bundle = new Bundle();
                                                        bundle.putStringArrayList("User",User);
                                                        Intent intent = new Intent(getActivity(), cardsinfodetail.class);
                                                        intent.putExtras(bundle);
                                                        startActivity(intent);

                                                    }
                                                });
                                            }catch (Exception e){

                                            }

                                        });
                                    }catch (Exception e){

                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            t.start();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    reader.close();
                }
                catch(Exception ex)
                {   }

            return null;
        }
    }

}
