package com.example.weplan.Reviews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weplan.Classes.FirebaseHelper;
import com.example.weplan.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class GoogleReviews extends AppCompatActivity {
    private static final String TAG = "Ashir ALI";
    PlacesClient placesClient;
    String placeId = "asdfaI";



    FirebaseHelper firebaseHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        placeId=bundle.getString("placeid");
        Places.initialize(getApplicationContext(),"AIzaSyCEKQJPGzRjzE58jJzWnoOFStg3j-QDKck");
        placesClient = Places.createClient(this);
        setContentView(R.layout.activity_google_reviews);
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse response) {
                Place place = response.getPlace();
                Toast.makeText(GoogleReviews.this, "here is"+place.getName(), Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    int statusCode = apiException.getStatusCode();
                    // Handle error with given status code.
                    Log.e(TAG, "Place not found: " + exception.getMessage());
                }
            }
        });
    }
}
