package com.lingoking.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.presenter.CreateProfilePresenter;

public class CreateProfileView extends Composite implements CreateProfilePresenter.Display{
    private static CreateProfileViewUiBinder ourUiBinder = GWT.create(CreateProfileViewUiBinder.class);

    private CreateProfilePresenter presenter;

    public CreateProfileView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public CreateProfileView(String firstName) {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    @UiField
    Label createProfileLabel;

    @UiField
    Button button;

    @UiHandler("button")
    void onClick(ClickEvent e) {
        if (presenter != null) {
            presenter.onShowProfile();
        }
    }

    @Override
    public void clear() {
        createProfileLabel.setText("");
    }

    @Override
    public void setName(String name) {
        this.createProfileLabel.setText(name);

    }

    @Override
    public void setPresenter(CreateProfilePresenter presenter) {
        this.presenter = presenter;
    }

    interface CreateProfileViewUiBinder extends UiBinder<Widget, CreateProfileView> {
    }
}