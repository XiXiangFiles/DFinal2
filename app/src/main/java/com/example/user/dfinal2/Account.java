package com.example.user.dfinal2;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.UiThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment {

    Bundle bundle = new Bundle();
    String U_Name="";
    String U_Num="";
    public Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        Bundle bundle = getArguments();
        ArrayList<String> a=bundle.getStringArrayList("User");
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

        return inflater.inflate(R.layout.fragment_account, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        TextView t = (TextView) getView().findViewById(R.id.Today);
        t.setText(date);

        t = (TextView) getView().findViewById(R.id.Today);
        t.setText(date);

        TextView t1 = (TextView) getView().findViewById(R.id.Hello);
        t1.setText(this.U_Name+" 你好！");

        ImageButton Manual=(ImageButton) getView().findViewById(R.id.Manual);

        try {
            post("http://140.119.163.195/DFapp/DayOfRecords.php",U_Num,date);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Manual.setOnClickListener((View v)->{
//            Toast toast = Toast.makeText(getActivity(), "TEST！！\t"+ U_Name, Toast.LENGTH_LONG);
//            toast.show();

            Intent intent = new Intent(getActivity(),Manuabill.class);
            intent.putExtras(bundle);
            startActivity(intent);
//            FragmentManager FMSec=getFragmentManager();
//            FragmentTransaction FMSecFT=FMSec.beginTransaction();
//            System.out.println("Click!!");
//            manualbill mau=new manualbill();
//            mau.setArguments(this.bundle);
//            FMSecFT.replace(R.id.f_container,mau);
//            FMSecFT.commit();
        });
        ImageButton Auto=(ImageButton)getView().findViewById(R.id.auto_bull_input);
        Auto.setOnClickListener((View v)->{
//            Toast toast = Toast.makeText(getActivity(), "TEST！！\t"+ U_Name, Toast.LENGTH_LONG);
//            toast.show();
            Intent intent = new Intent(getActivity(),NewAutobill.class);
            intent.putExtras(bundle);
            startActivity(intent);
//            FragmentManager FMSec=getFragmentManager();
//            FragmentTransaction FMSecFT=FMSec.beginTransaction();
//            System.out.println("Click!!");
//            Atuo_bill auto_B=new Atuo_bill();
//            auto_B.setArguments(this.bundle);
//            FMSecFT.replace(R.id.f_container,auto_B);
//            FMSecFT.commit();
        });
        ImageButton B_chart=(ImageButton)getView().findViewById(R.id.B_chart);
        B_chart.setOnClickListener((View v)->{
//            Toast toast = Toast.makeText(getActivity(), "TEST！！\t"+ U_Name, Toast.LENGTH_LONG);
//            toast.show();
            Intent intent = new Intent(getActivity(),NewChart.class);
            intent.putExtras(bundle);
            startActivity(intent);

        });
        ImageButton showsth=(ImageButton)getView().findViewById(R.id.showsth);
        showsth.setOnClickListener((View)->{
            FragmentManager FMSec=getFragmentManager();
            FragmentTransaction FMSecFT=FMSec.beginTransaction();
            System.out.println("Click!!");
            BlankFragment auto_B=new BlankFragment();
            auto_B.setArguments(this.bundle);
            FMSecFT.replace(R.id.f_container,auto_B);
            FMSecFT.commit();
        });

    }
    private String post(String url1,String U_Num,String BL_Date) throws Exception{
        String sData="";
        URL url=new URL(url1);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        sData+= URLEncoder.encode("U_Num", "UTF-8")+"="+URLEncoder.encode(U_Num, "UTF-8")+"&";
        sData+= URLEncoder.encode("BL_Date", "UTF-8")+"="+URLEncoder.encode(BL_Date, "UTF-8");


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
                System.out.println("rlt=\t"+rlt);
                System.out.println("Test=\t"+sb.toString());
                if(rlt.equals("false")){
                    try{
                        getActivity().runOnUiThread(()->{
                            TextView textView=(TextView)getView().findViewById(R.id.user_Status);
                            textView.setText("你還沒記今天的帳");
                            TextView text=(TextView)getView().findViewById(R.id.Total);
                            text.setText("$ 0");
                        });
                    }catch (Exception e){

                    }


                }else{
                    try {
                            getActivity().runOnUiThread(()->{
                            TextView textView=(TextView)getView().findViewById(R.id.user_Status);
                            textView.setText("YA!!!!\t 你記帳了喔！！");
                            TextView text=(TextView)getView().findViewById(R.id.Total);
                            text.setText("$ "+rlt1[rlt1.length-1]);

                        });
                    }catch (Exception e){

                    }


                }

                reader.close();
            }
            catch(Exception ex)
            {   }
        });
        return "ok";
    }
}
