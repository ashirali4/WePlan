package com.example.weplan.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weplan.Classes.Services;
import com.example.weplan.Fragments.MyServiceslistRecyclerViewAdapter;
import com.example.weplan.ProfileScreen;
import com.example.weplan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceAdapter extends ArrayAdapter<Services>  {


    public ServiceAdapter(@NonNull Context context, int resource, @NonNull List<Services> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.fragment_organizerlist, null);
        }
        Services services = getItem(position);
        if (services != null) {

            TextView name = view.findViewById(R.id.uuuuu);
            TextView location = view.findViewById(R.id.user_location_org);
            TextView rating = view.findViewById(R.id.rating_org);
            TextView startbudg = view.findViewById(R.id.startb);
            TextView endbudg = view.findViewById(R.id.endb);
            ImageView imageView = view.findViewById(R.id.imageView12);
            if (name != null) {
                name.setText(services.getServicename());
            }
            if (imageView != null) {
                String link = services.getImglink();
                Picasso.get().load(link).into(imageView);
            }
            if (location != null) {
                location.setText(services.getLocation());
            }
            if (rating != null) {
                rating.setText(services.getRating());
            }
            if (startbudg != null) {
                startbudg.setText(services.getStartb());
            }
            if (endbudg != null) {
                endbudg.setText(services.getEndb());
            }
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rati=getItem(position).rating;
                Float rat=Float.parseFloat(rati);
                String name=getItem(position).getServicename();
                String location=getItem(position).getLocation();
                String budget=getItem(position).getEndb();
                String imagelink=getItem(position).getImglink();
                String placeid=getItem(position).getPlaceid();
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("location", location);
                bundle.putFloat("rating", rat);
                bundle.putString("budget", budget);
                bundle.putString("imageLink", imagelink);
                bundle.putString("placeid", placeid);
                final Intent intent = new Intent(v.getContext(), ProfileScreen.class);
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
            }
        });
        return  view;
    }


}
