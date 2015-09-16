package com.lingoking.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.LoginPresenter;
import com.lingoking.shared.model.Profile;

public class LoginView extends Composite implements LoginPresenter.Display {

    private final Label emailAddress;
    private final TextBox emailAddressTextBox;
    private final Label password;
    private final PasswordTextBox passwordTextBox;
    private final Button loginButton;

    public LoginView() {

        VerticalPanel loginPanel = new VerticalPanel();
        initWidget(loginPanel);

        Label titleLabel = new Label("Sign in to proceed:");
        loginPanel.add(titleLabel);

        FlexTable loginTable = new FlexTable();

        emailAddress = new Label("E-mail address:");
        loginTable.setWidget(1, 0, emailAddress);

        emailAddressTextBox = new TextBox();
        loginTable.setWidget(1, 1, emailAddressTextBox);

        password = new Label("Password:");
        loginTable.setWidget(2, 0, password);

        passwordTextBox = new PasswordTextBox();
        loginTable.setWidget(2, 1, passwordTextBox);

        loginButton = new Button("Sign in");
        loginTable.setWidget(3, 0, loginButton);

        loginPanel.add(loginTable);
    }

    public Profile getProfile() {
        Profile profile = new Profile();
        return profile;
    }

    public HasClickHandlers getLoginButton() {
        return loginButton;
    }

    public Widget asWidget() {
        return this;
    }

}



