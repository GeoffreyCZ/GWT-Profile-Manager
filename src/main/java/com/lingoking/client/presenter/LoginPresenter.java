package com.lingoking.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.UserSignedInEvent;
import com.lingoking.shared.model.Profile;

public class LoginPresenter implements Presenter{

    public interface Display {
        HasClickHandlers getLoginButton();
        Widget asWidget();
        Profile getProfile();
        Label getLoginErrorMessage();
    }

    private Profile profile;
    private final ProfilesServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;

    public LoginPresenter(ProfilesServiceAsync rpcService, HandlerManager eventBus, Display view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.display = view;
        bind();
    }

    public void bind() {
        this.display.getLoginButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        display.getLoginErrorMessage().setText("");
        profile = display.getProfile();
        rpcService.login(profile, new AsyncCallback<Boolean>() {
            public void onSuccess(Boolean result) {
                if (result) {
                    eventBus.fireEvent(new UserSignedInEvent());
                } else {
                    display.getLoginErrorMessage().setText("Wrong email or password!");
                }
            }

            public void onFailure(Throwable caught) {
                Window.alert("An error occurred when trying to log in.");
            }
        });
    }

    public void go(final HasWidgets container) {
        container.clear();
        container.add(display.asWidget());
    }


}
