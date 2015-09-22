package com.lingoking.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.CreateProfileCancelledEvent;
import com.lingoking.client.events.ProfileCreatedEvent;
import com.lingoking.shared.model.ErrorMessages;
import com.lingoking.shared.model.Profile;

public class EditProfilePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getEditButton();
        HasClickHandlers getCancelButton();
        FormPanel getFormPanel();
        Widget asWidget();
        void setProfile(Profile profile);
        Profile getProfile();
        Label getFirstNameErrorMessage();
        Label getLastNameErrorMessage();
        Label getEmailErrorMessage();
        Label getPhoneNumberErrorMessage();
        Label getStreetErrorMessage();
        Label getStreetNumberErrorMessage();
        Label getCityErrorMessage();
        Label getPostcodeErrorMessage();
    }

    private boolean valid;
    private Profile profile;
    private final ProfilesServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;
    private final String profileId;

    public EditProfilePresenter(ProfilesServiceAsync rpcService, HandlerManager eventBus, Display display, String profileId) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.profile = new Profile();
        this.display = display;
        this.profileId = profileId;
        bind();
    }

    public void bind() {
        this.display.getEditButton().addClickHandler(new ClickHandler() {
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
                            doEdit();
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
        fetchProfile(profileId);
    }

    private void fetchProfile(String id) {
        rpcService.fetchProfile(id, new AsyncCallback<Profile>() {
            public void onSuccess(Profile result) {
                Profile profile;
                profile = result;
                display.setProfile(profile);
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error fetching profiles");
            }
        });
    }

    private void doEdit() {
        profile = display.getProfile();

        rpcService.editProfile(profileId, profile, new AsyncCallback<ErrorMessages>() {
            public void onSuccess(ErrorMessages result) {
                if (result.isValid()) {
                    eventBus.fireEvent(new ProfileCreatedEvent());
                } else {
                    setErrorMessages(result);
                }
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error editing the profile.");
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

        if (display.getProfile().getFirstName().length() > 45) {
            display.getFirstNameErrorMessage().setText("Your first name is too long!");
            valid = false;
        }
        if (display.getProfile().getLastName().length() > 45) {
            display.getLastNameErrorMessage().setText("Your last name is too long!");
            valid = false;
        }
        if (!display.getProfile().getEmailAddress().matches(CreateProfilePresenter.EMAIL_PATTERN)) {
            display.getEmailErrorMessage().setText("Please enter valid email address!");
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

        rpcService.checkEmail(profileId, display.getProfile().getEmailAddress(), new AsyncCallback<Boolean>() {
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

