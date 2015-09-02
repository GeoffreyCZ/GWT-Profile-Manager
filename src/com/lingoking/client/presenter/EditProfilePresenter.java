package com.lingoking.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.CreateProfileCancelledEvent;
import com.lingoking.client.events.ProfileCreatedEvent;
import com.lingoking.shared.model.Profile;

public class EditProfilePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getEditButton();
        HasClickHandlers getCancelButton();
//        HasValue<String> getFirstName();
//        HasValue<String> getLastName();
//        HasValue<String> getEmail();
        Widget asWidget();
        void setData(Profile profile);
        Profile getData();
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
                display.setData(profile);
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error fetching profiles");
            }
        });
    }

    private void doEdit() {
        profile = display.getData();

        rpcService.editProfile(profileId, profile, new AsyncCallback<Profile>() {
            public void onSuccess(Profile result) {
                eventBus.fireEvent(new ProfileCreatedEvent(result));
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error editing the profile.");
            }
        });
    }


}