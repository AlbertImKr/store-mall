package com.albert.commerce.application.service;

public class StoreRegisterCommand extends Command {

    private final String userEmail;
    private final String storeName;
    private final String address;
    private final String phoneNumber;
    private final String email;
    private final String ownerName;

    public StoreRegisterCommand(String userEmail, String storeName, String address, String phoneNumber, String email,
            String ownerName) {
        this.userEmail = userEmail;
        this.storeName = storeName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ownerName = ownerName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
