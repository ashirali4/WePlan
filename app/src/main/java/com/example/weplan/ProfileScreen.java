package com.example.weplan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.developer.kalert.KAlertDialog;
import com.example.weplan.Reviews.Facebook;
import com.example.weplan.Reviews.GoogleReviews;
import com.squareup.picasso.Picasso;

public class ProfileScreen extends AppCompatActivity {


    TextView textName,textType,textPrice,textLocation;
    RatingBar ratingbar;
    ImageView image;
    KAlertDialog pDialog;
    private Handler mHandler;
    String service_name,location,budget,imageLink,placeid,id,fb,token;
    Float ratingfloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        pDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#4E67FD"));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mHandler=new Handler();
        if (bundle.getString("name").equals(null)) {
            service_name="Name here";

        }
        else {
            id=bundle.getString("id");
            service_name=bundle.getString("name");
            location = bundle.getString("location");
            ratingfloat = bundle.getFloat("rating");
            budget = bundle.getString("budget");
            imageLink = bundle.getString("imageLink");
            placeid=bundle.getString("placeid");
            fb=bundle.getString("fb");
            token=bundle.getString("token");
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

            mHandler.postDelayed(new Runnable() {
                public void run() {

                    pDialog.hide();
                }
            }, 500);
        }


    }

    public void googlepage(View view) {
        Bundle bundle=new Bundle();
        bundle.putString("placeid",placeid);
        bundle.putString("placename",service_name);
        Intent intent=new Intent(ProfileScreen.this, GoogleReviews.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startchatmessage(View view) {

                Intent intent = new Intent(this, ConversationActivity.class);
                intent.putExtra(ConversationUIService.USER_ID, id);
                intent.putExtra(ConversationUIService.DISPLAY_NAME, service_name); //put it for displaying the title.
                intent.putExtra(ConversationUIService.TAKE_ORDER,true); //Skip chat list for showing on back press
                startActivity(intent);
    }

    public void fbmessage(View view) {
        Bundle bundle=new Bundle();
        bundle.putString("fb",fb);
        bundle.putString("token",token);
        bundle.putString("name",service_name);
        Intent intent=new Intent(ProfileScreen.this, Facebook.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
