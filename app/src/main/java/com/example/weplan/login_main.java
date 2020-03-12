package com.example.weplan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_main extends AppCompatActivity {
    private EditText emailTV, passwordTV;
    private FirebaseAuth mAuth;
    KAlertDialog pDialog;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    DatabaseReference reference;
    TextView textView,signin;
    String useridfor;
    String namel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        mAuth = FirebaseAuth.getInstance();
        initializeUI();
         sharedpreferences = getSharedPreferences("userinfo", Context.MODE_PRIVATE );
       editor = sharedpreferences.edit();

       textView=findViewById(R.id.textview1);
       signin=findViewById(R.id.organizerSignIn);




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


    public void login_with_phone(View view) {
        Intent intent = new Intent(login_main.this, phonesignup.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void onBackPressed()
    {
        super.onBackPressed();
        pDialog.dismiss();
    }

    public void create_account(View view) {
        pDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#4E67FD"));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        pDialog.show();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(login_main.this, signup.class);
                startActivity(intent);
                pDialog.hide();
            }
        }, 1000);
    }

    public void Sign_in(View view) {
        loginUserAccount();

    }
    private void initializeUI() {
        emailTV = findViewById(R.id.email);
        passwordTV = findViewById(R.id.password);
    }

    private void loginUserAccount() {

        pDialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#4E67FD"));
        pDialog.setTitleText("Please Wait");
        pDialog.setCancelable(false);
        final String email, password;
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
        pDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            String uid=mAuth.getUid().toString();
                            useridfor=uid;
                            editor.putString("userid", uid); // Storing string
                            reference = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override

                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String Username = dataSnapshot.child("email").getValue().toString();
                                    editor.putString("email",Username);
                                    Toast.makeText(login_main.this, Username, Toast.LENGTH_SHORT).show();
                                    String Adress = dataSnapshot.child("location").getValue().toString();
                                    editor.putString("location",Adress);
                                    String name = dataSnapshot.child("name").getValue().toString();
                                    namel=dataSnapshot.child("name").getValue().toString();
                                    editor.putString("name",name);
                                    String Phone = dataSnapshot.child("phone").getValue().toString();
                                    editor.putString("phone",Phone);
                                    editor.commit();


                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            Handler mhandler=new Handler();
                            mhandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(login_main.this, DashboardActivity.class);
                                    startActivity(intent);
                                    pDialog.hide();
                                }
                            },3000);


                        }
                        else {
                            pDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                               pDialog .setTitleText("Please Verify Creditionals Again");
                               pDialog .setContentText("Please Verify Creditionals Again");
                               pDialog.setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    pDialog .hide();
                                }
                            });

                            pDialog.isShowCancelButton();
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    public void backbut(View view) {

    }

    public void showOrganizerLogin(View view) {
        Intent intent=new Intent(this,SellerSignIn.class);
        startActivity(intent);
    }


}
