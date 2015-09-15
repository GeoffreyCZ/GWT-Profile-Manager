package com.lingoking.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.CreateProfilePresenter;
import com.lingoking.shared.model.Address;
import com.lingoking.shared.model.Profile;
import com.pietschy.gwt.pectin.client.form.binding.FormBinder;
import com.pietschy.gwt.pectin.client.form.validation.binding.ValidationBinder;
import com.pietschy.gwt.pectin.client.form.validation.component.ValidationDisplayPanel;

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
    private String randomString;

    private CreateProfileModel model;

    public CreateProfileView() {
        this(new CreateProfileModel());
    }

    public CreateProfileView(CreateProfileModel model) {

        this.model = model;

        VerticalPanel formUploadPanel = new VerticalPanel();

        formPanel = new FormPanel();
        formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
        formPanel.setMethod(FormPanel.METHOD_POST);
        formPanel.setWidth("100%");


        ValidationDisplayPanel validationMessages = new ValidationDisplayPanel();

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

        formUploadPanel.add(new Label("First name"));
        formUploadPanel.add(firstName);
        formUploadPanel.add(new Label("Last name"));
        formUploadPanel.add(lastName);
        formUploadPanel.add(new Label("Email Address"));
        formUploadPanel.add(emailAddress);
        formUploadPanel.add(new Label("Password"));
        formUploadPanel.add(password);
        formUploadPanel.add(new Label("Password confirmation"));
        formUploadPanel.add(passwordAgain);
        formUploadPanel.add(new Label("Telephone number"));
        formUploadPanel.add(phoneNumber);
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
        formUploadPanel.add(validationMessages);
        formUploadPanel.add(createButton);
        formUploadPanel.add(cancelButton);

        formPanel.setWidget(formUploadPanel);

        initWidget(formPanel);

        FormBinder binder = new FormBinder();
        ValidationBinder validation = new ValidationBinder();

        binder.bind(model.emailAddress).to(emailAddress);
        binder.bind(model.password).to(password);
        binder.bind(model.passwordAgain).to(passwordAgain);
        binder.bind(model.phoneNumber).to(phoneNumber);

        validation.bindValidationOf(model).to(validationMessages);

    }

    public void setProfile(Profile profile)
    {
        model.setProfile(profile);
    }

    public void commit()
    {
        model.commit();
    }

    public boolean validate()
    {
        return model.validate();
    }

    public Profile getProfile() {
        String avatarName;
        Window.alert("Filename: " + uploadAvatarWidget.getFilename());
        if (uploadAvatarWidget.getFilename() != "") {
            avatarName = firstName.getText() + "_" + lastName.getText() + "_" + randomString + ".jpg";
        } else {
            avatarName = "";
        }
        Window.alert("Avatar Name: " + avatarName);
        Address address = new Address(street.getText(),streetNumber.getText(), city.getText(), postcode.getText());
        uploadAvatarWidget.setName(firstName.getText() + "_" + lastName.getText() + ".jpg");
        Profile profile = new Profile(null, firstName.getText(), lastName.getText(), emailAddress.getText(),
                password.getText(), phoneNumber.getText(), address, avatarName);
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