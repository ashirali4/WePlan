package com.example.weplan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.weplan.Classes.DBController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    private EditText emailTV, passwordTV,phoneTV,locationTV,nameTV;
    private Button regBtn;
    private ProgressBar progressBar;
    DBController dbController;

    String name,password,phone,email,location;



    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        dbController=new DBController();

        initializeUI();


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
    private void registerNewUser() {


        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        name=nameTV.getText().toString();
        password=passwordTV.getText().toString();
        phone=phoneTV.getText().toString();
        email=emailTV.getText().toString();
        location=locationTV.getText().toString();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            String userKey=mAuth.getCurrentUser().getUid();
                            dbController.insertUserData(name,password,phone,email,location,userKey);


                            Intent intent = new Intent(signup.this, preconnect.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void initializeUI() {
        emailTV = findViewById(R.id.signup_email);
        passwordTV = findViewById(R.id.signup_password);
        phoneTV=findViewById(R.id.signup_phone);
        locationTV=findViewById(R.id.signup_location);
        nameTV=findViewById(R.id.signup_name);

    }
    public void register_user(View view) {
        registerNewUser();
    }

    public void backbut(View view) {
        this.finish();
    }
}
