package com.example.weplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;

import com.applozic.mobicomkit.broadcast.BroadcastService;
import com.example.weplan.Adapters.ServiceAdapter;
import com.example.weplan.Classes.FirebaseHelper;
import com.example.weplan.Classes.Services;
import com.example.weplan.Classes.servicelist;
import com.example.weplan.Fragments.MyServiceslistRecyclerViewAdapter;

import java.util.ArrayList;

public class ServicesList extends AppCompatActivity {

    FirebaseHelper helper;
    ListView serviceListq;
    ArrayList<Services> mylisttemp;

    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);

        sharedpreferences = getSharedPreferences("ServiceInfo", Context.MODE_PRIVATE );
        editor = sharedpreferences.edit();

        Intent intent=getIntent();
        String name=intent.getStringExtra("ServiceName");

        editor.putString("ServiceName", name); // Storing string

        editor.commit();


        serviceListq=findViewById(R.id.serviceList);

        helper = new FirebaseHelper();
        helper.getSingleServiceList(new FirebaseHelper.Callback() {




            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onSuccess(ArrayList<Services> arrayList) {
                mylisttemp=arrayList;

            }

        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ServiceAdapter customAdapter=new ServiceAdapter(getApplicationContext(),0,mylisttemp);
                serviceListq.setAdapter(customAdapter);
            }
        }, 5000);



    }
}
