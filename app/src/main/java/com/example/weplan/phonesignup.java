package com.example.weplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class phonesignup extends AppCompatActivity {

    EditText mobile;
    ImageView button;
    String no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_signup);

        mobile = (EditText) findViewById(R.id.mobile);

        button = (ImageView) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                no = mobile.getText().toString();
                if(no.length()==10) {
                    Intent intent = new Intent(phonesignup.this, verifyphonenumber.class);
                    intent.putExtra("mobile", no);
                    startActivity(intent);
                    Toast.makeText(phonesignup.this, no, Toast.LENGTH_LONG).show();
                }
                else {
                    mobile.setError("Enter a valid mobile");
                    mobile.requestFocus();
                }
            }
        });


    }

    //function to hide keyboard when touch on ui
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    public void backbutt(View view) {
        this.finish();
    }
}

