package com.lingoking.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.lingoking.client.events.*;
import com.lingoking.client.presenter.*;
import com.lingoking.client.views.CreateProfileView;
import com.lingoking.client.views.ListProfilesView;
import com.lingoking.client.views.WelcomePageView;

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
                        doEditProfile(event.getId());
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

        eventBus.addHandler(ProfileCreatedEvent.TYPE,
                new ProfileCreatedEventHandler() {
                    public void onProfileCreated(ProfileCreatedEvent event) {
                        doProfileUpdated();
                    }
                });
    }

    private void doCreateProfile() {
        History.newItem("create");
    }

    private void doEditProfile(String id) {
        History.newItem("edit", false);
        Presenter presenter = new CreateProfilePresenter(rpcService, eventBus, new CreateProfileView());
        presenter.go(container);
    }

    private void doEditProfileCancelled() {
        History.newItem("list");
    }

    private void doProfileUpdated() {
        History.newItem("list");
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
        String token = event.getValue();

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
            else if (token.equals("edit")) {
                presenter = new CreateProfilePresenter(rpcService, eventBus, new CreateProfileView());
            }
            if (presenter != null) {
                presenter.go(container);
            }
        }
    }
}
