package com.example.weplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class requirement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement);
    }

    public void searchforreq(View view) {

        Intent intent=new Intent(this,Search.class);
        startActivity(intent);
    }
}
