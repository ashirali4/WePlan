package com.example.weplan.Adapters;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.developer.kalert.KAlertDialog;
import com.example.weplan.Classes.Services;
import com.example.weplan.Fragments.Location;
import com.example.weplan.ProfileScreen;
import com.example.weplan.R;
import com.squareup.picasso.Picasso;

import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.MyView> {

    // List with String type

    private List<Services> list;
    private final Location.OnFragmentInteractionListener mmListener;
    KAlertDialog pDialog;
    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {
        public final View mView;

        // Text View
        TextView rating,name,bud,img;
        ImageView imageview;
        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);
            mView = view;
            // initialise TextView with id
            rating = (TextView)view
                    .findViewById(R.id.featuredratimg);
            name = (TextView)view
                    .findViewById(R.id.featuredname);
            bud = (TextView)view
                    .findViewById(R.id.featuredprice);
            imageview = (ImageView)view
                    .findViewById(R.id.featuredimage);
        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public Adapter(List<Services> horizontalList, Location.OnFragmentInteractionListener listener)
    {
        mmListener=listener;
        this.list = horizontalList;
    }

    // Override onCreateViewHolder which deals
    // with the inflation of the card layout
    // as an item for the RecyclerView.
    @Override
    public MyView onCreateViewHolder(ViewGroup parent,
                                     int viewType)
    {

        // Inflate item.xml using LayoutInflator
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.fragment_featuredlists,
                        parent,
                        false);

        // return itemView
        return new MyView(itemView);
    }



    // Override onBindViewHolder which deals
    // with the setting of different data
    // and methods related to clicks on
    // particular items of the RecyclerView.
    @Override
    public void onBindViewHolder(final MyView holder,
                                 final int position)
    {
        // Set the text of each item of
        // Recycler view with the list items
        final String name=list.get(position).servicename;
        final String id=list.get(position).id;
        final String location=list.get(position).location;
        final String rating=list.get(position).rating;
        final Float ratingFloat=Float.parseFloat(rating);
        final String budget=list.get(position).startb;
        final String imagelink=list.get(position).imglink;
        final String placeid=list.get(position).placeid;
        final String fb=list.get(position).fb;
        final String token=list.get(position).token;
        final String phone=list.get(position).phone;
        holder.rating.setText(list.get(position).rating);
        holder.bud.setText(list.get(position).endb);
        holder.name.setText(list.get(position).servicename);
        Picasso.get().load(list.get(position).imglink).into(holder.imageview);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (null != mmListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    Toast.makeText(v.getContext(), "hi", Toast.LENGTH_SHORT).show();
                    Bundle bundle=new Bundle();
                    bundle.putString("id",id);
                    bundle.putString("phone",phone);
                    bundle.putString("name",name);
                    bundle.putString("location",location);
                    bundle.putFloat("rating",ratingFloat);
                    bundle.putString("budget",budget);
                    bundle.putString("imageLink",imagelink);
                    bundle.putString("placeid",placeid);
                    bundle.putString("fb",fb);
                    bundle.putString("token",token);
                    final Intent intent=new Intent(v.getContext(), ProfileScreen.class);
                    intent.putExtras(bundle);
                    pDialog = new KAlertDialog(v.getContext(), KAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#4E67FD"));
                    pDialog.setTitleText("Please Wait");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    Handler mHandler=new Handler();
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            v.getContext().startActivity(intent);
                            pDialog.hide();
                        }
                    }, 1000);




                }
            }
        });
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return list.size();
    }
}