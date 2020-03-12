package com.example.weplan.Reviews;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;
import com.example.weplan.Classes.FirebaseHelper;
import com.example.weplan.Classes.HttpHandler;
import com.example.weplan.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GoogleReviews extends AppCompatActivity {
    private static final String TAG = "Ashir ALI";
    PlacesClient placesClient;
    String placeId = "asdfaI";
    TextView ratingcount,rating,placename;
    String ratingcounts,ratings,placenames;
    Integer count;
    Double rat;
    String Apikey;
    String url;
    ListView listView;
    String jsonstring;
    KAlertDialog pDialog;
    String ashir[]=new String[5];
    FirebaseHelper firebaseHelper;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_reviews);
        ratingcount=findViewById(R.id.ratincountlayout);
        rating=findViewById(R.id.ratinglayout);
        placename=findViewById(R.id.placenamelayout);

        pDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#4E67FD"));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();
        Intent intent = getIntent();
        getSupportActionBar().hide();
        Bundle bundle = intent.getExtras();
        placeId=bundle.getString("placeid");
        placenames=bundle.getString("placename");
        Apikey="AIzaSyBDZJKicpvy9Q3yP7IKzflvsH9Y3WA_Np8";
        Places.initialize(getApplicationContext(),"AIzaSyBDZJKicpvy9Q3yP7IKzflvsH9Y3WA_Np8");
        placesClient = Places.createClient(this);
       url="https://maps.googleapis.com/maps/api/place/details/json?placeid="+placeId+"&fields=reviews&key=AIzaSyBDZJKicpvy9Q3yP7IKzflvsH9Y3WA_Np8";
        listView=findViewById(R.id.commentlist);

        //the array adapter to load data into list
     List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.RATING,Place.Field.USER_RATINGS_TOTAL);
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
        placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
            @Override
            public void onSuccess(FetchPlaceResponse response) {
                Place place = response.getPlace();
                count=place.getUserRatingsTotal();

                rat=place.getRating();

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
        new GetContacts().execute(url);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ratingcounts=count.toString();
                ratings=rat.toString();
                rating.setText(ratings);
                placename.setText(placenames);
                ratingcount.setText(ratingcounts);
                pDialog.hide();

            }
        }, 4000);


    }




    private class GetContacts extends AsyncTask<String, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(GoogleReviews.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(String... params) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response

            String url = params[0];

            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject result = jsonObj.getJSONObject("result");
                    JSONArray contacts = result.getJSONArray("reviews");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String id = c.getString("text");


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("comment", id);
                        ashir[i]=id;

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
             ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(GoogleReviews.this, android.R.layout.simple_list_item_1, ashir);
            listView.setAdapter(arrayAdapter);


        }
    }
}











