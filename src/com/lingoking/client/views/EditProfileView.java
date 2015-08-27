package com.lingoking.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.presenter.CreateProfilePresenter;
import com.lingoking.client.presenter.EditProfilePresenter;

public class EditProfileView extends Composite implements EditProfilePresenter.Display{
    private static EditProfileViewUiBinder ourUiBinder = GWT.create(EditProfileViewUiBinder.class);

    private EditProfilePresenter presenter;

    public EditProfileView() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    public EditProfileView(String firstName) {
        initWidget(ourUiBinder.createAndBindUi(this));
        editProfileLabel.setText("There will be a tool to edit profile. Soon(TM)");
    }

    @UiField
    Label editProfileLabel;

    @Override
    public void clear() {
        editProfileLabel.setText("");
    }

    @Override
    public void setName(String name) {
        this.editProfileLabel.setText(name);

    }

    @Override
    public void setPresenter(EditProfilePresenter presenter) {
        this.presenter = presenter;
    }

    interface EditProfileViewUiBinder extends UiBinder<Widget, EditProfileView> {
    }
}