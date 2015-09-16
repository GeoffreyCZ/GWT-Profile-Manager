package com.lingoking.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lingoking.client.events.*;
import com.lingoking.client.presenter.*;
import com.lingoking.client.views.*;

public class AppController implements Presenter, ValueChangeHandler<String> {
    private final HandlerManager eventBus;
    private final ProfilesServiceAsync rpcService;
    private HasWidgets container;

    public AppController(ProfilesServiceAsync rpcService, HandlerManager eventBus) {
        this.eventBus = eventBus;
        this.rpcService = rpcService;
        bind();
    }

    private void bind() {
        History.addValueChangeHandler(this);

        eventBus.addHandler(CreateProfileEvent.TYPE,
                new CreateProfileEventHandler() {
                    public void onCreateProfile(CreateProfileEvent event) {
                        doCreateProfile();
                    }
                });

        eventBus.addHandler(EditProfileEvent.TYPE,
                new EditProfileEventHandler() {
                    public void onEditProfile(EditProfileEvent event) {
                        doEditProfile();
                    }
                });

        eventBus.addHandler(CreateProfileCancelledEvent.TYPE,
                new CreateProfileCancelledEventHandler() {
                    public void onCreateProfileCancelled(CreateProfileCancelledEvent event) {
                        doEditProfileCancelled();
                    }
                });

        eventBus.addHandler(EditProfileCancelledEvent.TYPE,
                new EditProfileCancelledEventHandler() {
                    public void onEditProfileCancelled(EditProfileCancelledEvent event) {
                        doEditProfileCancelled();
                    }
                });

        eventBus.addHandler(ShowProfileEvent.TYPE, new ShowProfileEventHandler() {
            @Override
            public void onShowProfile(ShowProfileEvent event) {
                doShowProfile();
            }
        });

        eventBus.addHandler(ProfileCreatedEvent.TYPE,
                new ProfileCreatedEventHandler() {
                    public void onProfileCreated(ProfileCreatedEvent event) {
                        doProfileUpdated();
                    }
                });

        eventBus.addHandler(ShowProfileListEvent.TYPE, new ShowProfileListEventHandler() {
            @Override
            public void onShowProfileList(ShowProfileListEvent event) {
                doShowProfileList();
            }
        });
    }

    private void doCreateProfile() {
        History.newItem("create");
    }

    private void doShowProfile() {
        History.newItem("profile");
    }

    private void doEditProfile() {
        String[] tokens = History.getToken().split("=");
        final String token2 = tokens.length > 1 ? tokens[1] : "";
        History.newItem("edit");
        Presenter presenter = new EditProfilePresenter(rpcService, eventBus, new EditProfileView(), token2);
        presenter.go(container);
    }

    private void doEditProfileCancelled() {
        History.newItem("list");
    }

    private void doProfileUpdated() {
        History.newItem("list");
    }

    private void doShowProfileList() {
        History.newItem("list", false);
            Presenter presenter = new ListProfilesPresenter(rpcService, eventBus, new ListProfilesView());
            presenter.go(container);
    }

    public void go(final HasWidgets container) {
        this.container = container;

        if ("".equals(History.getToken())) {
            History.newItem("home");
        }
        else {
            History.fireCurrentHistoryState();
        }
    }

    public void onValueChange(ValueChangeEvent<String> event) {
        String[] tokens = History.getToken().split("=");
        final String token = tokens[0];
        final String token2 = tokens.length > 1 ? tokens[1] : "";

        if (token != null) {
            Presenter presenter = null;
            if (token.equals("home")) {
                presenter = new WelcomePagePresenter(eventBus, new WelcomePageView());
            }
            if (token.equals("list")) {
                presenter = new ListProfilesPresenter(rpcService, eventBus, new ListProfilesView());
            }
            if (token.equals("create")) {
                presenter = new CreateProfilePresenter(rpcService, eventBus, new CreateProfileView());
            }
            else if (token.equals("profile") && token2.length() > 0) {
                presenter = new ProfilePresenter(rpcService, eventBus, new ProfileView(), token2);
            }
            else if (token.equals("edit") && token2.length() > 0) {
                presenter = new EditProfilePresenter(rpcService, eventBus, new EditProfileView(), token2);
            }
            if (presenter != null) {
                presenter.go(container);
            }
        }
    }
}
