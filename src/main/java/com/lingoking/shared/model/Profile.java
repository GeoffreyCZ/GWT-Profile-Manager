package com.lingoking.shared.model;

import java.io.Serializable;

public class Profile implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String passwordAgain;
    private String phoneNumber;
    private Address address;
    private String avatar;

    private String imageString;

    public Profile() {
    }

    public Profile(String id, String firstName, String lastName, String emailAddress, String avatar) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.avatar = avatar;
    }

    public Profile(String id, String firstName, String lastName, String emailAddress, String password, String phoneNumber, Address address, String avatar) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.avatar = avatar;
    }

    public Profile(String firstName, String lastName, String emailAddress, String phoneNumber, Address address, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.avatar = avatar;
    }


    public Profile(String id, String firstName, String lastName, String emailAddress, String password, String passwordAgain, String phoneNumber, Address address, String avatar) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.passwordAgain = passwordAgain;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }
}
