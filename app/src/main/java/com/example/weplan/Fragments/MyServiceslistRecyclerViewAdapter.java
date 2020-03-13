package com.example.weplan.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weplan.Classes.servicelist;
import com.example.weplan.Fragments.ServiceslistFragment.OnListFragmentInteractionListener;
import com.example.weplan.Fragments.dummy.DummyContent.DummyItem;
import com.example.weplan.MainActivity;
import com.example.weplan.ProfileScreen;
import com.example.weplan.R;
import com.example.weplan.Search;
import com.example.weplan.ServicesList;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyServiceslistRecyclerViewAdapter extends RecyclerView.Adapter<MyServiceslistRecyclerViewAdapter.ViewHolder> {

    private final List<servicelist> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyServiceslistRecyclerViewAdapter(List<servicelist> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_serviceslist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final String name=mValues.get(position).sname;
        final String imglink=mValues.get(position).logolink;
        holder.servicename.setText(mValues.get(position).sname);
        Picasso.get().load(mValues.get(position).logolink).into(holder.servicelogo);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction();

                    Intent intent=new Intent(v.getContext(), ServicesList.class);
                    intent.putExtra("ServiceName",name);
                    intent.putExtra("imglink",imglink);
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
        public final TextView servicename;
        public final ImageView servicelogo;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            servicename = (TextView) view.findViewById(R.id.servicenameonlayout);
            servicelogo = (ImageView) view.findViewById(R.id.serviceimage);

        }
    }
}
