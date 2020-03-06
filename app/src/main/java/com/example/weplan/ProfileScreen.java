package com.example.weplan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.Rating;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProfileScreen extends AppCompatActivity {


    TextView textName,textType,textPrice,textLocation;
    RatingBar ratingbar;
    ImageView image;

    String service_name,location,budget,imageLink;
    Float ratingfloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle.getString("name").equals(null)) {
            this.finish();
        }
        else {
            service_name=bundle.getString("name");
            location = bundle.getString("location");
            ratingfloat = bundle.getFloat("rating");
            budget = bundle.getString("budget");
            imageLink = bundle.getString("imageLink");

            textName = findViewById(R.id.name);
            textType = findViewById(R.id.serviceType);
            textPrice = findViewById(R.id.price);
            textLocation = findViewById(R.id.location);
            image = findViewById(R.id.profileimage);
            ratingbar = findViewById(R.id.ratingstar);


            Picasso.get().load(imageLink).into(image);
            textName.setText(service_name);
            textType.setText("Service Company");
            textPrice.setText(budget);
            textLocation.setText(location);
            ratingbar.setRating(ratingfloat);
        }


    }
}
