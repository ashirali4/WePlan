package com.example.weplan.Classes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class DBController {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;
    String currentUserId;


    public DBController() {

        ref=FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
    }


    public void saveUserState(String currentTime,String currentDate,String state)
    {
        FirebaseUser currentUser=mAuth.getCurrentUser();
        currentUserId=currentUser.getUid();
        HashMap<String,Object> onlineState=new HashMap<>();
        onlineState.put("time",currentTime);
        onlineState.put("date",currentDate);
        onlineState.put("state",state);

        ref.child("Users").child(currentUserId).child("userState").updateChildren(onlineState);

    }

    public void insertUserData(String name,String password,String phone,String email,String location,String userKey)
    {
        DatabaseReference usersRef = ref.child("Users").child(userKey);


        Map<String, Object> users = new HashMap<>();
        users.put("name", name);
        users.put("phone",phone);
        users.put("email",email);
        users.put("location",location);

        usersRef.updateChildren(users);
    }

    public void insertSellerData(String name,String password,String phone,String email,String location,String userKey)
    {
        DatabaseReference usersRef = ref.child("Services").child(userKey);


        Map<String, Object> users = new HashMap<>();
        users.put("name", name);
        users.put("phone",phone);
        users.put("email",email);
        users.put("location",location);

        usersRef.updateChildren(users);
    }

    public void insertSocialAccounts(String type,String userKey,String token)
    {
        DatabaseReference usersRef = ref.child("SocialAccounts").child(userKey).child(type);


        Map<String, Object> users = new HashMap<>();
        users.put("Token",token);

        usersRef.setValue(users);
    }
    public void removeSocialAccounts(String type,String uid){
        DatabaseReference usersRef = ref.child("SocialAccounts").child(uid).child(type);
        usersRef.removeValue();
    }


}
