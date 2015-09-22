package com.lingoking.shared.model;

import java.io.Serializable;

public class ErrorMessages implements Serializable {

    private boolean valid;
    private String firstNameError;
    private String lastNameError;
    private String emailError;
    private String passwordError;
    private String passwordAgainError;
    private String passwordMismatchError;
    private String phoneNumberError;
    private String streetError;
    private String streetNumberError;
    private String cityError;
    private String postcodeError;

    public ErrorMessages() {
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getFirstNameError() {
        return firstNameError;
    }

    public void setFirstNameError(String firstNameError) {
        this.firstNameError = firstNameError;
    }

    public String getLastNameError() {
        return lastNameError;
    }

    public void setLastNameError(String lastNameError) {
        this.lastNameError = lastNameError;
    }

    public String getEmailError() {
        return emailError;
    }

    public void setEmailError(String emailError) {
        this.emailError = emailError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPasswordAgainError() {
        return passwordAgainError;
    }

    public void setPasswordAgainError(String passwordAgainError) {
        this.passwordAgainError = passwordAgainError;
    }

    public String getPasswordMismatchError() {
        return passwordMismatchError;
    }

    public void setPasswordMismatchError(String passwordMismatchError) {
        this.passwordMismatchError = passwordMismatchError;
    }

    public String getPhoneNumberError() {
        return phoneNumberError;
    }

    public void setPhoneNumberError(String phoneNumberError) {
        this.phoneNumberError = phoneNumberError;
    }

    public String getStreetError() {
        return streetError;
    }

    public void setStreetError(String streetError) {
        this.streetError = streetError;
    }

    public String getStreetNumberError() {
        return streetNumberError;
    }

    public void setStreetNumberError(String streetNumberError) {
        this.streetNumberError = streetNumberError;
    }

    public String getCityError() {
        return cityError;
    }

    public void setCityError(String cityError) {
        this.cityError = cityError;
    }

    public String getPostcodeError() {
        return postcodeError;
    }

    public void setPostcodeError(String postcodeError) {
        this.postcodeError = postcodeError;
    }
}
