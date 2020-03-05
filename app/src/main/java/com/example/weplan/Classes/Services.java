package com.example.weplan.Classes;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Services {
    public String servicename;
    public String location;
    public String rating;
    public String imglink;
    public String startb;
    public String endb;
    public ImageView imageview;
    public Bitmap imgbitmap;
    public Services(){


    }
    public Services(String servicename){
        this.servicename=servicename;

    }

    public void setservicename(String Servicename){
        this.servicename=servicename;
    }
    public String getservicename(){
        return servicename;
    }

    public void setLocation(String location){
        this.location=location;
    }
    public String getLocation(){
        return location;
    }

    public void setRating(String rating){
        this.rating=rating;
    }
    public String getRating(){
        return rating;
    }

}
