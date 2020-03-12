package com.example.weplan.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weplan.Classes.servicelist;
import com.example.weplan.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Servicehomeadapter extends RecyclerView.Adapter<Servicehomeadapter.MyView> {

    // List with String type
    private List<servicelist> list;

    // View Holder class which
    // extends RecyclerView.ViewHolder
    public class MyView
            extends RecyclerView.ViewHolder {

        // Text View
        TextView textView;
        ImageView icon;

        // parameterised constructor for View Holder class
        // which takes the view as a parameter
        public MyView(View view)
        {
            super(view);

            // initialise TextView with id
            textView = (TextView)view
                    .findViewById(R.id.s_title);
            icon=(ImageView)view.findViewById(R.id.image3);

        }
    }

    // Constructor for adapter class
    // which takes a list of String type
    public Servicehomeadapter(List<servicelist> horizontalList)
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
                .inflate(R.layout.activity_serviceslist,
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
        holder.textView.setText(list.get(position).sname);
        Picasso.get().load(list.get(position).logolink).into(holder.icon);
    }

    // Override getItemCount which Returns
    // the length of the RecyclerView.
    @Override
    public int getItemCount()
    {
        return list.size();
    }
}