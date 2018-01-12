package com.example.user.dfinal2;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class Setting extends Fragment {

    Context context;
    String Email="";
    String U_Name="";
    String U_Num="";
    Bundle bundle = new Bundle();
    public Setting() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        ArrayList<String> a=bundle.getStringArrayList("User");
        Map<String,String> map=new HashMap<>();
        this.bundle.putStringArrayList("User", a);
        for (String str: a) {
            System.out.println("DATA=\t"+str);
            String []items=str.split(":");
            if (str.contains("Email")){
                this.Email=items[1];
            }
            if (str.contains("U_Name")){
                this.U_Name=items[1];
            }
            if (str.contains("U_Num")){
                this.U_Num=items[1];
            }
            map.put(items[0],items[1]);
        }


        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        Button bt=getView().findViewById(R.id.logout);
        bt.setOnClickListener((View v)->{
            Context c;
            DBsqlite db=new DBsqlite(getActivity());
//            db.delete(db,"yes");
//            db.delete(db,"no");
            db.deleteAll();
            Toast toast = Toast.makeText(getActivity(),
                    "登出成功！！", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        Button select=(Button)getView().findViewById(R.id.deleteallcards);
        select.setOnClickListener((View)->{
            try {
                post("http://140.119.163.195/DFapp/deletecards.php",this.U_Num);
            }catch (Exception e){

            }

        });
    }
    private String post(String url1,String U_Num) throws Exception{
        String sData="";
        URL url=new URL(url1);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        sData+= URLEncoder.encode("U_Num", "UTF-8")+"="+URLEncoder.encode(U_Num, "UTF-8");
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
                if(rlt.equals("false")){

                }else{
                    getActivity().runOnUiThread(()->{
                        Intent intent = new Intent(getActivity(), select_bank.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        getActivity().finish();
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
