package com.example.weplan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weplan.Classes.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class connect_social_account extends AppCompatActivity implements View.OnClickListener{
    SharedPreferences sharedPref ;
    int RC_SIGN_IN;
    public static final String MyPREFERENCES = "accesstokens" ;
    User user;
    LinearLayout facebookconnect;
    private static final String TAG= "FACEBOOK";
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    SignInButton signInButton;
    ImageButton button;
    TextView accesstoken,googles;
    LinearLayout googlebefore,facebookbefore,googleafter,facebookafter;
    LoginButton loginButton;
    GoogleSignInClient mGoogleSignInClient;
    SharedPreferences preferences;
    String sellerid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_social_account);
        sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        googlebefore=findViewById(R.id.togooglelayout);
        facebookbefore=findViewById(R.id.tofblayout);
        googleafter=findViewById(R.id.gconnectedlayout);
        facebookafter=findViewById(R.id.fbconnectedlayout);
        user=new User();
        mAuth = FirebaseAuth.getInstance();
        accesstoken=findViewById(R.id.acesstoken);
        googles=findViewById(R.id.googles);
        initializeUI();

        preferences= this.getSharedPreferences("sellerinfo", Context.MODE_PRIVATE);
        sellerid=preferences.getString("sellerid","notloggedin");


        mCallbackManager = CallbackManager.Factory.create();
          loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email",
                "manage_pages",
                " pages_show_list",
                "public_profile",
                "user_age_range",
                "user_gender",
                "user_friends",
                "user_birthday",
                "user_hometown",
                " user_likes",
                "user_link",
                "user_location",
                "user_photos",
                "user_posts",
                "user_status",
                "user_tagged_places",
                "user_videos",
                "ads_management",
                "ads_read",
                "attribution_read",
                "business_management",
                "groups_access_member_info",
                "pages_manage_cta",
                "pages_manage_instant_articles",
                "pages_messaging",
                "pages_messaging_phone_number",
                "pages_messaging_subscriptions",
                "publish_pages",
                "publish_to_groups",
                "read_page_mailboxes",
                "user_events",
                "catalog_management",
                "instagram_basic",
                "instagram_manage_comments",
                "instagram_manage_insights",
                "leads_retrieval",
                "publish_video",
                "read_insights",
                "whatsapp_business_management");

        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(
                    "com.example.weplan",
                    PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        for (Signature signature : info.signatures) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            md.update(signature.toByteArray());
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
        }
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                //handleFacebookAccessToken(loginResult.getAccessToken())
                String access_token=loginResult.getAccessToken().getToken().toString();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("", access_token);
                editor.commit();
                accesstoken.setText(access_token);
                facebookbefore.setVisibility(View.GONE);
                facebookafter.setVisibility(View.VISIBLE);
                user.connectSocialAccount("Facebook",mAuth.getCurrentUser().getUid().toString(),access_token);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });



    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Succesfull", Toast.LENGTH_SHORT).show();
            String ggtoken=account.getId().toString();
            googles.setText(ggtoken);
            googlebefore.setVisibility(View.INVISIBLE);
            googleafter.setVisibility(View.VISIBLE);
            user.connectSocialAccount("Google",mAuth.getCurrentUser().getUid().toString(),ggtoken);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }



    @SuppressLint("WrongViewCast")
    private void initializeUI() {
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {

        super.onStart();
        String at=sharedPref.getString("fbtoken",null);
        accesstoken.setText(at);
        if(at==null){
            facebookbefore.setVisibility(View.VISIBLE);
            facebookafter.setVisibility(View.GONE);
        }
        else{
            facebookbefore.setVisibility(View.GONE);
            facebookafter.setVisibility(View.VISIBLE);
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            // ...
        }
    }
    public void fblogin(View view) {
        loginButton.performClick();
    }

    public void removefb(View view) {

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("fbtoken", null);
        editor.commit();
        LoginManager.getInstance().logOut();
        facebookbefore.setVisibility(View.VISIBLE);
        facebookafter.setVisibility(View.GONE);
        accesstoken.setText(null);
        user.removeSocialAccount("Facebook",mAuth.getCurrentUser().getUid());
    }

    public void backbut(View view) {
        this.finish();
    }

    public void removeg(View view) {
        googlebefore.setVisibility(View.VISIBLE);
        facebookafter.setVisibility(View.GONE);
        googles.setText(null);
        user.removeSocialAccount("Google",mAuth.getCurrentUser().getUid());
    }

    public void viewdata(View view) {
        Intent intent=new Intent(connect_social_account.this,ViewFeedback.class);
        startActivity(intent);
    }
}
