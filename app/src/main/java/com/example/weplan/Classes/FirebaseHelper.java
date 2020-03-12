package com.example.weplan.Classes;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.weplan.uploadinfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirebaseHelper {
    public static final List<Services> ITEMS = new ArrayList<Services>();
    DatabaseReference db;
    private FirebaseStorage firebaseStorage;
    public StorageReference storageReference;
    public String ashir;
    Boolean saved = null;
    String string;

    ArrayList<Services> arrayList = new ArrayList();
    ArrayList<servicelist> servicelist = new ArrayList();

    public interface Callback {
        void onSuccess(ArrayList<Services> arrayList);

        void onFailure(Exception e);


    }

    public interface servicecallback {
        void onSuccess(ArrayList<servicelist> arrayList);

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
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Services services = new Services();
                    services.id= snapshot.getKey();
                    services.servicename = snapshot.child("servicename").getValue().toString();
                    services.location = snapshot.child("location").getValue().toString();
                    services.rating = snapshot.child("rating").getValue().toString();
                    services.imglink = snapshot.child("link").getValue().toString();
                    services.startb = snapshot.child("sb").getValue().toString();
                    services.endb = snapshot.child("eb").getValue().toString();

                    if(snapshot.child("placeid").exists()) {
                        services.placeid = snapshot.child("placeid").getValue().toString();
                    }
                    if(snapshot.child("fb").exists()) {
                        services.fb=snapshot.child("fb").getValue().toString();
                    }
                    if(snapshot.child("token").exists()) {
                        services.fb=snapshot.child("token").getValue().toString();
                    }
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


    public void getserviceslist(final servicecallback callback) {


        db = FirebaseDatabase.getInstance().getReference("Servicelist");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    servicelist services = new servicelist();
                    services.sname = snapshot.child("servicename").getValue().toString();
                    services.logolink = snapshot.child("logo").getValue().toString();


                    servicelist.add(services);
                }
                callback.onSuccess(servicelist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onFailure(databaseError.toException());
            }
        });

    }

    public void updateuser(HashMap<String, Object> map,String userid) {
        firebaseStorage = FirebaseStorage.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);
        ref.updateChildren(map);

    }
    public void pushorganizerplace(String userid,String value) {
        firebaseStorage = FirebaseStorage.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Services").child(userid);
        ref.child("placeid").setValue(value);

    }
    public String getplaceid(String userid,String value) {
        firebaseStorage = FirebaseStorage.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Services").child(userid);
        String key=ref.child("placeid").getKey();
        return key;
    }

    public void changeimage(Uri FilePathUri, String extension, final String userid) {
        storageReference = firebaseStorage.getReference();
        final StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + extension);
        Uri file = Uri.fromFile(new File(FilePathUri.toString()));
        storageReference2.putFile(FilePathUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                                uploadinfo upload = new uploadinfo("imagename", url);

                                databaseReference.child("image").setValue(upload);
                            }
                        });
                        //uploadinfo imageUploadInfo = new uploadinfo(taskSnapshot.getUploadSessionUri().toString());
                       //String ImageUploadId = databaseReference.push().getKey();
                        //databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                    }
                });
    }
}


