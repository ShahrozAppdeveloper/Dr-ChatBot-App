package com.example.doctorchatbotapp.User.ModelClass;

import java.io.Serializable;

public class AddItemToCartDetails implements Serializable {
    String UserID,ownerID,productId,productImage,productname,productprice,productquantity;

    public AddItemToCartDetails() {
    }

    public AddItemToCartDetails(String userID, String ownerID, String productId, String productImage, String productname, String productprice, String productquantity) {
        UserID = userID;
        this.ownerID = ownerID;
        this.productId = productId;
        this.productImage = productImage;
        this.productname = productname;
        this.productprice = productprice;
        this.productquantity = productquantity;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductquantity() {
        return productquantity;
    }

    public void setProductquantity(String productquantity) {
        this.productquantity = productquantity;
    }
}
