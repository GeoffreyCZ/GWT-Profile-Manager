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
import com.lingoking.client.ProfilesService;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.CreateProfileCancelledEvent;
import com.lingoking.client.events.ProfileCreatedEvent;
import com.lingoking.shared.model.Profile;

public class CreateProfilePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getCreateButton();
        HasClickHandlers getCancelButton();
        HasValue<String> getFirstName();
        HasValue<String> getLastName();
        HasValue<String> getEmail();
        Widget asWidget();
    }

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
        profile.setFirstName(display.getFirstName().getValue());
        profile.setLastName(display.getLastName().getValue());
        profile.setEmail(display.getEmail().getValue());

        rpcService.createProfile(profile, new AsyncCallback<Profile>() {
            public void onSuccess(Profile result) {
                eventBus.fireEvent(new ProfileCreatedEvent(result));
            }
            public void onFailure(Throwable caught) {
                Window.alert("Error creating new profile.");
            }
        });
    }


}