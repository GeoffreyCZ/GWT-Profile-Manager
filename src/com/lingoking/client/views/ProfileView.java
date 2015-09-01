package com.lingoking.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.ProfilePresenter;
import com.lingoking.shared.model.Profile;

public class ProfileView extends Composite implements ProfilePresenter.Display {
    private final Button editButton;
    private final Button deleteButton;
    private final Button backButton;
    private FlexTable profileTable;
    private final FlexTable contentTable;

    public ProfileView() {
        DecoratorPanel contentTableDecorator = new DecoratorPanel();
        initWidget(contentTableDecorator);
        contentTableDecorator.setWidth("100%");
        contentTableDecorator.setWidth("18em");

        contentTable = new FlexTable();
        contentTable.setWidth("100%");
        contentTable.getCellFormatter().addStyleName(0, 0, "profiles-ListContainer");
        contentTable.getCellFormatter().setWidth(0, 0, "100%");
        contentTable.getFlexCellFormatter().setVerticalAlignment(0, 0, DockPanel.ALIGN_TOP);

        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setBorderWidth(0);
        hPanel.setSpacing(0);
        hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
        editButton = new Button("Edit this profile");
        hPanel.add(editButton);
        deleteButton = new Button("Delete this profile");
        hPanel.add(deleteButton);
        backButton = new Button("Back to list");
        hPanel.add(backButton);
        contentTable.getCellFormatter().addStyleName(0, 0, "profiles-ListMenu");
        contentTable.setWidget(0, 0, hPanel);

        profileTable = new FlexTable();
        profileTable.setCellSpacing(0);
        profileTable.setCellPadding(0);
        profileTable.setWidth("100%");
        profileTable.getColumnFormatter().setWidth(0, "15px");
        contentTable.setWidget(1, 0, profileTable);

        contentTableDecorator.add(contentTable);
    }

    public void setData(Profile profile) {
        profileTable.removeAllRows();
            profileTable.setText(1, 1, profile.getFirstName());
    }

    public HasClickHandlers getBackButton() {
        return backButton;
    }

    public HasClickHandlers getEditButton() {
        return editButton;
    }

    public HasClickHandlers getDeleteButton() {
        return deleteButton;
    }

    public Widget asWidget() {
        return this;
    }
}