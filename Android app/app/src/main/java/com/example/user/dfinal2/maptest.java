package com.example.user.dfinal2;


import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
public class maptest extends Fragment implements OnMapReadyCallback {
    GoogleMap googleMap;
    LocationManager locationManager;
//    String lattitude, longitude;
    View myview;
    MapView mapView;

    Bundle bundle = new Bundle();
    String U_Name = "";
    String U_Num = "";
    String BL_Date = "";

    public maptest() {
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

        myview=inflater.inflate(R.layout.fragment_maptest, container, false);
        return inflater.inflate(R.layout.fragment_maptest, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        mapView = (MapView) getView().findViewById(R.id.mapView2);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
//        String str=getLocation();
//        System.out.println("Test=\t"+str);

        MyTask myTask=new MyTask(getActivity(),"http://140.119.163.195/DFapp/cardinfo.php",U_Num);
        myTask.execute();
        
        Handler handler=new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                //要做的事情
                try {
                    MyTask myTask=new MyTask(getActivity(),"http://140.119.163.195/DFapp/cardinfo.php",U_Num);
                    myTask.execute();
                }catch (Exception e){

                }

                handler.postDelayed(this, 360000);
            }
        };
        handler.postDelayed(runnable, 360000);

    }
    @Override
    public void onMapReady(GoogleMap googleMap1) {
        MapsInitializer.initialize(getContext());

        this.googleMap = googleMap1;
        String current=getLocation();
        String []dc=current.split(";");
        LatLng sydney = new LatLng(Double.valueOf(25.033203), Double.valueOf(121.563229));
//        googleMap.addMarker(new MarkerOptions().position(sydney).title("101捷運站"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

    }
    private String getLocation() {
        String longitude="";
        String lattitude="";
        try {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            } else {
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

                if (location != null) {
                    double latti = location.getLatitude();
                    double longi = location.getLongitude();
                    lattitude = String.valueOf(latti);
                    longitude = String.valueOf(longi);

                    System.out.println("Your current location is"+ "\n" + "Lattitude = " + lattitude
                            + "\n" + "Longitude = " + longitude);


                } else  if (location1 != null) {
                    double latti = location1.getLatitude();
                    double longi = location1.getLongitude();
                    lattitude = String.valueOf(latti);
                    longitude = String.valueOf(longi);

                    System.out.println("Your current location is"+ "\n" + "Lattitude = " + lattitude
                            + "\n" + "Longitude = " + longitude);


                } else  if (location2 != null) {
                    double latti = location2.getLatitude();
                    double longi = location2.getLongitude();
                    lattitude = String.valueOf(latti);
                    longitude = String.valueOf(longi);

                    System.out.println("Your current location is"+ "\n" + "Lattitude = " + lattitude
                            + "\n" + "Longitude = " + longitude);

                }else{

                }
            }
        }catch (Exception e){

        }

        return lattitude+";"+longitude;
    }
    public class MyTask extends AsyncTask<Void,Void,Void> {
        Context c;
        String Url;
//        String Data;
        MyTask(Context c,String url,String username){
            this.c=c;
            this.Url=url;
//            this.Data=m;

        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String latti,longi;
                latti=getLocation().split(";")[0];
                longi=getLocation().split(";")[1];
//                ExecutorService pool = Executors.newFixedThreadPool(70);
//                pool.execute(() -> {
                    try {
                        URL url = new URL(this.Url);
                        URLConnection conn = url.openConnection();
                        conn.setDoOutput(true);
                        String sData="";
                    sData+= URLEncoder.encode("latti", "UTF-8")+"="+URLEncoder.encode("25.033203", "UTF-8")+"&";
                    sData+= URLEncoder.encode("longi", "UTF-8")+"="+URLEncoder.encode("121.563229", "UTF-8")+"&";
//                        sData+= URLEncoder.encode("latti", "UTF-8")+"="+URLEncoder.encode(latti, "UTF-8")+"&";
//                        sData+= URLEncoder.encode("longi", "UTF-8")+"="+URLEncoder.encode(longi, "UTF-8")+"&";
                        sData+= URLEncoder.encode("U_Num", "UTF-8")+"="+URLEncoder.encode(U_Num, "UTF-8");
                        System.out.println("SData=\t"+sData);
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
                        System.out.println("Locate=\t"+rlt);
                        reader.close();
                        if(rlt.equals("")){
                            try {
                                getActivity().runOnUiThread(() -> {
                                    ListView listview=(ListView)getView().findViewById(R.id.Listview3);
                                    String [] str={"目前這裡沒有優惠"};
                                    ArrayAdapter <String> adapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,str);
                                    listview.setAdapter(adapter);
                                });

                            }catch (Exception e){

                            }

                        }else{

                            Map<String ,String> gomap=new HashMap<>();
                            ArrayList<Bitmap> lab=new ArrayList<>();
                            ArrayList<String> lab2=new ArrayList<>();
                            ArrayList<String> items=new ArrayList<>();
                            for (int i=0; i<rlt1.length ; i++){
                                String [] splitdata=rlt1[i].split("@");
                                String [] splitdata_G=splitdata[0].split(":");
                                try {
                                    gomap.put(splitdata_G[0],splitdata_G[1]);
                                }catch (Exception e){

                                }

    //                       -----------------------------------------google
                                String [] splitdata_I=splitdata[1].split(":");
                                try {
                                    URL urlimg = new URL("http://www.cardori.com.tw/resources/img/creditCards/innerPage/large/" + splitdata_I[0] + ".png");
                                    System.out.println("TEST URL=\t" + "http://www.cardori.com.tw/resources/img/creditCards/innerPage/large/" + splitdata_I[0] + ".png");
                                    HttpURLConnection conn1 = (HttpURLConnection) urlimg.openConnection();
                                    conn1.setDoInput(true);
                                    Thread t = new Thread(() -> {
                                        try {
                                            conn1.connect();
                                            InputStream input = conn1.getInputStream();
                                            Bitmap bitmap = BitmapFactory.decodeStream(input);
                                            try {
                                                getActivity().runOnUiThread(() -> {
                                                    try{
                                                        String []local=splitdata_G[1].split(",");
                                                        LatLng latLng= new LatLng(Double.valueOf(local[0]),Double.valueOf(local[1]));
                                                       googleMap.addMarker(new MarkerOptions().position(latLng).title(splitdata_G[0]));

                                                        lab.add(bitmap);
                                                        items.add(splitdata_G[0]);
                                                        lab2.add(splitdata_I[1]);
                                                        ListView listview=(ListView)getView().findViewById(R.id.Listview3);
                                                        Recommandlist recommandlist=new Recommandlist(getActivity(),lab,lab2,items);
                                                        listview.setAdapter(recommandlist);
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
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }catch (Exception e){

            }



            return null;
        }
    }

}
