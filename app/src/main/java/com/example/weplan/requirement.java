package com.example.weplan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class requirement extends AppCompatActivity {


    TextInputEditText textbudget,textlocation,textpeople;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static Context reqContext;
    RadioButton radioButtonorg,radioButtonservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement);
        List<String> list = new ArrayList<String>();
         list.add("ashir");
         list.add("ali");

        reqContext=getApplicationContext();

         textbudget=findViewById(R.id.textbudget);
         textlocation=findViewById(R.id.textlocation);
         textpeople=findViewById(R.id.textpeoplecount);

         radioButtonorg=findViewById(R.id.radioButtonOrganizer);
         radioButtonservice=findViewById(R.id.radioButtonService);


        sharedpreferences = getSharedPreferences("requirements", Context.MODE_PRIVATE );
        editor = sharedpreferences.edit();

    }
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
    public void searchforreq(View view) {

        if (textpeople.getText().toString().equals("") || textlocation.getText().toString().equals("") || textbudget.getText().toString().equals(""))
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        else {
            if(radioButtonorg.isChecked() || radioButtonservice.isChecked()) {
                editor.putString("budget", textbudget.getText().toString()); // Storing string
                editor.putString("peoplecount", textpeople.getText().toString()); // Storing string
                editor.putString("location", textlocation.getText().toString()); // Storing string
                editor.commit();

                Intent intent = new Intent(this, Search.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Check Organizer or Service Provider", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static Context ApplicationContext(){
        return reqContext;
    }
}
