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

import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;
import com.example.weplan.Classes.HttpHandler;
import com.example.weplan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Facebook extends AppCompatActivity {
    TextView ratingcount,rating,placename;
    String ratingcounts,ratings,placenames;
    JSONObject feedback;
    String ashir[]=new String[15];
    ListView listView;
    KAlertDialog pDialog;
    private static final String TAG = "Ashir ALI";
String token,fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        feedback = new JSONObject();
        ratingcount = findViewById(R.id.fbrating);
        rating = findViewById(R.id.fbratingcount);
        placename = findViewById(R.id.fbname);
        Intent intent = getIntent();
        getSupportActionBar().hide();
        Bundle bundle = intent.getExtras();
        fb=bundle.getString("fb");
        token=bundle.getString("token");
        placenames=bundle.getString("name");
        fb="106821374127944";
        token="EAAGWt4lBREUBAPE0ZAh2YZB1EJkZAfiqZAYLXfqmlxZB0SKRhz2JnNpJZB5SaxZAqNIxOXvRqaZC7ACR4RXN0MOu8FVx1mwh2FfLCdZC3i6d02JltyyTqD0BwgZBcJIYucdCyb0qIvTZCwXyvKZA1LZBJTkWJq6bAprRxA1fcVh17mHvCfiMsDl7Gl9fsCcjeUIAkpoMWkIZAnC2SVO9jFNDZALWSNk";
        String url="https://graph.facebook.com/"+fb+"?fields=rating_count,overall_star_rating,name,ratings&access_token="+token;

        new Facebook.GetContacts().execute(url);
                for(int i=0;i<15;i++)
        {
            ashir[i]="------------";
        }
        listView=findViewById(R.id.fblist);
        pDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#4E67FD"));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();

    }
        private class GetContacts extends AsyncTask<String, Void, Void> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(Facebook.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

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
                        ratings=jsonObj.getString("overall_star_rating");
                        ratingcounts=jsonObj.getString("rating_count");
                        // Getting JSON Array node
                        JSONObject result = jsonObj.getJSONObject("ratings");
                        JSONArray contacts = result.getJSONArray("data");
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);
                            String id = c.getString("review_text");


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
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(Facebook.this, android.R.layout.simple_list_item_1, ashir);
                listView.setAdapter(arrayAdapter);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rating.setText(ratings);
                        placename.setText(placenames);
                        ratingcount.setText(ratingcounts);
                        pDialog.hide();

                    }
                }, 4000);
            }
        }

}
