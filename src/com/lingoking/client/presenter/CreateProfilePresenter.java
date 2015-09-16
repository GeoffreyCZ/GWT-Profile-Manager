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
import com.lingoking.shared.model.Profile;

public class CreateProfilePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getCreateButton();
        HasClickHandlers getCancelButton();
        Profile getProfile();
        Widget asWidget();
        FormPanel getFormPanel();
        Label getPasswordErrorMessage();
        Label getPasswordAgainErrorMessage();
        Label getPasswordMismatchErrorMessage();
        Label getPhoneNumberErrorMessage();
        Label getEmailErrorMessage();
    }

    public static final String EMAIL_PATTERN =  "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,6})$";

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
//                Window.alert("bind: " + action);
                display.getFormPanel().submit();
            }
        });

        this.display.getFormPanel().addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                doCreate();
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
        if (validate()) {
            rpcService.createProfile(profile, new AsyncCallback<Profile>() {
                public void onSuccess(Profile result) {
                    eventBus.fireEvent(new ProfileCreatedEvent(result));
                }

                public void onFailure(Throwable caught) {
                    Window.alert("Error creating profile.");
                }
            });
        }

    }

    private boolean validate() {
        boolean valid = true;
        display.getPasswordErrorMessage().setText(null);
        display.getPasswordAgainErrorMessage().setText(null);
        display.getPasswordMismatchErrorMessage().setText(null);
        display.getPhoneNumberErrorMessage().setText(null);
        display.getEmailErrorMessage().setText(null);

        if (display.getProfile().getPassword().equals("")) {
            display.getPasswordErrorMessage().setText("Please enter your password!");
            valid = false;
        }
        if (display.getProfile().getPasswordAgain().equals("")) {
            display.getPasswordAgainErrorMessage().setText("Please confirm your password!");
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
        if (!display.getProfile().getEmailAddress().matches(EMAIL_PATTERN)) {
            display.getEmailErrorMessage().setText("Please enter valid email address!");
            valid = false;
        }
        return valid;
    }


}