package com.example.doctorchatbotapp.ModelClass;

public class AddPatientDetailToRealtym {
    String userid,username,imageurl,provedstatus,bookingstatus;

    public AddPatientDetailToRealtym() {
    }

    public AddPatientDetailToRealtym(String userid, String username, String imageurl, String provedstatus, String bookingstatus) {
        this.userid = userid;
        this.username = username;
        this.imageurl = imageurl;
        this.provedstatus = provedstatus;
        this.bookingstatus = bookingstatus;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getProvedstatus() {
        return provedstatus;
    }

    public void setProvedstatus(String provedstatus) {
        this.provedstatus = provedstatus;
    }

    public String getBookingstatus() {
        return bookingstatus;
    }

    public void setBookingstatus(String bookingstatus) {
        this.bookingstatus = bookingstatus;
    }
}
