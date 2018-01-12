package com.example.user.dfinal2;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class manualbill extends Fragment {
    String type;

    boolean bol=true;
    Bundle bundle = new Bundle();
    String U_Name="";
    String U_Num="";
    String BL_Date="";
    public manualbill() {
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
        return inflater.inflate(R.layout.fragment_manualbill, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        TextView t = (TextView) getView().findViewById(R.id.M_date);
        t.setText(date);
        this.BL_Date=date;

        Spinner m_type = (Spinner) getView().findViewById(R.id.m_type);
        String[] types = {"食物", "用品", "學業", "娛樂", "交通", "其他", "收入"};
        String[] items=null;
        ArrayAdapter<String> typeAD = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,types);
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
                    case 6:
                        {items=new String[]{"薪水","生活費","其它"};}
                    break;
                }
                S_items=items;
                Spinner m_item = (Spinner) getView().findViewById(R.id.m_item);
                ArrayAdapter<String> itemList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,S_items);
                m_item.setAdapter(itemList);
            }
        });



        Button Manual=(Button) getView().findViewById(R.id.submit_manu);
        Manual.setOnClickListener((View v)->{
            Spinner m_item = (Spinner) getView().findViewById(R.id.m_item);
            TextView Tprice=(TextView)getView().findViewById(R.id.price_m);
            EditText M_info=(EditText)getView().findViewById(R.id.munu_info) ;
            String type=m_type.getSelectedItem().toString();
            String item= m_item.getSelectedItem().toString();
            String price_m=Tprice.getText().toString();
            try {
                post("http://140.119.163.195/DFapp/InsertBill.php",this.U_Num,this.BL_Date,type+":"+item,price_m,M_info.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ImageButton m_cal=(ImageButton)getView().findViewById(R.id.m_calandar);
        m_cal.setOnClickListener((View)->{
            TableRow tableRow=(TableRow)getView().findViewById(R.id.Calander);
            tableRow.setVisibility(View.VISIBLE);
            TableLayout content=(TableLayout)getView().findViewById(R.id.manu_content);
            content.setVisibility(View.GONE);
            TableLayout but=(TableLayout)getView().findViewById(R.id.manu_button);
            but.setVisibility(View.GONE);
        });
//        android.view.View
        Button chose_c=(Button)getView().findViewById(R.id.chose_c);
        chose_c.setOnClickListener((View View) ->{
            TableRow tableRow=(TableRow)getView().findViewById(R.id.Calander);
            tableRow.setVisibility(View.GONE);
            TableLayout content=(TableLayout)getView().findViewById(R.id.manu_content);
            content.setVisibility(View.VISIBLE);
            TableLayout but=(TableLayout)getView().findViewById(R.id.manu_button);
            but.setVisibility(View.VISIBLE);
            CalendarView calendar=(CalendarView)getView().findViewById(R.id.calendarView);

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
                    getActivity().runOnUiThread(() -> {
                        Toast toast = Toast.makeText(getActivity(), "插入失敗！！\t" + this.U_Name, Toast.LENGTH_LONG);
                        toast.show();
                    });
                }else{
                    getActivity().runOnUiThread(() -> {
                        Toast toast = Toast.makeText(getActivity(),
                                "成功！！\t" + this.U_Name, Toast.LENGTH_LONG);
                        toast.show();
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
