package com.example.weplan.Fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.developer.kalert.KAlertDialog;
import com.example.weplan.Classes.Services;
import com.example.weplan.ProfileScreen;
import com.example.weplan.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyOrganizerListRecyclerViewAdapter extends RecyclerView.Adapter<MyOrganizerListRecyclerViewAdapter.ViewHolder> {

    private final List<Services> mValues;
    private final OrganizerListFragment.OnListFragmentInteractionListener mmListener;
    public ProfileScreen profileScreen;
    KAlertDialog pDialog;
    private Handler mHandler;


    public MyOrganizerListRecyclerViewAdapter(List<Services> items, OrganizerListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mmListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_organizerlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String name=mValues.get(position).servicename;
        final String location=mValues.get(position).location;
        final String rating=mValues.get(position).rating;
        final Float ratingFloat=Float.parseFloat(rating);
        final String budget=mValues.get(position).startb;
        final String imagelink=mValues.get(position).imglink;
        final String placeid=mValues.get(position).placeid;
        mHandler=new Handler();
        holder.organizername.setText(name);
        holder.locationorg.setText(location);
        holder.ratingorg.setText(rating);
        holder.startb.setText(budget);
        holder.endb.setText(mValues.get(position).endb);
        holder.imageView.setImageBitmap(mValues.get(position).imgbitmap);
        Picasso.get().load(mValues.get(position).imglink).into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (null != mmListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mmListener.onListFragmentInteraction();

                    Bundle bundle=new Bundle();
                    bundle.putString("name",name);
                    bundle.putString("location",location);
                    bundle.putFloat("rating",ratingFloat);
                    bundle.putString("budget",budget);
                    bundle.putString("imageLink",imagelink);
                    bundle.putString("placeid",placeid);
                    final Intent intent=new Intent(v.getContext(), ProfileScreen.class);
                    intent.putExtras(bundle);
                    pDialog = new KAlertDialog(v.getContext(), KAlertDialog.PROGRESS_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#4E67FD"));
                    pDialog.setTitleText("Please Wait");
                    pDialog.setCancelable(false);
                    pDialog.show();

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



    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView organizername;
        public final TextView locationorg;
        public final TextView ratingorg;
        public final TextView startb;
        public final TextView endb;
        public final ImageView imageView;
        final String imgURL  = "https://www.google.com/images/srpr/logo11w.png";

        public Services mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            organizername = (TextView) view.findViewById(R.id.uuuuu);
            locationorg=(TextView) view.findViewById(R.id.user_location_org);
            ratingorg=(TextView) view.findViewById(R.id.rating_org);
            startb=(TextView) view.findViewById(R.id.startb);
            endb=(TextView) view.findViewById(R.id.endb);
            imageView=(ImageView) view.findViewById(R.id.imageView12);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + organizername.getText() + "'";
        }


    }





}
