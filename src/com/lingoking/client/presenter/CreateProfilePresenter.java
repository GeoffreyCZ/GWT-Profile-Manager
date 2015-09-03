package com.lingoking.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.validation.client.impl.Validation;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.CreateProfileCancelledEvent;
import com.lingoking.client.events.ProfileCreatedEvent;
import com.lingoking.shared.model.Profile;

import com.google.gwt.validation.client.GwtValidation;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;


public class CreateProfilePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getCreateButton();
        HasClickHandlers getCancelButton();
        Profile getData();
        Widget asWidget();
        FormPanel getFormPanel();
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

//    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//    Set<ConstraintViolation<Profile>> violations = validator.validate(profile);

    public void bind() {
        this.display.getCreateButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
//                validator.validate(profile);
//                if (!violations.isEmpty()) {
//                    Window.alert("Probléééém");
//                } else {
                    display.getFormPanel().submit();
//                }
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
        profile = display.getData();
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