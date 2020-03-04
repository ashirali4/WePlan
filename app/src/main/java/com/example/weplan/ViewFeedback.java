package com.example.weplan;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewFeedback extends AppCompatActivity {
JSONObject feedback;
public String ratting;
String pageid="106821374127944";
TextView rating,countt,fbname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback);
        feedback=new JSONObject();
       fbname=findViewById(R.id.facebookpage);
                rating=findViewById(R.id.rating);
        countt=findViewById(R.id.ratingcount);

        GraphRequest request = GraphRequest.newGraphPathRequest(
                AccessToken.getCurrentAccessToken(),
                "/106821374127944?fields=overall_star_rating,rating_count,name",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                       feedback=response.getJSONObject();
                        try {
                            String feed=feedback.getString("overall_star_rating");
                            rating.setText(feed);
                            String count=feedback.getString("rating_count");
                            String fname=feedback.getString("name");
                            fbname.setText(fname);
                            countt.setText(count);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


        request.executeAsync();

}}
