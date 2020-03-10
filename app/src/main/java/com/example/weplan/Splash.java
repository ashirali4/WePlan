package com.example.weplan;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class Splash extends AppCompatActivity {
  LottieAnimationView l;
  private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        l=findViewById(R.id.animation);
        SharedPreferences sharedpreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE );
        userid=sharedpreferences.getString("userid","notloggedin");
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable
        l.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(userid=="notloggedin"){
                Intent intent = new Intent(Splash.this, login_main.class);
                startActivity(intent);}
                else{ Intent intent = new Intent(Splash.this, DashboardActivity.class);
                startActivity(intent);}

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Intent intent = new Intent(Splash.this, login_main.class);
                startActivity(intent);
            }
        });
    }

}
