package com.lingoking.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.WelcomePagePresenter;

public class WelcomePageView extends Composite implements WelcomePagePresenter.Display {
    private final Label titleLabel;
    private final Button createButton;
    private final Button showProfileListButton;
    private FlexTable profilesTable;
    private final FlexTable contentTable;

    public WelcomePageView() {
        DecoratorPanel contentTableDecorator = new DecoratorPanel();
        initWidget(contentTableDecorator);
        contentTableDecorator.setWidth("100%");
        contentTableDecorator.setWidth("18em");

        contentTable = new FlexTable();
        contentTable.setWidth("100%");
        contentTable.getCellFormatter().addStyleName(0, 0, "contacts-ListContainer");
        contentTable.getCellFormatter().setWidth(0, 0, "100%");
        contentTable.getFlexCellFormatter().setVerticalAlignment(0, 0, DockPanel.ALIGN_TOP);

        // Create the menu
        //
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setBorderWidth(0);
        hPanel.setSpacing(0);
        hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        titleLabel = new Label("Profile management menu");
        hPanel.add(titleLabel);
        createButton = new Button("Create new profile");
        hPanel.add(createButton);
        showProfileListButton = new Button("Show list of profiles");
        hPanel.add(showProfileListButton);
        contentTable.getCellFormatter().addStyleName(0, 0, "contacts-ListMenu");
        contentTable.setWidget(0, 0, hPanel);

        // Create the contacts list
        //
        profilesTable = new FlexTable();
        profilesTable.setCellSpacing(0);
        profilesTable.setCellPadding(0);
        profilesTable.setWidth("100%");
        profilesTable.addStyleName("contacts-ListContents");
        profilesTable.getColumnFormatter().setWidth(0, "15px");
        contentTable.setWidget(1, 0, profilesTable);

        contentTableDecorator.add(contentTable);
    }

    public HasClickHandlers getCreateButton() {
        return createButton;
    }

    public HasClickHandlers getListButton() {
        return showProfileListButton;
    }

    public Widget asWidget() {
        return this;
    }
}