package com.lingoking.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.CreateProfileEvent;
import com.lingoking.client.events.EditProfileEvent;
import com.lingoking.client.events.ShowProfileListEvent;
import com.lingoking.shared.model.ProfileDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michal on 31. 8. 2015.
 */
public class WelcomePagePresenter implements Presenter {

    public interface Display {
        HasClickHandlers getCreateButton();
        HasClickHandlers getListButton();
        Widget asWidget();
    }

    private final HandlerManager eventBus;
    private final Display display;

    public WelcomePagePresenter(HandlerManager eventBus, Display view) {
        this.eventBus = eventBus;
        this.display = view;
    }

    public void bind() {
        display.getCreateButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new CreateProfileEvent());
            }
        });

        display.getListButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new ShowProfileListEvent());
            }
        });
    }

    public void go(final HasWidgets container) {
        bind();
        container.clear();
        container.add(display.asWidget());
    }
}
