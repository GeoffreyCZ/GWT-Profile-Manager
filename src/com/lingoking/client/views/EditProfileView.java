package com.lingoking.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.EditProfilePresenter;
import com.lingoking.shared.model.Address;
import com.lingoking.shared.model.Profile;

import java.util.Random;

/**
 * Created by Michal on 1. 9. 2015.
 */
public class EditProfileView extends Composite implements EditProfilePresenter.Display {
    private final TextBox firstName;
    private final TextBox lastName;
    private final TextBox emailAddress;
    private final TextBox phoneNumber;
    private final TextBox street;
    private final TextBox streetNumber;
    private final TextBox city;
    private final TextBox postcode;
    private final FileUpload uploadAvatarWidget;
    private final Button editButton;
    private final Button cancelButton;
    private String randomString;
    private final FormPanel formPanel;
    private final Label phoneNumberErrorMessage;
    private final Label emailErrorMessage;

    public EditProfileView() {

        VerticalPanel formUploadPanel = new VerticalPanel();

        formPanel = new FormPanel();
        formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
        formPanel.setMethod(FormPanel.METHOD_POST);
        formPanel.setWidth("100%");

        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0 ; i < 5; i++) {
            stringBuilder.append('a' + random.nextInt(26));
        }
        randomString = stringBuilder.toString();

        firstName = new TextBox();
        lastName = new TextBox();
        emailAddress = new TextBox();
        phoneNumber = new TextBox();
        street = new TextBox();
        streetNumber = new TextBox();
        city = new TextBox();
        postcode = new TextBox();
        uploadAvatarWidget = new FileUpload();
        phoneNumberErrorMessage = new Label();
        emailErrorMessage = new Label();
        editButton = new Button("Finish");
        cancelButton = new Button("Cancel");

        formUploadPanel.add(new Label("First name"));
        formUploadPanel.add(firstName);
        formUploadPanel.add(new Label("Last name"));
        formUploadPanel.add(lastName);
        formUploadPanel.add(new Label("Email Address"));
        formUploadPanel.add(emailAddress);
        formUploadPanel.add(emailErrorMessage);
        formUploadPanel.add(new Label("Telephone number"));
        formUploadPanel.add(phoneNumber);
        formUploadPanel.add(phoneNumberErrorMessage);
        formUploadPanel.add(new Label("Street"));
        formUploadPanel.add(street);
        formUploadPanel.add(new Label("Street Number"));
        formUploadPanel.add(streetNumber);
        formUploadPanel.add(new Label("City"));
        formUploadPanel.add(city);
        formUploadPanel.add(new Label("Postcode"));
        formUploadPanel.add(postcode);
        formUploadPanel.add(new Label("Profile picture"));
        formUploadPanel.add(uploadAvatarWidget);
        formUploadPanel.add(editButton);
        formUploadPanel.add(cancelButton);

        formPanel.setWidget(formUploadPanel);

        initWidget(formPanel);
    }

    public void setProfile(Profile profile) {
        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        emailAddress.setText(profile.getEmailAddress());
        phoneNumber.setText(profile.getPhoneNumber());
        street.setText(profile.getAddress().getStreet());
        streetNumber.setText(profile.getAddress().getStreetNumber());
        city.setText(profile.getAddress().getCity());
        postcode.setText(profile.getAddress().getPostcode());
    }

    public Profile getProfile() {
        String avatarName;
        if (!uploadAvatarWidget.getFilename().equals("")) {
            avatarName = firstName.getText() + "_" + lastName.getText() + "_" + randomString + ".jpg";
        } else {
            avatarName = "";
        }
        Address address = new Address(street.getText(),streetNumber.getText(), city.getText(), postcode.getText());
        uploadAvatarWidget.setName(firstName.getText() + "_" + lastName.getText() + ".jpg");
        Profile profile = new Profile(firstName.getText(), lastName.getText(), emailAddress.getText(), phoneNumber.getText(), address, avatarName);
        return profile;
    }

    public Label getPhoneNumberErrorMessage() {
        return phoneNumberErrorMessage;
    }

    public Label getEmailErrorMessage() {
        return emailErrorMessage;
    }

    public FormPanel getFormPanel() {
        return formPanel;
    }

    public HasClickHandlers getEditButton() {
        return editButton;
    }

    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }

    public Widget asWidget() {
        return this;
    }
}