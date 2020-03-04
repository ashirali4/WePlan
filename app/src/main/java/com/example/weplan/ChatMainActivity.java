package com.example.weplan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ChatMainActivity extends AppCompatActivity {

    private TabsAccess tabsAccess;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private String currentUserId,currentDate,currentTime;;
    private RecyclerView chatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);


        rootRef= FirebaseDatabase.getInstance().getReference();
        mAuth= FirebaseAuth.getInstance();

//       contactsRef= FirebaseDatabase.getInstance().getReference().child("Contacs").child(currentUserId);

        toolbar=findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("WePlan");
        viewPager=findViewById(R.id.main_tabs_pager);
        tabsAccess=new TabsAccess(getSupportFragmentManager());
        viewPager.setAdapter(tabsAccess);
        tabLayout=findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);
        // chatList=findViewById(R.id.chats_list);
//        chatList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        userState("online");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        userState("offline");
    }


    private void userState(String state)
    {
        FirebaseUser currentUser=mAuth.getCurrentUser();
        currentUserId=currentUser.getUid();
        Calendar calendarDate=Calendar.getInstance();
        SimpleDateFormat currentDateFormat=new SimpleDateFormat("MMM dd,yyyy");
        currentDate=currentDateFormat.format(calendarDate.getTime());

        Calendar calendarTime=Calendar.getInstance();
        SimpleDateFormat currentTimeFormat=new SimpleDateFormat("hh:mm a");  //a is am or pm (12 hours)
        currentTime=currentTimeFormat.format(calendarTime.getTime());

        HashMap<String,Object> onlineState=new HashMap<>();
        onlineState.put("time",currentTime);
        onlineState.put("date",currentDate);
        onlineState.put("state",state);

        rootRef.child("Users").child(currentUserId).child("userState").updateChildren(onlineState);

    }

}
