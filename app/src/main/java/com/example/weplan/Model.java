package com.example.weplan;

public class Model {

    public String name,dp,lastSeen;


    public Model() {
    }

    public Model(String name, String dp, String lastSeen) {
        this.name = name;
        this.lastSeen = lastSeen;
        this.dp = dp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setlastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public String getlastSeen() {
        return lastSeen;
    }

    public String getDp() {
        return dp;
    }

}
