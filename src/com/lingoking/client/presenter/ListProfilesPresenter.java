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
import com.lingoking.client.events.EditProfileEvent;
import com.lingoking.client.events.ShowProfileEvent;
import com.lingoking.shared.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ListProfilesPresenter implements Presenter {

    private List<Profile> profile;

    public interface Display {
        HasClickHandlers getCreateButton();
        HasClickHandlers getDeleteButton();
        HasClickHandlers getProfilesList();
        void setData(List<String> data);
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

    public void sortProfileList() {
        for (int i = 0; i < profile.size(); ++i) {
            for (int j = 0; j < profile.size() - 1; ++j) {
                if (profile.get(j).getWholeName().compareToIgnoreCase(profile.get(j + 1).getWholeName()) >= 0) {
                    Profile tmp = profile.get(j);
                    profile.set(j, profile.get(j + 1));
                    profile.set(j + 1, tmp);
                }
            }
        }
    }

    public String getProfileId (String profileId) {
        return profileId;
    }

    public void setProfile(List<Profile> profile) {
        this.profile = profile;
    }

    private void fetchProfileList() {
        rpcService.getListOfProfiles(new AsyncCallback<ArrayList<Profile>>() {
            public void onSuccess(ArrayList<Profile> result) {
                profile = result;
                sortProfileList();
                List<String> data = new ArrayList<>();

                for (int i = 0; i < result.size(); ++i) {
                    data.add(profile.get(i).getFirstName() + " " + profile.get(i).getLastName() + " " + profile.get(i).getEmail());
                }
                display.setData(data);
            }
            public void onFailure(Throwable caught) {
                Window.alert("Error fetching profiles");
            }
        });
    }

    private void deleteSelectedProfiles() {
        List<Integer> selectedRows = display.getSelectedRows();
        if (selectedRows.size() > 0) {
            ArrayList<String> ids = new ArrayList<>();

            for (int i = 0; i < selectedRows.size(); ++i) {
                ids.add(profile.get(selectedRows.get(i)).getId());
            }

            rpcService.deleteProfiles(ids, new AsyncCallback<ArrayList<Profile>>() {
                public void onSuccess(ArrayList<Profile> result) {
                    profile = result;
                    sortProfileList();
                    List<String> data = new ArrayList<>();

                    for (int i = 0; i < result.size(); ++i) {
                        data.add(profile.get(i).getWholeName());
                    }
                    fetchProfileList();
                }

                public void onFailure(Throwable caught) {
                    Window.alert("Error deleting selected profiles");
                }
            });
        }
    }
}
