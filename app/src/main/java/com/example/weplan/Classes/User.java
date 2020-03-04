package com.example.weplan.Classes;

public class User {

    public User(){}
    public User(String name, String password, String location) {
        this.name = name;
        this.password = password;
        this.location = location;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    String userId,name,password,location;

    public void connectSocialAccount(String type,String Uid,String token)

    {
        DBController socialAccounts=new DBController();
        socialAccounts.insertSocialAccounts(type,Uid,token);
    }
    public void removeSocialAccount(String type,String Uid)
    {
        DBController socialAccounts=new DBController();
        socialAccounts.removeSocialAccounts(type,Uid);
    }
    public void viewSocialAccount(String type,String Uid)
    {

    }

}
