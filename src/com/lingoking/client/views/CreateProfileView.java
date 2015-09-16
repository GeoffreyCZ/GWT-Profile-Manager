package com.lingoking.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.CreateProfilePresenter;
import com.lingoking.shared.model.Address;
import com.lingoking.shared.model.Profile;

import java.util.Random;

public class CreateProfileView extends Composite implements CreateProfilePresenter.Display {
    private final TextBox firstName;
    private final TextBox lastName;
    private final TextBox emailAddress;
    private final TextBox password;
    private final TextBox passwordAgain;
    private final TextBox phoneNumber;
    private final TextBox street;
    private final TextBox streetNumber;
    private final TextBox city;
    private final TextBox postcode;
    private final FileUpload uploadAvatarWidget;
    private final Button createButton;
    private final Button cancelButton;
    private final FormPanel formPanel;
    private final Label passwordErrorMessage;
    private final Label passwordAgainErrorMessage;
    private final Label passwordMismatchErrorMessage;
    private final Label phoneNumberErrorMessage;
    private final Label emailErrorMessage;
    private String randomString;

    public CreateProfileView() {

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
        password = new PasswordTextBox();
        passwordAgain = new PasswordTextBox();
        phoneNumber = new TextBox();
        street = new TextBox();
        streetNumber = new TextBox();
        city = new TextBox();
        postcode = new TextBox();

        passwordErrorMessage = new Label();
        passwordAgainErrorMessage = new Label();
        passwordMismatchErrorMessage = new Label();
        phoneNumberErrorMessage = new Label();
        emailErrorMessage = new Label();

        uploadAvatarWidget = new FileUpload();
        createButton = new Button("Create");
        cancelButton = new Button("Cancel");

        formUploadPanel.add(new Label("First name"));
        formUploadPanel.add(firstName);
        formUploadPanel.add(new Label("Last name"));
        formUploadPanel.add(lastName);
        formUploadPanel.add(new Label("Email Address"));
        formUploadPanel.add(emailAddress);
        formUploadPanel.add(emailErrorMessage);
        formUploadPanel.add(new Label("Password"));
        formUploadPanel.add(password);
        formUploadPanel.add(passwordErrorMessage);
        formUploadPanel.add(new Label("Password confirmation"));
        formUploadPanel.add(passwordAgain);
        formUploadPanel.add(passwordAgainErrorMessage);
        formUploadPanel.add(passwordMismatchErrorMessage);
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


        formUploadPanel.add(createButton);
        formUploadPanel.add(cancelButton);

        formPanel.setWidget(formUploadPanel);

        initWidget(formPanel);
    }

    public Profile getProfile() {
        String avatarName;
//        Window.alert("Filename: " + uploadAvatarWidget.getFilename());
        if (uploadAvatarWidget.getFilename() != "") {
            avatarName = firstName.getText() + "_" + lastName.getText() + "_" + randomString + ".jpg";
        } else {
            avatarName = "";
        }
//        Window.alert("Avatar Name: " + avatarName);
        Address address = new Address(street.getText(),streetNumber.getText(), city.getText(), postcode.getText());
        uploadAvatarWidget.setName(firstName.getText() + "_" + lastName.getText() + ".jpg");
        Profile profile = new Profile(null, firstName.getText(), lastName.getText(), emailAddress.getText(),
                password.getText(), passwordAgain.getText(), phoneNumber.getText(), address, avatarName);
        return profile;
    }

    public Label getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public Label getPasswordAgainErrorMessage() {
        return passwordAgainErrorMessage;
    }

    public Label getPasswordMismatchErrorMessage() {
        return passwordMismatchErrorMessage;
    }

    public Label getPhoneNumberErrorMessage() {
        return phoneNumberErrorMessage;
    }

    public Label getEmailErrorMessage() {
        return emailErrorMessage;
    }

    public HasClickHandlers getCreateButton() {
        return createButton;
    }

    public FormPanel getFormPanel() {
        return formPanel;
    }

    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }

    public Widget asWidget() {
        return this;
    }
}