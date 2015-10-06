package com.lingoking.client.presenter;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.UserSignedInEvent;
import com.lingoking.shared.model.Profile;

public class LoginPresenter implements Presenter {

    public interface Display {
    	Profile getProfile();
    	Label getLoginErrorMessage();
    	HasClickHandlers getLoginButton();
        Widget asWidget();
    }

    private final ProfilesServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;
	private Profile profile;
	final long DURATION = 1000 * 60 * 60 * 24; // 1 day expiration time
	Date expires = new Date(System.currentTimeMillis() + DURATION);

    public LoginPresenter(ProfilesServiceAsync rpcService, HandlerManager eventBus, Display display) {
        this.eventBus = eventBus;
        this.display = display;
        this.profile = new Profile();
        this.rpcService = rpcService;
        bind();
    }

    public void bind() {
        this.display.getLoginButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	profile.setEmailAddress(display.getProfile().getEmailAddress());
            	profile.setPassword(display.getProfile().getPassword());
            	validateLoginCredentials();
            }
        });
    }

    public void go(final HasWidgets container) {
        container.clear();
        container.add(display.asWidget());
    }
    
    private void validateLoginCredentials() {
    	display.getLoginErrorMessage().setText("");
    	rpcService.login(profile, new AsyncCallback<String>() {
            public void onSuccess(String token) {
            	if (!token.equals("")) {
            		doSignIn(token);
            	} else {
            		display.getLoginErrorMessage().setText("Your email or password is incorrect! Try again.");
            	}
            }
            public void onFailure(Throwable caught) {
        		Window.alert("Error contacting database!");
            }
        });
    }
    private void doSignIn(String token) {
    	Cookies.setCookie("token", token, expires, null, "/", false);
    	eventBus.fireEvent(new UserSignedInEvent());
    }
}
