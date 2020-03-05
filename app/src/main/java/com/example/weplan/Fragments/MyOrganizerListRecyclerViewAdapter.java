package com.example.weplan.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weplan.Classes.Services;
import com.example.weplan.ProfileScreen;
import com.example.weplan.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MyOrganizerListRecyclerViewAdapter extends RecyclerView.Adapter<MyOrganizerListRecyclerViewAdapter.ViewHolder> {

    private final List<Services> mValues;
    private final OrganizerListFragment.OnListFragmentInteractionListener mmListener;
    public ProfileScreen profileScreen;

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
        final String budget=mValues.get(position).startb;
        final Bitmap image=mValues.get(position).imgbitmap;

        holder.organizername.setText(name);
        holder.locationorg.setText(location);
        holder.ratingorg.setText(rating);
        holder.startb.setText(budget);
        holder.endb.setText(mValues.get(position).endb);
        holder.imageView.setImageBitmap(image);
        Picasso.get().load(mValues.get(position).imglink).into(holder.imageView);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mmListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mmListener.onListFragmentInteraction();
                    Toast.makeText(v.getContext(), "kjhk  "+ position, Toast.LENGTH_SHORT).show();

                    Bundle bundle=new Bundle();
                    bundle.putString("name",name);
                    bundle.putString("location",location);
                    bundle.putString("rating",rating);
                    bundle.putString("budget",budget);
                    bundle.putParcelable("image",image);
                    Intent intent=new Intent(v.getContext(), ProfileScreen.class);
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);

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
