package com.lingoking.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
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
    private Label emailErrorLabel;
    private Label passwordErrorLabel;
    private Label telephoneErrorLabel;
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
        password = new TextBox();
        passwordAgain = new TextBox();
        phoneNumber = new TextBox();
        street = new TextBox();
        streetNumber = new TextBox();
        city = new TextBox();
        postcode = new TextBox();
        uploadAvatarWidget = new FileUpload();
        createButton = new Button("Create");
        cancelButton = new Button("Cancel");

        emailErrorLabel = new Label();
        emailErrorLabel.setText("Enter valid e-mail address");
        emailErrorLabel.setVisible(false);

        passwordErrorLabel = new Label();
        passwordErrorLabel.setText("Passwords need to match");
        passwordErrorLabel.setVisible(false);

        telephoneErrorLabel = new Label();
        telephoneErrorLabel.setText("Telephone number has to be entered");
        telephoneErrorLabel.setVisible(false);

        formUploadPanel.add(new Label("First name"));
        formUploadPanel.add(firstName);
        formUploadPanel.add(new Label("Last name"));
        formUploadPanel.add(lastName);
        formUploadPanel.add(new Label("Email Address *"));
        formUploadPanel.add(emailAddress);
        formUploadPanel.add(emailErrorLabel);
        formUploadPanel.add(new Label("Password *"));
        formUploadPanel.add(password);
        formUploadPanel.add(new Label("Password confirmation *"));
        formUploadPanel.add(passwordAgain);
        formUploadPanel.add(passwordErrorLabel);
        formUploadPanel.add(new Label("Telephone number *"));
        formUploadPanel.add(phoneNumber);
        formUploadPanel.add(telephoneErrorLabel);
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

    public Profile getData() {
        String avatarName = firstName.getText() + "_" + lastName.getText() + "_" + randomString + ".jpg";
        Window.alert("Avatar Name: " + avatarName);
        Address address = new Address(street.getText(),streetNumber.getText(), city.getText(), postcode.getText());
        uploadAvatarWidget.setName(firstName.getText() + "_" + lastName.getText() + ".jpg");
        Profile profile = new Profile(null, firstName.getText(), lastName.getText(), emailAddress.getText(),
                password.getText(), passwordAgain.getText(), phoneNumber.getText(), address, avatarName);
        return profile;
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