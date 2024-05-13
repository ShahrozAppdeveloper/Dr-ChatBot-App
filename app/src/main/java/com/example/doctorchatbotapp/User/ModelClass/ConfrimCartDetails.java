package com.example.doctorchatbotapp.User.ModelClass;

public class ConfrimCartDetails {
    String userId,prodoctId,ownerID,image,price,quantitiy,name;

    public ConfrimCartDetails() {
    }

    public ConfrimCartDetails(String userId, String prodoctId, String ownerID, String image, String price, String quantitiy, String name) {
        this.userId = userId;
        this.prodoctId = prodoctId;
        this.ownerID = ownerID;
        this.image = image;
        this.price = price;
        this.quantitiy = quantitiy;
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProdoctId() {
        return prodoctId;
    }

    public void setProdoctId(String prodoctId) {
        this.prodoctId = prodoctId;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantitiy() {
        return quantitiy;
    }

    public void setQuantitiy(String quantitiy) {
        this.quantitiy = quantitiy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
