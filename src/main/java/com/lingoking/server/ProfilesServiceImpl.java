package com.lingoking.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.lingoking.client.ProfilesService;
import com.lingoking.client.presenter.CreateProfilePresenter;
import com.lingoking.shared.model.ErrorMessages;
import com.lingoking.shared.model.Profile;
import org.apache.commons.codec.binary.Base64;

@SuppressWarnings("serial")
public class ProfilesServiceImpl extends RemoteServiceServlet implements
        ProfilesService {

    ErrorMessages errorMessages;

    public ProfilesServiceImpl() {
    }

    public ErrorMessages createProfile(Profile profile) {
        errorMessages = new ErrorMessages();
        if (validateCreateProfile(profile)) {
            ConnectionConfiguration.insertIntoDB(profile);
            try {
                ImageResize.createThumbnail(profile);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return errorMessages;
    }

    public ErrorMessages editProfile(String id, Profile profile) {
        errorMessages = new ErrorMessages();
        if (validateEditProfile(profile)) {
            ConnectionConfiguration.editProfileInDB(id, profile);
            try {
                ImageResize.createThumbnail(profile);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return errorMessages;
    }

    public String getNumberOfPages() {
        LoadProperties loadProperties = new LoadProperties();
        double numberOfRows = ConnectionConfiguration.getNumberOfRows();
        double rowsPerPage = Double.parseDouble(loadProperties.get("numberOfProfilesPerPage"));
        double numberOfPages = numberOfRows / rowsPerPage;
        System.out.println("numberofrows: " + numberOfRows + "/" + "rowsPerPage: " + rowsPerPage + " = numberofpages: " + numberOfPages);
        numberOfPages = Math.ceil(numberOfPages);
        String numberOfPagesString;
        if (numberOfPages == (long) numberOfPages) {
            numberOfPagesString = String.format("%d", (long) numberOfPages);
        } else {
            numberOfPagesString =  String.format("%s", numberOfPages);
        }
        return numberOfPagesString;
    }

    public List<Profile> getListOfProfiles(int offset) {
        List<Profile> profiles;
        profiles = ConnectionConfiguration.fetchAllProfilesFromDB(offset);
        for (int i = 0; i < profiles.size(); ++i) {
            if (!profiles.get(i).getAvatar().equals("")) {
                File file = new File(UploadServlet.IMAGES_DIRECTORY + "thumb_" + profiles.get(i).getAvatar());
                profiles.get(i).setImageString(imageToString(file));
            }
        }
        return profiles;
    }



    private String imageToString(File file) {
        String imageDataString = "";
        try {
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            imageDataString = Base64.encodeBase64String(imageData);
            imageInFile.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return imageDataString;
    }

    public Boolean login(Profile profile) {
        String hashedPassword;
        String salt;
        salt = ConnectionConfiguration.getSalt(profile.getEmailAddress());
        hashedPassword = PasswordHash.getPassword(profile.getPassword(), salt);
        profile.setPassword(hashedPassword);
        return ConnectionConfiguration.searchLoginCredentials(profile);
    }

    public Boolean checkEmail(String id, String email) {
        return ConnectionConfiguration.searchInDB(id, email);
    }

    public Boolean deleteProfiles(String id) {
        ConnectionConfiguration.deleteProfilesFromDB(id);
        return true;
    }

    public List<Profile> deleteProfiles(List<String> ids) {
        for (int i = 0; i < ids.size(); ++i) {
            deleteProfiles(ids.get(i));
        }
        return getListOfProfiles(0);
    }

    public Profile fetchProfile(String id) {
        Profile profile;
        profile = ConnectionConfiguration.fetchProfileFromDB(id);
        return profile;
    }

    private boolean validateCreateProfile(Profile profile) {
        errorMessages.setValid(true);
        if (profile.getFirstName().length() > 45) {
            errorMessages.setFirstNameError("Your first name is too long!");
            errorMessages.setValid(false);
        }
        if (profile.getLastName().length() > 45) {
            errorMessages.setLastNameError("Your last name is too long!");
            errorMessages.setValid(false);
        }
        if (!profile.getEmailAddress().matches(CreateProfilePresenter.EMAIL_PATTERN)) {
            errorMessages.setEmailError("Please enter valid email address!");
            errorMessages.setValid(false);
        }
        if (!profile.getPassword().matches(CreateProfilePresenter.PASSWORD_PATTERN)) {
            errorMessages.setPasswordError("Please enter valid password! Password must contain at least " +
                    "1 digit, 1 lowercase letter, 1 uppercase letter and its length must be from 6 to 20 letters.");
            errorMessages.setValid(false);
        }
        if (!profile.getPasswordAgain().matches(CreateProfilePresenter.PASSWORD_PATTERN)) {
           errorMessages.setPasswordAgainError("Please enter valid password confirmation! Password must contain at least " +
                   "1 digit, 1 lowercase letter, 1 uppercase letter and its length must be from 6 to 20 letters.");
            errorMessages.setValid(false);
        }
        if (!profile.getPassword().equals(profile.getPasswordAgain())) {
            errorMessages.setPasswordMismatchError("Passwords don't match!");
            errorMessages.setValid(false);
        }
        if (profile.getPhoneNumber().equals("")) {
            errorMessages.setPhoneNumberError("Please enter phone number!");
            errorMessages.setValid(false);
        }
        if (profile.getPhoneNumber().length() > 20) {
            errorMessages.setPhoneNumberError("Your phone number is too long!");
            errorMessages.setValid(false);
        }
        if (profile.getAddress().getStreet().length() > 45) {
            errorMessages.setStreetError("Your street is too long!");
            errorMessages.setValid(false);
        }
        if (profile.getAddress().getStreetNumber().length() > 10) {
            errorMessages.setStreetNumberError("Your street number is too long!");
            errorMessages.setValid(false);
        }
        if (profile.getAddress().getCity().length() > 45) {
            errorMessages.setCityError("Your city name is too long!");
            errorMessages.setValid(false);
        }
        if (profile.getAddress().getPostcode().length() > 10) {
            errorMessages.setPostcodeError("Your postcode is too long!");
            errorMessages.setValid(false);
        }
        return errorMessages.isValid();
    }

    private boolean validateEditProfile(Profile profile) {
        errorMessages.setValid(true);
        if (profile.getFirstName().length() > 45) {
            errorMessages.setFirstNameError("Your first name is too long!");
            errorMessages.setValid(false);
        }
        if (profile.getLastName().length() > 45) {
            errorMessages.setLastNameError("Your last name is too long!");
            errorMessages.setValid(false);
        }
        if (!profile.getEmailAddress().matches(CreateProfilePresenter.EMAIL_PATTERN)) {
            errorMessages.setEmailError("Please enter valid email address!");
            errorMessages.setValid(false);
        }
        if (profile.getPhoneNumber().equals("")) {
            errorMessages.setPhoneNumberError("Please enter phone number!");
            errorMessages.setValid(false);
        }
        if (profile.getPhoneNumber().length() > 20) {
            errorMessages.setPhoneNumberError("Your phone number is too long!");
            errorMessages.setValid(false);
        }
        if (profile.getAddress().getStreet().length() > 45) {
            errorMessages.setStreetError("Your street is too long!");
            errorMessages.setValid(false);
        }
        if (profile.getAddress().getStreetNumber().length() > 10) {
            errorMessages.setStreetNumberError("Your street number is too long!");
            errorMessages.setValid(false);
        }
        if (profile.getAddress().getCity().length() > 45) {
            errorMessages.setCityError("Your city name is too long!");
            errorMessages.setValid(false);
        }
        if (profile.getAddress().getPostcode().length() > 10) {
            errorMessages.setPostcodeError("Your postcode is too long!");
            errorMessages.setValid(false);
        }
        return errorMessages.isValid();
    }
}
