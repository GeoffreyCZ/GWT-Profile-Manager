package com.lingoking.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.EditProfilePresenter;
import com.lingoking.shared.model.Address;
import com.lingoking.shared.model.Profile;

/**
 * Created by Michal on 1. 9. 2015.
 */
public class EditProfileView extends Composite implements EditProfilePresenter.Display {
    private final TextBox firstName;
    private final TextBox lastName;
    private final TextBox emailAddress;
    private final TextBox password;
    private final TextBox passwordAgain;
    private final TextBox phoneNumber;
    private final TextBox street;
    private final TextBox streetNumber;
    private final TextBox city;;
    private final TextBox postcode;
    private final Button avatar;
    private final FlexTable detailsTable;
    private final Button editButton;
    private final Button cancelButton;

    public EditProfileView() {
        DecoratorPanel contentDetailsDecorator = new DecoratorPanel();
        contentDetailsDecorator.setWidth("18em");
        initWidget(contentDetailsDecorator);

        VerticalPanel contentDetailsPanel = new VerticalPanel();
        contentDetailsPanel.setWidth("100%");

        detailsTable = new FlexTable();
        detailsTable.setCellSpacing(0);
        detailsTable.setWidth("100%");
        detailsTable.addStyleName("profiles-ListContainer");
        detailsTable.getColumnFormatter().addStyleName(1, "add-profile-input");
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
        avatar = new Button();
        initDetailsTable();
        contentDetailsPanel.add(detailsTable);

        HorizontalPanel menuPanel = new HorizontalPanel();
        editButton = new Button("Finish");
        cancelButton = new Button("Cancel");
        menuPanel.add(editButton);
        menuPanel.add(cancelButton);
        contentDetailsPanel.add(menuPanel);
        contentDetailsDecorator.add(contentDetailsPanel);
    }

    private void initDetailsTable() {
        detailsTable.setWidget(0, 0, new Label("First name"));
        detailsTable.setWidget(0, 1, firstName);
        detailsTable.setWidget(1, 0, new Label("Last name"));
        detailsTable.setWidget(1, 1, lastName);
        detailsTable.setWidget(2, 0, new Label("Email Address"));
        detailsTable.setWidget(2, 1, emailAddress);
        detailsTable.setWidget(3, 0, new Label("Password"));
        detailsTable.setWidget(3, 1, password);
        detailsTable.setWidget(4, 0, new Label("Password confirmation"));
        detailsTable.setWidget(4, 1, passwordAgain);
        detailsTable.setWidget(5, 0, new Label("Telephone number"));
        detailsTable.setWidget(5, 1, phoneNumber);
        detailsTable.setWidget(6, 0, new Label("Street"));
        detailsTable.setWidget(6, 1, street);
        detailsTable.setWidget(6, 2, new Label("Street Number"));
        detailsTable.setWidget(6, 3, streetNumber);
        detailsTable.setWidget(7, 0, new Label("City"));
        detailsTable.setWidget(7, 1, city);
        detailsTable.setWidget(7, 2, new Label("Postcode"));
        detailsTable.setWidget(7, 3, postcode);
        detailsTable.setWidget(8, 0, new Label("Avatar"));
        detailsTable.setWidget(8, 1, avatar);
        firstName.setFocus(true);
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
//        avatar.setText(profile.getAvatar());
    }

    public Profile getProfile() {
        Address address = new Address(street.getText(),streetNumber.getText(), city.getText(), postcode.getText());
        Profile profile = new Profile(null, firstName.getText(), lastName.getText(), emailAddress.getText(),
                password.getText(), phoneNumber.getText(), address, avatar.getText());
        return profile;
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