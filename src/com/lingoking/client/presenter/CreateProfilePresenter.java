package com.lingoking.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.CreateProfileCancelledEvent;
import com.lingoking.client.events.ProfileCreatedEvent;
import com.lingoking.shared.model.ErrorMessages;
import com.lingoking.shared.model.Profile;

public class CreateProfilePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getCreateButton();
        HasClickHandlers getCancelButton();
        Profile getProfile();
        Widget asWidget();
        FormPanel getFormPanel();
        Label getFirstNameErrorMessage();
        Label getLastNameErrorMessage();
        Label getEmailErrorMessage();
        Label getPasswordErrorMessage();
        Label getPasswordAgainErrorMessage();
        Label getPasswordMismatchErrorMessage();
        Label getPhoneNumberErrorMessage();
        Label getStreetErrorMessage();
        Label getStreetNumberErrorMessage();
        Label getCityErrorMessage();
        Label getPostcodeErrorMessage();
    }

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$";
    public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

    private boolean valid;
    private Profile profile;
    private final ProfilesServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;

    public CreateProfilePresenter(ProfilesServiceAsync rpcService, HandlerManager eventBus, Display display) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.profile = new Profile();
        this.display = display;
        bind();
    }

    public void bind() {
        this.display.getCreateButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String action;
                if (!display.getProfile().getAvatar().equals("")) {
                    action = "UploadServlet?profile_name=" + display.getProfile().getAvatar();
                } else {
                    action = null;
                }
                display.getFormPanel().setAction(action);
                display.getFormPanel().submit();
            }
        });

        this.display.getFormPanel().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                AsyncCallback<Boolean> onComplete = new AsyncCallback<Boolean>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        Window.alert("Validation error!");
                    }

                    @Override
                    public void onSuccess(Boolean valid) {
                        if (valid) {
                            doCreate();
                        }
                    }
                };

                validate(onComplete);
            }
        });

        this.display.getCancelButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new CreateProfileCancelledEvent());
            }
        });
    }

    public void go(final HasWidgets container) {
        container.clear();
        container.add(display.asWidget());
    }

    private void doCreate() {
        profile = display.getProfile();

        rpcService.createProfile(profile, new AsyncCallback<ErrorMessages>() {
            public void onSuccess(ErrorMessages result) {
                if (result.isValid()) {
                    eventBus.fireEvent(new ProfileCreatedEvent());
                } else {
                    setErrorMessages(result);
                }
            }
            public void onFailure(Throwable caught) {
                Window.alert("Error creating profile.");
            }
        });

    }

    private void setErrorMessages(ErrorMessages errorMessages) {
        if (!errorMessages.getFirstNameError().equals(null)) {
            display.getFirstNameErrorMessage().setText(errorMessages.getFirstNameError());
        }
        if (!errorMessages.getLastNameError().equals(null)) {
            display.getLastNameErrorMessage().setText(errorMessages.getLastNameError());
        }
        if (!errorMessages.getEmailError().equals(null)) {
            display.getEmailErrorMessage().setText(errorMessages.getEmailError());
        }
        if (!errorMessages.getPasswordError().equals(null)) {
            display.getPasswordErrorMessage().setText(errorMessages.getPasswordError());
        }
        if (!errorMessages.getPasswordAgainError().equals(null)) {
            display.getPasswordAgainErrorMessage().setText(errorMessages.getPasswordAgainError());
        }
        if (!errorMessages.getPasswordMismatchError().equals(null)) {
            display.getPasswordMismatchErrorMessage().setText(errorMessages.getPasswordMismatchError());
        }
        if (!errorMessages.getPhoneNumberError().equals(null)) {
            display.getPhoneNumberErrorMessage().setText(errorMessages.getPhoneNumberError());
        }
        if (!errorMessages.getStreetError().equals(null)) {
            display.getStreetErrorMessage().setText(errorMessages.getStreetError());
        }
        if (!errorMessages.getStreetNumberError().equals(null)) {
            display.getStreetNumberErrorMessage().setText(errorMessages.getStreetNumberError());
        }
        if (!errorMessages.getCityError().equals(null)) {
            display.getCityErrorMessage().setText(errorMessages.getCityError());
        }
        if (!errorMessages.getPostcodeError().equals(null)) {
            display.getPostcodeErrorMessage().setText(errorMessages.getPostcodeError());
        }
    }


    private void validate(final AsyncCallback<Boolean> onComplete) {
        valid = true;

        display.getFirstNameErrorMessage().setText(null);
        display.getLastNameErrorMessage().setText(null);
        display.getEmailErrorMessage().setText(null);
        display.getPasswordErrorMessage().setText(null);
        display.getPasswordAgainErrorMessage().setText(null);
        display.getPasswordMismatchErrorMessage().setText(null);
        display.getPhoneNumberErrorMessage().setText(null);
        display.getStreetErrorMessage().setText(null);
        display.getStreetNumberErrorMessage().setText(null);
        display.getCityErrorMessage().setText(null);
        display.getPostcodeErrorMessage().setText(null);

        if (display.getProfile().getFirstName().length() > 45) {
            display.getFirstNameErrorMessage().setText("Your first name is too long!");
            valid = false;
        }
        if (display.getProfile().getLastName().length() > 45) {
            display.getLastNameErrorMessage().setText("Your last name is too long!");
            valid = false;
        }
        if (!display.getProfile().getEmailAddress().matches(EMAIL_PATTERN)) {
            display.getEmailErrorMessage().setText("Please enter valid email address!");
            valid = false;
        }
        if (!display.getProfile().getPassword().matches(PASSWORD_PATTERN)) {
            display.getPasswordErrorMessage().setText("Please enter valid password! Password must contain at least " +
                    "1 digit, 1 lowercase letter, 1 uppercase letter and its length must be from 6 to 20 letters.");
            valid = false;
        }
        if (!display.getProfile().getPasswordAgain().matches(PASSWORD_PATTERN)) {
            display.getPasswordAgainErrorMessage().setText("Please enter valid password confirmation! Password must contain at least " +
                    "1 digit, 1 lowercase letter, 1 uppercase letter and its length must be from 6 to 20 letters.");
            valid = false;
        }
        if (!display.getProfile().getPassword().equals(display.getProfile().getPasswordAgain())) {
            display.getPasswordMismatchErrorMessage().setText("Passwords don't match!");
            valid = false;
        }
        if (display.getProfile().getPhoneNumber().equals("")) {
            display.getPhoneNumberErrorMessage().setText("Please enter phone number!");
            valid = false;
        }
        if (display.getProfile().getPhoneNumber().length() > 20) {
            display.getPhoneNumberErrorMessage().setText("Your phone number is too long!");
            valid = false;
        }
        if (display.getProfile().getAddress().getStreet().length() > 45) {
            display.getStreetErrorMessage().setText("Your street is too long!");
            valid = false;
        }
        if (display.getProfile().getAddress().getStreetNumber().length() > 10) {
            display.getStreetNumberErrorMessage().setText("Your street number is too long!");
            valid = false;
        }
        if (display.getProfile().getAddress().getCity().length() > 45) {
            display.getCityErrorMessage().setText("Your city name is too long!");
            valid = false;
        }
        if (display.getProfile().getAddress().getPostcode().length() > 10) {
            display.getPostcodeErrorMessage().setText("Your postcode is too long!");
            valid = false;
        }

        rpcService.checkEmail(display.getProfile().getEmailAddress(), new AsyncCallback<Boolean>() {
            public void onSuccess(Boolean result) {
                if (result) {
                    display.getEmailErrorMessage().setText("Email is already registered!");
                    valid = false;
                }
                onComplete.onSuccess(valid);
            }
            public void onFailure(Throwable caught) {
                Window.alert("Error searching in database.");
                onComplete.onFailure(caught);
            }
        });
    }


}