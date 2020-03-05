package com.example.weplan.Classes;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {
    public static final List<Services> ITEMS = new ArrayList<Services>();
    DatabaseReference db;
    public String ashir;
    Boolean saved=null;
    String string;
    ArrayList<String> serviceslist=new ArrayList<>();
    ArrayList<Services> arrayList=new ArrayList();

    public interface Callback{
        void onSuccess(ArrayList<Services> arrayList);
        void onFailure(Exception e);


    }

    public FirebaseHelper() {

    }

    //WRITE

    public void getlist(final Callback callback) {


        db = FirebaseDatabase.getInstance().getReference("Services");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Services services = new Services();
                    services.servicename = snapshot.child("servicename").getValue().toString();
                    services.location = snapshot.child("location").getValue().toString();
                    services.rating = snapshot.child("rating").getValue().toString();
                    services.imglink = snapshot.child("link").getValue().toString();
                    services.startb = snapshot.child("sb").getValue().toString();
                    services.endb = snapshot.child("eb").getValue().toString();


                    arrayList.add(services);
                }
                callback.onSuccess(arrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });

    }

    }
