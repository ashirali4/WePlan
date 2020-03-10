package com.example.weplan;

public class uploadinfo {

    public String imageURL;
    public uploadinfo(String s){}

    public uploadinfo(String name, String url) {
        this.imageURL = url;
    }


    public String getImageURL() {
        return imageURL;
    }
}
