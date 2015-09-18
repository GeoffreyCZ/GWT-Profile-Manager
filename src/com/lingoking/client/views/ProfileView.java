package com.lingoking.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.ProfilePresenter;
import com.lingoking.server.UploadServlet;
import com.lingoking.shared.model.Profile;
import com.reveregroup.gwt.imagepreloader.FitImage;

public class ProfileView extends Composite implements ProfilePresenter.Display {
    private final Button editButton;
    private final Button deleteButton;
    private final Button backButton;
    private final FitImage avatarImage;
    private FlexTable profileTable;
    private final FlexTable contentTable;

    public ProfileView() {
        DecoratorPanel contentTableDecorator = new DecoratorPanel();
        initWidget(contentTableDecorator);
        contentTableDecorator.setWidth("100%");
        contentTableDecorator.setWidth("18em");

        contentTable = new FlexTable();
        contentTable.setWidth("100%");
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
        avatarImage = new FitImage();
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
        avatarImage.setUrl(UploadServlet.PATH_TO_FILE + profile.getAvatar());
        avatarImage.setMaxSize(90, 90);
        if (profile.getAvatar() == "") {
            avatarImage.setUrl("lib/avatar.jpg");
        }
        profileTable.removeAllRows();
        profileTable.setText(1, 1, profile.getFirstName());
        profileTable.setText(2, 1, profile.getLastName());
        profileTable.setText(3, 1, profile.getEmailAddress());
        profileTable.setText(4, 1, profile.getPhoneNumber());
        profileTable.setText(5, 1, profile.getAddress().getStreet());
        profileTable.setText(5, 2, profile.getAddress().getStreetNumber());
        profileTable.setText(6, 1, profile.getAddress().getCity());
        profileTable.setText(6, 2, profile.getAddress().getPostcode());
        profileTable.setWidget(7, 1, avatarImage);
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