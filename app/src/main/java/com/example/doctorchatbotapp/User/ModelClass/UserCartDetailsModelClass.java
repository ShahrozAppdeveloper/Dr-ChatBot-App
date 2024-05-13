package com.example.doctorchatbotapp.User.ModelClass;

public class UserCartDetailsModelClass {
    String userID,productID,productName,productImage,productPrice,quantity,productownerID;

    public UserCartDetailsModelClass() {
    }

    public UserCartDetailsModelClass(String userID, String productID, String productName, String productImage, String productPrice, String quantity, String ownerID) {
        this.userID = userID;
        this.productID = productID;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.productownerID = ownerID;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOwnerID() {
        return productownerID;
    }

    public void setOwnerID(String ownerID) {
        this.productownerID = ownerID;
    }
}
