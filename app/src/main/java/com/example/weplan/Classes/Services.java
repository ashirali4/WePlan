package com.example.weplan.Classes;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Services {
    public String servicename;
    public String location;
    public String rating;
    public String imglink;

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public void setStartb(String startb) {
        this.startb = startb;
    }

    public void setEndb(String endb) {
        this.endb = endb;
    }

    public void setImageview(ImageView imageview) {
        this.imageview = imageview;
    }

    public void setImgbitmap(Bitmap imgbitmap) {
        this.imgbitmap = imgbitmap;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    public String getServicename() {
        return servicename;
    }

    public String getImglink() {
        return imglink;
    }

    public String getStartb() {
        return startb;
    }

    public String getEndb() {
        return endb;
    }

    public ImageView getImageview() {
        return imageview;
    }

    public Bitmap getImgbitmap() {
        return imgbitmap;
    }

    public String getPlaceid() {
        return placeid;
    }

    public String startb;
    public String endb;
    public ImageView imageview;
    public Bitmap imgbitmap;
    public String placeid;
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
