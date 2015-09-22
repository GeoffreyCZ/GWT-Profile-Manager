package com.lingoking.client.views;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.lingoking.client.presenter.ListProfilesPresenter;
import com.lingoking.server.UploadServlet;
import com.lingoking.shared.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ListProfilesView extends Composite implements ListProfilesPresenter.Display {
    private final Button createButton;
    private final Button deleteButton;
    private FlexTable profilesTable;
    private final FlexTable contentTable;
    Image avatarImage = new Image();

    public ListProfilesView() {
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
        createButton = new Button("Create new profile");
        hPanel.add(createButton);
        deleteButton = new Button("Delete selected profiles");
        hPanel.add(deleteButton);
        contentTable.setWidget(0, 0, hPanel);

        profilesTable = new FlexTable();
        profilesTable.setCellSpacing(0);
        profilesTable.setCellPadding(0);
        profilesTable.setWidth("100%");
        profilesTable.getColumnFormatter().setWidth(0, "15px");
        contentTable.setWidget(1, 0, profilesTable);

        contentTableDecorator.add(contentTable);
    }

    public HasClickHandlers getCreateButton() {
        return createButton;
    }

    public HasClickHandlers getDeleteButton() {
        return deleteButton;
    }

    public void setData(List<Profile> data) {
        profilesTable.removeAllRows();
        for (int i = 0; i < data.size(); ++i) {
            avatarImage = new Image();
            if (data.get(i).getAvatar().equals("")) {
                avatarImage.setUrl("lib/avatar.jpg");
            } else {
                avatarImage.setUrl(UploadServlet.IMAGES_DIRECTORY + "thumb_" + data.get(i).getAvatar());
            }
            profilesTable.setWidget(i, 0, new CheckBox());
            profilesTable.setWidget(i, 1, avatarImage);
            profilesTable.setText(i, 2, data.get(i).getFirstName());
            profilesTable.setText(i, 3, data.get(i).getLastName());
            profilesTable.setText(i, 4, data.get(i).getEmailAddress());
        }
    }

    public int getClickedRow(ClickEvent event) {
        int selectedRow = -1;
        HTMLTable.Cell cell = profilesTable.getCellForEvent(event);

        if (cell != null) {
            if (cell.getCellIndex() > 0) {
                selectedRow = cell.getRowIndex();
            }
        }
        return selectedRow;
    }

    public HasClickHandlers getProfilesList() {
        return profilesTable;
    }

    public List<Integer> getSelectedRows() {
        List<Integer> selectedRows = new ArrayList<Integer>();

        for (int i = 0; i < profilesTable.getRowCount(); ++i) {
            CheckBox checkBox = (CheckBox)profilesTable.getWidget(i, 0);
            if (checkBox.getValue()) {
                selectedRows.add(i);
            }
        }
        return selectedRows;
    }

    public Widget asWidget() {
        return this;
    }
}