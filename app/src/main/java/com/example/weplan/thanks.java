package com.example.weplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class thanks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);
    }

    public void startusing(View view) {
        Intent intent = new Intent(thanks.this, DashboardActivity.class);
        startActivity(intent);
    }
}
