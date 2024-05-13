package com.example.doctorchatbotapp.User.ModelClass;

public class PaymentModel {
    private String fullName, bankName, cardNumber, securityCode, address, phoneNumber;

    public PaymentModel(String fullName, String bankName, String cardNumber, String securityCode, String address, String phoneNumber) {
        this.fullName = fullName;
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public PaymentModel() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
