package com.example.weplan.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weplan.Classes.Services;
import com.example.weplan.R;
import com.squareup.picasso.Picasso;

import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.MyView> {

    // List with String type

    private List<Services> list;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {

        // Text View
        TextView rating,name,bud,img;
        ImageView imageview;
        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

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
    public Adapter(List<Services> horizontalList)
    {
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
        holder.rating.setText(list.get(position).rating);
        holder.bud.setText(list.get(position).endb);
        holder.name.setText(list.get(position).servicename);
        Picasso.get().load(list.get(position).imglink).into(holder.imageview);
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return list.size();
    }
}