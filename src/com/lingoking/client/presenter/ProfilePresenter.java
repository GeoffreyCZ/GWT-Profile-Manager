package com.lingoking.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.CreateProfileCancelledEvent;
import com.lingoking.client.events.EditProfileEvent;
import com.lingoking.client.events.ProfileCreatedEvent;
import com.lingoking.client.events.ShowProfileListEvent;
import com.lingoking.shared.model.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michal on 31. 8. 2015.
 */
public class ProfilePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getEditButton();
        HasClickHandlers getBackButton();
        HasClickHandlers getDeleteButton();
        void setData(Profile profile);
        Widget asWidget();
    }

    private Profile profile;
    private final ProfilesServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;
    private final String profileId;

    public ProfilePresenter(ProfilesServiceAsync rpcService, HandlerManager eventBus, Display display, String profileId) {
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
                History.newItem("profile=" + profileId);
                eventBus.fireEvent(new EditProfileEvent());
            }

        });

        this.display.getBackButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new ShowProfileListEvent());
            }
        });

        this.display.getDeleteButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                ArrayList<String> al = new ArrayList<>();
                al.add(profileId);
                deleteSelectedProfile(al);
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
//                Window.alert("fetch profile");
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error fetching profiles");
            }
        });
    }

    private void deleteSelectedProfile(ArrayList<String> id) {
        rpcService.deleteProfiles(id, new AsyncCallback<ArrayList<Profile>>() {
            public void onSuccess(ArrayList<Profile> result) {
                eventBus.fireEvent(new ShowProfileListEvent());
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error deleting selected profile");
            }
        });
    }
}
