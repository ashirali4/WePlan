package com.example.weplan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class preconnect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preconnect);
    }

    public void connect(View view) {
        Intent intent = new Intent(preconnect.this, connect_social_account.class);
        startActivity(intent);
    }

    public void skipc(View view) {
        Intent intent = new Intent(preconnect.this, thanks.class);
        startActivity(intent);
    }
}
