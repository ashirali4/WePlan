package com.example.weplan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.listners.AlLoginHandler;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.example.weplan.Classes.FirebaseHelper;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
public class SellerDashboard extends AppCompatActivity {
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    SharedPreferences sharedpreferences;
    FirebaseHelper firebaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);
        // Initialize the SDK
        sharedpreferences = getSharedPreferences("sellerinfo", Context.MODE_PRIVATE );
        Places.initialize(getApplicationContext(),"AIzaSyBDZJKicpvy9Q3yP7IKzflvsH9Y3WA_Np8");
        firebaseHelper=new FirebaseHelper();

// Create a new Places client instance
        PlacesClient placesClient = Places.createClient(this);
    }

    public void googleyou(View view) {


        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Toast.makeText(this, "Place: " + place.getName() + ", " + place.getId(), Toast.LENGTH_SHORT).show();
                String uid=sharedpreferences.getString("sellerid","notlogged");
                firebaseHelper.pushorganizerplace(uid,place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(this, "error" + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void startChat(View view) {

        String uid=sharedpreferences.getString("sellerid","notlogged");
        String sname=sharedpreferences.getString("sellername","notlogged");


        User user = new User();
        user.setUserId(uid); //userId it can be any unique user identifier NOTE : +,*,? are not allowed chars in userId.
        user.setDisplayName(sname); //displayName is the name of the user which will be shown in chat messages
        user.setEmail(""); //optional
        user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());  //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
        user.setPassword(""); //optional, leave it blank for testing purpose, read this if you want to add additional security by verifying password from your server https://www.applozic.com/docs/configuration.html#access-token-url
        user.setImageLink("");//optional, set your image link if you have

        Applozic.connectUser(this, user, new AlLoginHandler() {
            @Override
            public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                Toast.makeText(context, "user created", Toast.LENGTH_SHORT).show();

                 Intent intent = new Intent(context, ConversationActivity.class);
                 startActivity(intent);
            }

            @Override
            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
