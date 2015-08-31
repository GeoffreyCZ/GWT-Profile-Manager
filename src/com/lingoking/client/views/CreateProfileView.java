package com.lingoking.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.CreateProfilePresenter;

public class CreateProfileView extends Composite implements CreateProfilePresenter.Display {
    private final TextBox firstName;
    private final TextBox lastName;
    private final TextBox emailAddress;
    private final FlexTable detailsTable;
    private final Button createButton;
    private final Button cancelButton;

    public CreateProfileView() {
        DecoratorPanel contentDetailsDecorator = new DecoratorPanel();
        contentDetailsDecorator.setWidth("18em");
        initWidget(contentDetailsDecorator);

        VerticalPanel contentDetailsPanel = new VerticalPanel();
        contentDetailsPanel.setWidth("100%");

        // Create the contacts list
        //
        detailsTable = new FlexTable();
        detailsTable.setCellSpacing(0);
        detailsTable.setWidth("100%");
        detailsTable.addStyleName("contacts-ListContainer");
        detailsTable.getColumnFormatter().addStyleName(1, "add-contact-input");
        firstName = new TextBox();
        lastName = new TextBox();
        emailAddress = new TextBox();
        initDetailsTable();
        contentDetailsPanel.add(detailsTable);

        HorizontalPanel menuPanel = new HorizontalPanel();
        createButton = new Button("Create");
        cancelButton = new Button("Cancel");
        menuPanel.add(createButton);
        menuPanel.add(cancelButton);
        contentDetailsPanel.add(menuPanel);
        contentDetailsDecorator.add(contentDetailsPanel);
    }

    private void initDetailsTable() {
        detailsTable.setWidget(0, 0, new Label("Firstname"));
        detailsTable.setWidget(0, 1, firstName);
        detailsTable.setWidget(1, 0, new Label("Lastname"));
        detailsTable.setWidget(1, 1, lastName);
        detailsTable.setWidget(2, 0, new Label("Email Address"));
        detailsTable.setWidget(2, 1, emailAddress);
        firstName.setFocus(true);
    }

    public HasValue<String> getFirstName() {
        return firstName;
    }

    public HasValue<String> getLastName() {
        return lastName;
    }

    public HasValue<String> getEmail() {
        return emailAddress;
    }

    public HasClickHandlers getCreateButton() {
        return createButton;
    }

    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }

    public Widget asWidget() {
        return this;
    }
}