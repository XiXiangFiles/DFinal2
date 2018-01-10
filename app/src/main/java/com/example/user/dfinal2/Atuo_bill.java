package com.example.user.dfinal2;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

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
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 */
public class Atuo_bill extends Fragment {

    Bundle bundle = new Bundle();
    String U_Name = "";
    String U_Num = "";
    String BL_Date = "";

    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    public Atuo_bill() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        ArrayList<String> a = bundle.getStringArrayList("User");
        this.bundle.putStringArrayList("User", a);
        Map<String, String> map = new HashMap<>();

        for (String str : a) {
            System.out.println("DATA=\t" + str);
            String[] items = str.split(":");
            if (str.contains("U_Name")) {
                this.U_Name = items[1];
            }
            if (str.contains("U_Num")) {
                this.U_Num = items[1];
            }
            map.put(items[0], items[1]);
        }


        return inflater.inflate(R.layout.fragment_atuo_bill, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);


        cameraPreview = (SurfaceView) getView().findViewById(R.id.surfaceView);
        barcodeDetector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(getActivity(), barcodeDetector)
                .setRequestedPreviewSize(4000 , 3000)
                .setAutoFocusEnabled(true)
                .build();
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                try {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {


            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0)
                {
                    Vibrator vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(1000);

                    System.out.println("QR=\t"+qrcodes.valueAt(0).displayValue);
                    String invoice=qrcodes.valueAt(0).displayValue;
                    invoice=invoice.trim();
                    String date_y=invoice.substring(10,13);
                    System.out.println("date_y=\t"+date_y);
                    int x=Integer.valueOf(date_y)+1911;
                    String date=String.valueOf(x)+"-"+invoice.substring(13,15)+"-"+invoice.substring(15,17);

                    String price=invoice.substring(29,37);
                    System.out.println(Integer.valueOf( price, 16 ));
                    int value=Integer.valueOf( price, 16 );
                    price=String.valueOf(value);
                    String finalPrice = price;
                    String []info=invoice.split(":",6);
                    String test="";
                    try{
                        test=info[5].replace(":",",");
                    }catch(Exception e){

                    }

                    if (info[4].equals("0")){
                            test=info[5];
                    }else if(info.equals("2")){
                        test=info[5];
                    }
                    String finalTest = test;
                    getActivity().runOnUiThread(()->{
                        TextView Date=getView().findViewById(R.id.auto_date);
                        TextView Price=getView().findViewById(R.id.auto_price);
                        TextView Info=getView().findViewById(R.id.auto_Binfo);
                        Date.setText(date);
                        Price.setText(finalPrice);
                        Info.setText(finalTest);
                    });
                }
            }

        });



        Spinner m_type = (Spinner) getView().findViewById(R.id.auto_type);
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
                Spinner m_item = (Spinner) getView().findViewById(R.id.auto_item);
                ArrayAdapter<String> itemList = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,S_items);
                m_item.setAdapter(itemList);
            }
        });


        Button bt_submit=(Button)getView().findViewById(R.id.auto_submit);
        bt_submit.setOnClickListener((View )->{
            Spinner m_item = (Spinner) getView().findViewById(R.id.auto_item);
            TextView Tprice=(TextView)getView().findViewById(R.id.auto_price);
            TextView M_info=(TextView)getView().findViewById(R.id.auto_Binfo);
            TextView A_Date=(TextView)getView().findViewById(R.id.auto_date) ;
            String type=m_type.getSelectedItem().toString();
            String item= m_item.getSelectedItem().toString();
            String price_m=Tprice.getText().toString();
            String date=A_Date.getText().toString().trim();
            if(date.equals("")){
                Toast toast = Toast.makeText(getActivity(), "請掃描發票！！\t" + this.U_Name, Toast.LENGTH_LONG);
                toast.show();
            }else{
                try {
                    post("http://140.119.163.195/DFapp/InsertBill.php",this.U_Num,date,type+":"+item,price_m,M_info.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
