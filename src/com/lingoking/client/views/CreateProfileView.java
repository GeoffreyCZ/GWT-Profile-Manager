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
    private final Label firstNameErrorMessage;
    private final Label lastNameErrorMessage;
    private final Label emailErrorMessage;
    private final Label passwordErrorMessage;
    private final Label passwordAgainErrorMessage;
    private final Label passwordMismatchErrorMessage;
    private final Label phoneNumberErrorMessage;
    private final Label streetErrorMessage;
    private final Label streetNumberErrorMessage;
    private final Label cityErrorMessage;
    private final Label postcodeErrorMessage;

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

        firstNameErrorMessage = new Label();
        lastNameErrorMessage = new Label();
        emailErrorMessage = new Label();
        passwordErrorMessage = new Label();
        passwordAgainErrorMessage = new Label();
        passwordMismatchErrorMessage = new Label();
        phoneNumberErrorMessage = new Label();
        streetErrorMessage = new Label();
        streetNumberErrorMessage = new Label();
        cityErrorMessage = new Label();
        postcodeErrorMessage = new Label();

        firstNameErrorMessage.addStyleName("errorMessage");
        lastNameErrorMessage.addStyleName("errorMessage");
        emailErrorMessage.addStyleName("errorMessage");
        passwordErrorMessage.addStyleName("errorMessage");
        passwordAgainErrorMessage.addStyleName("errorMessage");
        passwordMismatchErrorMessage.addStyleName("errorMessage");
        phoneNumberErrorMessage.addStyleName("errorMessage");
        streetErrorMessage.addStyleName("errorMessage");
        streetNumberErrorMessage.addStyleName("errorMessage");
        cityErrorMessage.addStyleName("errorMessage");
        postcodeErrorMessage.addStyleName("errorMessage");

        uploadAvatarWidget = new FileUpload();
        uploadAvatarWidget.getElement().setAttribute("accept", "image/*");

        createButton = new Button("Create");
        cancelButton = new Button("Cancel");

        formUploadPanel.add(new Label("First name"));
        formUploadPanel.add(firstName);
        formUploadPanel.add(firstNameErrorMessage);
        formUploadPanel.add(new Label("Last name"));
        formUploadPanel.add(lastName);
        formUploadPanel.add(lastNameErrorMessage);
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
        formUploadPanel.add(streetErrorMessage);
        formUploadPanel.add(new Label("Street Number"));
        formUploadPanel.add(streetNumber);
        formUploadPanel.add(streetNumberErrorMessage);
        formUploadPanel.add(new Label("City"));
        formUploadPanel.add(city);
        formUploadPanel.add(cityErrorMessage);
        formUploadPanel.add(new Label("Postcode"));
        formUploadPanel.add(postcode);
        formUploadPanel.add(postcodeErrorMessage);
        formUploadPanel.add(new Label("Profile picture"));
        formUploadPanel.add(uploadAvatarWidget);
        formUploadPanel.add(createButton);
        formUploadPanel.add(cancelButton);

        formPanel.setWidget(formUploadPanel);

        initWidget(formPanel);
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
        Profile profile = new Profile(null, firstName.getText(), lastName.getText(), emailAddress.getText(),
                password.getText(), passwordAgain.getText(), phoneNumber.getText(), address, avatarName);
        return profile;
    }

    public Label getFirstNameErrorMessage() {
        return firstNameErrorMessage;
    }

    public Label getLastNameErrorMessage() {
        return lastNameErrorMessage;
    }

    public Label getEmailErrorMessage() {
        return emailErrorMessage;
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

    public Label getStreetErrorMessage() {
        return streetErrorMessage;
    }

    public Label getStreetNumberErrorMessage() {
        return streetNumberErrorMessage;
    }

    public Label getCityErrorMessage() {
        return cityErrorMessage;
    }

    public Label getPostcodeErrorMessage() {
        return postcodeErrorMessage;
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