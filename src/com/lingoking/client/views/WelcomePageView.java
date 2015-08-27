package com.lingoking.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.presenter.WelcomePagePresenter;

/**
 * Created by Michal on 27. 8. 2015.
 */
public class WelcomePageView extends Composite implements WelcomePagePresenter.Display {

    private static WelcomePageViewUiBinder ourUiBinder = GWT.create(WelcomePageViewUiBinder.class);

    private WelcomePagePresenter presenter;

    public WelcomePageView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiField
    Button createProfileButton = new Button();

    @UiField
    Button editProfileButton = new Button();

    @UiField
    Button listProfilesButton = new Button();

    @UiHandler("createProfileButton")
    void onCreateProfileButtonClick(ClickEvent e) {
        if (presenter != null) {
            presenter.onCreateProfileButtonClicked();
        }
    }

    @UiHandler("editProfileButton")
    void onEditProfileButtonClick(ClickEvent e) {
        if (presenter != null) {
            presenter.onEditProfileButtonClicked();
        }
    }

    @UiHandler("listProfilesButton")
    void onListProfilesButtonClick(ClickEvent e) {
        if (presenter != null) {
            presenter.onListProfilesButtonClicked();
        }
    }

    @Override
    public void setPresenter(WelcomePagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void clear() {

    }
    interface WelcomePageViewUiBinder extends UiBinder<Widget, WelcomePageView> {
    }
}