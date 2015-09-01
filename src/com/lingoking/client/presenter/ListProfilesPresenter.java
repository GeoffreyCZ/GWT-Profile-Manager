package com.lingoking.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.CreateProfileEvent;
import com.lingoking.shared.model.Profile;

import java.util.ArrayList;
import java.util.List;

public class ListProfilesPresenter implements Presenter {

    private List<Profile> profile;

    public interface Display {
        HasClickHandlers getCreateButton();
        HasClickHandlers getDeleteButton();
//        HasClickHandlers getList();
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

//        display.getList().addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent event) {
//                int selectedRow = display.getClickedRow(event);
//
//                if (selectedRow >= 0) {
//                    String id = profile.get(selectedRow).getId();
//                    eventBus.fireEvent(new EditProfileEvent(id));
//                }
//            }
//        });
    }

    public void go(final HasWidgets container) {
        bind();
        container.clear();
        container.add(display.asWidget());
        fetchProfileList();
    }

    public void sortProfileList() {

        // Yes, we could use a more optimized method of sorting, but the
        //  point is to create a test case that helps illustrate the higher
        //  level concepts used when creating MVP-based applications.
        //
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

    public void setProfile(List<Profile> profile) {
        this.profile = profile;
    }

    public Profile getProfileDetail(int index) {
        return profile.get(index);
    }

    private void fetchProfileList() {
        rpcService.getListOfProfiles(new AsyncCallback<ArrayList<Profile>>() {
            public void onSuccess(ArrayList<Profile> result) {
                profile = result;
                sortProfileList();
                List<String> data = new ArrayList<String>();

                for (int i = 0; i < result.size(); ++i) {
                    data.add(profile.get(i).getFirstName() + " " + profile.get(i).getLastName());
                }

                display.setData(data);
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error fetching profile details");
            }
        });
    }

    private void deleteSelectedProfiles() {
        List<Integer> selectedRows = display.getSelectedRows();
        ArrayList<String> ids = new ArrayList<String>();

        for (int i = 0; i < selectedRows.size(); ++i) {
            ids.add(profile.get(selectedRows.get(i)).getId());
        }

        rpcService.deleteProfiles(ids, new AsyncCallback<ArrayList<Profile>>() {
            public void onSuccess(ArrayList<Profile> result) {
                profile = result;
                sortProfileList();
                List<String> data = new ArrayList<String>();

                for (int i = 0; i < result.size(); ++i) {
                    data.add(profile.get(i).getWholeName());
                }
                display.setData(data);
            }

            public void onFailure(Throwable caught) {
                Window.alert("Error deleting selected contacts");
            }
        });
    }
}
