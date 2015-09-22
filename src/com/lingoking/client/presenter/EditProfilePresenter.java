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
import com.lingoking.shared.model.Profile;

public class EditProfilePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getEditButton();
        HasClickHandlers getCancelButton();
        FormPanel getFormPanel();
        Widget asWidget();
        void setProfile(Profile profile);
        Profile getProfile();
        Label getPhoneNumberErrorMessage();
        Label getEmailErrorMessage();
    }

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
                doEdit();
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
        if (validate()) {
            rpcService.editProfile(profileId, profile, new AsyncCallback<Profile>() {
                public void onSuccess(Profile result) {
                    eventBus.fireEvent(new ProfileCreatedEvent());
                }

                public void onFailure(Throwable caught) {
                    Window.alert("Error editing the profile.");
                }
            });
        }
    }

    private boolean validate() {
        boolean valid = true;
        display.getPhoneNumberErrorMessage().setText(null);
        display.getEmailErrorMessage().setText(null);

        if (display.getProfile().getPhoneNumber().equals("")) {
            display.getPhoneNumberErrorMessage().setText("Please enter valid phone number!");
            valid = false;
        }
        if (!display.getProfile().getEmailAddress().matches(CreateProfilePresenter.EMAIL_PATTERN)) {
            display.getEmailErrorMessage().setText("Please enter valid email address!");
            valid = false;
        }
        return valid;
    }

}

