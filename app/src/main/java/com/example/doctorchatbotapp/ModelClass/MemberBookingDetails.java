package com.example.doctorchatbotapp.ModelClass;

public class MemberBookingDetails {
    String memberID, doctorID,reqID,reqstatus;

    public MemberBookingDetails() {
    }

    public MemberBookingDetails(String memberID, String trainnerID, String reqID, String reqstatus) {
        this.memberID = memberID;
        doctorID = trainnerID;
        this.reqID = reqID;
        this.reqstatus = reqstatus;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getReqstatus() {
        return reqstatus;
    }

    public void setReqstatus(String reqstatus) {
        this.reqstatus = reqstatus;
    }
}
