package com.example.weplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileScreen extends AppCompatActivity {


    TextView textName,textType,textPrice,textLocation;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String name=bundle.getString("name");
        String location=bundle.getString("location");
        String rating=bundle.getString("rating");
        String budget=bundle.getString("budget");
        Bitmap bitimage=bundle.getParcelable("image");

        textName=findViewById(R.id.name);
        textType=findViewById(R.id.serviceType);
        textPrice=findViewById(R.id.price);
        textLocation=findViewById(R.id.location);
        image=findViewById(R.id.profileimage);

        textName.setText(name);


    }
}
