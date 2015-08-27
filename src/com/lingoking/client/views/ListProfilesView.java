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
import com.lingoking.client.presenter.ListProfilesPresenter;

public class ListProfilesView extends Composite implements ListProfilesPresenter.Display {

    private static ListProfilesViewUiBinder ourUiBinder = GWT.create(ListProfilesViewUiBinder.class);

    private ListProfilesPresenter presenter;

    public ListProfilesView() {
        initWidget(ourUiBinder.createAndBindUi(this));

        label.setText("There will be a tool to list profiles. Soon(TM)");
    }

    public ListProfilesView(String firstName) {
        initWidget(ourUiBinder.createAndBindUi(this));
        button.setText(firstName);
    }

    @UiField
    Button button;
    @UiField
    Label label;

    @UiHandler("button")
    void onClick(ClickEvent e) {
        if (presenter != null) {
            presenter.onShowProfile();
        }
    }

    @Override
    public void clear() {
        label.setText("");
    }

    @Override
    public void setName(String name) {
        this.label.setText(name);

    }

    @Override
    public void setPresenter(ListProfilesPresenter presenter) {
        this.presenter = presenter;
    }

    interface ListProfilesViewUiBinder extends UiBinder<Widget, ListProfilesView> {
    }
}