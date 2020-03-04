package com.example.weplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void viewfeedback(View view) {
        Intent intent = new Intent(DashboardActivity.this, ViewFeedback.class);
        startActivity(intent);
    }

    public void backbut(View view) {
        this.finish();
    }

    public void startChat(View view) {

        Intent intent=new Intent(this,ChatMainActivity.class);
        startActivity(intent);
    }


    public void searchfun(View view) {

        Intent intent=new Intent(this,requirement.class);
        startActivity(intent);
    }
}
