package com.example.doctorchatbotapp.MedicalStore.MediModelClass;

public class AddProdcutDetails {
    String userID,productID,productname,productimage,productprice;

    public AddProdcutDetails() {
    }

    public AddProdcutDetails(String userID, String productID, String productname, String productimage, String productprice) {
        this.userID = userID;
        this.productID = productID;
        this.productname = productname;
        this.productimage = productimage;
        this.productprice = productprice;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }
}
