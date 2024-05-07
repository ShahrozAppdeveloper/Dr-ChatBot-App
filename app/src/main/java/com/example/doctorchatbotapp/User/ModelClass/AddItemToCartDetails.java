package com.example.doctorchatbotapp.User.ModelClass;

public class AddItemToCartDetails {
    String ownerID,productId,productImage,productname,productprice;

    public AddItemToCartDetails() {
    }

    public AddItemToCartDetails(String ownerID, String productId, String productImage, String productname, String productprice) {
        this.ownerID = ownerID;
        this.productId = productId;
        this.productImage = productImage;
        this.productname = productname;
        this.productprice = productprice;
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
}
