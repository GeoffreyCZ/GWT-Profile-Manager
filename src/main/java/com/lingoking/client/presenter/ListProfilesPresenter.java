package com.lingoking.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.CreateProfileEvent;
import com.lingoking.client.events.ShowProfileEvent;
import com.lingoking.shared.model.Profile;

import java.util.*;

public class ListProfilesPresenter implements Presenter {

    private List<Profile> profile;

    public interface Display {
        HasClickHandlers getCreateButton();
        HasClickHandlers getDeleteButton();
        HasClickHandlers getProfilesList();
        void setData(List<Profile> data);
        int getClickedRow(ClickEvent event);
        List<Integer> getSelectedRows();
        Widget asWidget();
    }

    private final ProfilesServiceAsync rpcService;
    private final HandlerManager eventBus;
    private final Display display;

    public ListProfilesPresenter(ProfilesServiceAsync rpcService, HandlerManager eventBus, Display view) {
        this.rpcService = rpcService;
        this.eventBus = eventBus;
        this.display = view;
    }

    public void bind() {
        display.getCreateButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new CreateProfileEvent());
            }
        });

        display.getDeleteButton().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                deleteSelectedProfiles();
            }
        });

        display.getProfilesList().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int selectedRow = display.getClickedRow(event);

                if (selectedRow >= 0) {
                    String profileId = profile.get(selectedRow).getId();
                    getProfileId(profileId);
                    eventBus.fireEvent(new ShowProfileEvent());
                    History.newItem("profile=" + profile.get(selectedRow).getId());
                }
            }
        });
    }

    public void go(final HasWidgets container) {
        bind();
        container.clear();
        container.add(display.asWidget());
        fetchProfileList();
    }

    public String getProfileId (String profileId) {
        return profileId;
    }

    public void setProfile(List<Profile> profile) {
        this.profile = profile;
    }

    private void fetchProfileList() {
        rpcService.getListOfProfiles(new AsyncCallback<List<Profile>>() {
            public void onSuccess(List<Profile> result) {
                profile = result;
                display.setData(result);
            }
            public void onFailure(Throwable caught) {
                Window.alert("onFailure: " + caught.getStackTrace().toString());
            }
        });
    }

    private void deleteSelectedProfiles() {
        List<Integer> selectedRows = display.getSelectedRows();
        if (selectedRows.size() > 0) {
            List<String> ids = new ArrayList<>();

            for (int i = 0; i < selectedRows.size(); ++i) {
                ids.add(profile.get(selectedRows.get(i)).getId());
            }

            rpcService.deleteProfiles(ids, new AsyncCallback<List<Profile>>() {
                public void onSuccess(List<Profile> result) {
                    fetchProfileList();
                }

                public void onFailure(Throwable caught) {
                    Window.alert("Error deleting selected profiles");
                }
            });
        }
    }
}
