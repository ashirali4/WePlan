package com.example.weplan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.lang.System.exit;

public class SellerSignIn extends AppCompatActivity {


    DatabaseReference reference;
    TextInputEditText textsellerId;
    String sellerid;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_sign_in);

        textsellerId=findViewById(R.id.sellerId);

        sharedpreferences = getSharedPreferences("sellerinfo", Context.MODE_PRIVATE );
        editor = sharedpreferences.edit();
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
    public void SignInSeller(View view) {
        reference = FirebaseDatabase.getInstance().getReference("Services");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    sellerid= snapshot.getValue().toString();
                    if(sellerid==textsellerId.toString()) {
                        editor.putString("sellerid", sellerid); // Storing string
                        editor.commit();
                        Toast.makeText(SellerSignIn.this, "check"+sellerid.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getApplicationContext(),SellerDashboard.class);
                        startActivity(intent);
                        exit(0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    public void createSeller(View view) {

        Intent intent=new Intent(this,SellerSignup.class);
        startActivity(intent);

    }

    public void backPressed(View view) {
        this.finish();
    }
}
