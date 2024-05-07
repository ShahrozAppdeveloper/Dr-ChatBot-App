package com.example.doctorchatbotapp.ModelClass;

public class AddRequserDetailsToDatabase {
    String userID,username,imageurl,reqID;

    public AddRequserDetailsToDatabase() {
    }

    public AddRequserDetailsToDatabase(String userID, String username, String imageurl, String reqID) {
        this.userID = userID;
        this.username = username;
        this.imageurl = imageurl;
        this.reqID = reqID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }
}
