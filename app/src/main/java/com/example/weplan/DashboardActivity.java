package com.example.weplan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.listners.AlLoginHandler;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.MobiComKitBroadcastReceiver;
import com.applozic.mobicomkit.uiwidgets.conversation.fragment.MobiComQuickConversationFragment;
import com.example.weplan.Fragments.Accounts;
import com.example.weplan.Fragments.Home_Dashboard_Featured;
import com.example.weplan.Fragments.Location;
import com.example.weplan.Fragments.ProfileFragment;
import com.example.weplan.Fragments.chats;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DashboardActivity extends FragmentActivity implements Accounts.OnFragmentInteractionListener,Location.OnFragmentInteractionListener,Home_Dashboard_Featured.OnFragmentInteractionListener, chats.OnFragmentInteractionListener{

    private static int retry;
    ConversationUIService conversationUIService;
    MobiComQuickConversationFragment mobiComQuickConversationFragment;
    MobiComKitBroadcastReceiver mobiComKitBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar

       // getSupportActionBar().hide();


//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        User user = new User();
        user.setUserId("alsjdf"); //userId it can be any unique user identifier NOTE : +,*,? are not allowed chars in userId.
        user.setDisplayName("Ashir Ali"); //displayName is the name of the user which will be shown in chat messages
        user.setEmail(""); //optional
        user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());  //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
        user.setPassword(""); //optional, leave it blank for testing purpose, read this if you want to add additional security by verifying password from your server https://www.applozic.com/docs/configuration.html#access-token-url
        user.setImageLink("");//optional, set your image link if you have

        Applozic.connectUser(this, user, new AlLoginHandler() {
            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                //      Toast.makeText(context, "user created", Toast.LENGTH_SHORT).show();
                //    Intent intent = new Intent(context, ConversationActivity.class);
                //   intent.putExtra(ConversationUIService.USER_ID, "ashirali");
                // intent.putExtra(ConversationUIService.DISPLAY_NAME, "User Name"); //put it for displaying the title.
                // intent.putExtra(ConversationUIService.TAKE_ORDER,true); //Skip chat list for showing on back press
                // startActivity(intent);
                //Intent intent = new Intent(context, ConversationActivity.class);
                //  startActivity(intent);
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                Toast.makeText(DashboardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });


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
                            selectedFragment =  mobiComQuickConversationFragment;
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
