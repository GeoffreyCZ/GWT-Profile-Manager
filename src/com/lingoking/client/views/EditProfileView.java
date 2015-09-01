package com.lingoking.client.views;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.EditProfilePresenter;

/**
 * Created by Michal on 1. 9. 2015.
 */
public class EditProfileView extends Composite implements EditProfilePresenter.Display {
    private final TextBox firstName;
    private final TextBox lastName;
    private final TextBox emailAddress;
    private final FlexTable detailsTable;
    private final Button editButton;
    private final Button cancelButton;

    public EditProfileView() {
        DecoratorPanel contentDetailsDecorator = new DecoratorPanel();
        contentDetailsDecorator.setWidth("18em");
        initWidget(contentDetailsDecorator);

        VerticalPanel contentDetailsPanel = new VerticalPanel();
        contentDetailsPanel.setWidth("100%");

        detailsTable = new FlexTable();
        detailsTable.setCellSpacing(0);
        detailsTable.setWidth("100%");
        detailsTable.addStyleName("profiles-ListContainer");
        detailsTable.getColumnFormatter().addStyleName(1, "add-profile-input");
        firstName = new TextBox();
        lastName = new TextBox();
        emailAddress = new TextBox();
        initDetailsTable();
        contentDetailsPanel.add(detailsTable);

        HorizontalPanel menuPanel = new HorizontalPanel();
        editButton = new Button("Finish");
        cancelButton = new Button("Cancel");
        menuPanel.add(editButton);
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

    public HasClickHandlers getEditButton() {
        return editButton;
    }

    public HasClickHandlers getCancelButton() {
        return cancelButton;
    }

    public Widget asWidget() {
        return this;
    }
}