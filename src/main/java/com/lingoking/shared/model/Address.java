package com.lingoking.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

public class Address implements Serializable {
    private String street;
    private String streetNumber;
    private String city;
    private String postcode;

    public Address(){
    }

    public Address(String street, String streetNumber, String city, String postcode) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.postcode = postcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
