package com.example.weplan.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.weplan.Classes.FirebaseHelper;
import com.example.weplan.R;
import com.example.weplan.uploadinfo;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnSelectedListener;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    String service_name, location, budget, imageLink;


    private static Uri FilePathUri = null;
    EditText txtName, textType, textPrice, phone, textLocation, username;

    RatingBar ratingBar;

    FirebaseHelper firebaseHelper;

    MaterialButton editbutton,updatebutton;
    Button Update, Edit;
    ImageView imageView;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        txtName = view.findViewById(R.id.name2);
        username=view.findViewById(R.id.useremail);
        //textType = view.findViewById(R.id.serviceType);
        imageView = view.findViewById(R.id.profileimage);
        textLocation = view.findViewById(R.id.location);
        textPrice = view.findViewById(R.id.price);
        phone=view.findViewById(R.id.number);
        ratingBar = view.findViewById(R.id.ratingstar);
        editbutton=view.findViewById(R.id.editProfile);
        updatebutton=view.findViewById(R.id.updatebutton);




        Disable();
        showData();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseimage();
//                UploadImage();
            }
        });
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Enable();
            }
        });

        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", txtName.getText().toString());
                map.put("email", username.getText().toString());
                map.put("phoneNumber", phone.getText().toString());
                map.put("budget", textPrice.getText().toString());
                map.put("type", textType.getText().toString());
                map.put("location", textLocation.getText().toString());

                firebaseHelper.updateuser(map);
                UploadImage();
                Disable();
            }
        });

        return view;

    }

    public void showData() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                txtName.setText(name);
                String Username = dataSnapshot.child("email").getValue().toString();
                username.setText(Username);
                String Phone = dataSnapshot.child("phone").getValue().toString();
                phone.setText(Phone);
                String Adress = dataSnapshot.child("location").getValue().toString();
                textLocation.setText(Adress);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getActivity().getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public void UploadImage() {

        if (FilePathUri != null) {

//            progressDialog.setTitle("Image is Uploading...");
//            progressDialog.show();
            String extension=GetFileExtension(FilePathUri);
            firebaseHelper.changeimage(FilePathUri,extension);

        } else {

            Toast.makeText(getContext(), "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }


    public void chooseimage() {
        TedImagePicker.with(getActivity())
                .start(new OnSelectedListener() {
                    @Override
                    public void onSelected(@NotNull Uri uri) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver(), uri);
                            FilePathUri = uri;
                            imageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void Disable() {
        txtName.setEnabled(false);
        username.setEnabled(false);
        phone.setEnabled(false);
        textLocation.setEnabled(false);
        textType.setEnabled(false);
        textPrice.setEnabled(false);
    }

    public void Enable() {
        txtName.setEnabled(true);
        username.setEnabled(true);
        phone.setEnabled(true);
        textLocation.setEnabled(true);
        textType.setEnabled(true);
        textPrice.setEnabled(true);
    }

}
