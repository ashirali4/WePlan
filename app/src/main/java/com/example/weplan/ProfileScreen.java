package com.example.weplan;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.developer.kalert.KAlertDialog;
import com.example.weplan.Reviews.Facebook;
import com.example.weplan.Reviews.GoogleReviews;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class ProfileScreen extends AppCompatActivity {

    PlacesClient placesClient;
    TextView textName, textType, textPrice, textLocation, phonenum;
    RatingBar ratingbar;
    ImageView image;
    KAlertDialog pDialog;
    private Handler mHandler;
    String service_name, location, budget, imageLink, placeid, id, fb, token, phone,placeaddress;
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
        Places.initialize(getApplicationContext(),"AIzaSyBDZJKicpvy9Q3yP7IKzflvsH9Y3WA_Np8");
        placesClient = Places.createClient(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mHandler = new Handler();
        if (bundle.getString("name").equals(null)) {
            service_name = "Name here";

        } else {
            id = bundle.getString("id");
            service_name = bundle.getString("name");
            location = bundle.getString("location");
            ratingfloat = bundle.getFloat("rating");
            budget = bundle.getString("budget");
            imageLink = bundle.getString("imageLink");
            placeid = bundle.getString("placeid");
            fb = bundle.getString("fb");
            phone = bundle.getString("phone", "+923006568113");
            token = bundle.getString("token");
            textName = findViewById(R.id.name);
            textType = findViewById(R.id.serviceType);
            textPrice = findViewById(R.id.price);
            textLocation = findViewById(R.id.location);
            image = findViewById(R.id.profileimage);
            ratingbar = findViewById(R.id.ratingstar);
            phonenum = findViewById(R.id.phoneprofile);


            Picasso.get().load(imageLink).into(image);
            textName.setText(service_name);
            textType.setText("Event Organizers");
            textPrice.setText(budget);
            textLocation.setText(location);
            ratingbar.setRating(ratingfloat);
            phonenum.setText(phone);
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    fetchaddress();
                    pDialog.hide();
                }
            }, 500);
        }


    }

    public void googlepage(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("placeid", placeid);
        bundle.putString("placename", service_name);
        Intent intent = new Intent(ProfileScreen.this, GoogleReviews.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startchatmessage(View view) {

        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra(ConversationUIService.USER_ID, id);
        intent.putExtra(ConversationUIService.DISPLAY_NAME, service_name); //put it for displaying the title.
        intent.putExtra(ConversationUIService.TAKE_ORDER, true); //Skip chat list for showing on back press
        startActivity(intent);
    }

    public void fbmessage(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("fb", fb);
        bundle.putString("token", token);
        bundle.putString("name", service_name);
        Intent intent = new Intent(ProfileScreen.this, Facebook.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void callus(View view) {
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("google.navigation:q=Stone+Ove+Pizza+Faisalabad"));
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        try {
            startActivity(i);
        } catch (Exception e) {
            // this can happen if the device can't make phone calls
            // for example, a tablet
        }

    }

    public void showonmap(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
              Uri.parse("google.navigation:q="+placeaddress));
        try {
            startActivity(intent);
        } catch (Exception e) {
            // this can happen if the device can't make phone calls
            // for example, a tablet
        }
    }
    public void fetchaddress(){
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.ADDRESS);
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeid, placeFields);
        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse response) {
                Place place = response.getPlace();
                placeaddress=place.getAddress();
                Toast.makeText(ProfileScreen.this, ""+place.getAddress(), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    int statusCode = apiException.getStatusCode();
                    // Handle error with given status code.

                }
            }
        });

    }
}
