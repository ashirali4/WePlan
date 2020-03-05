package com.example.weplan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.weplan.Fragments.Accounts;
import com.example.weplan.Fragments.Home_Dashboard_Featured;
import com.example.weplan.Fragments.Location;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity implements Accounts.OnFragmentInteractionListener,Location.OnFragmentInteractionListener,Home_Dashboard_Featured.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Home_Dashboard_Featured()).commit();
        }
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
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new Home_Dashboard_Featured();
                            break;
                        case R.id.nav_favorites:
                            selectedFragment = new Accounts();
                            break;
                        case R.id.nav_search:
                            selectedFragment = new Location();
                            break;}

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
