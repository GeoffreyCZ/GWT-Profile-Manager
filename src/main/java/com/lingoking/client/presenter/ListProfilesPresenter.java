package com.lingoking.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.ProfilesServiceAsync;
import com.lingoking.client.events.CreateProfileEvent;
import com.lingoking.client.events.ShowProfileEvent;
import com.lingoking.client.events.ShowProfileListEvent;
import com.lingoking.shared.model.Profile;

import java.util.*;

public class ListProfilesPresenter implements Presenter {

    private List<Profile> profile;
    private int page;
    private String numberOfPages;
    private int numberOfDeletedProfiles;

    public interface Display {
        HasClickHandlers getCreateButton();
        HasClickHandlers getDeleteButton();
        HasClickHandlers getProfilesList();
        HasClickHandlers getPreviousButtonClick();
        Button getPreviousButton();
        HasClickHandlers getNextButtonClick();
        Button getNextButton();
        Label getCurrentPageLabel();
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

        display.getPreviousButtonClick().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (page > 0) {
                    display.getNextButton().setEnabled(true);
                    page -= 1;
                    fetchProfileList(page);
                    display.getCurrentPageLabel().setText("Page " + (page + 1));
                    if (page == 0) {
                        display.getPreviousButton().setEnabled(false);
                    } else {
                        display.getPreviousButton().setEnabled(true);
                    }
                }
            }
        });

        display.getNextButtonClick().addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if ((page + 1) < Integer.parseInt(numberOfPages)) {
                    display.getPreviousButton().setEnabled(true);
                    page += 1;
                    fetchProfileList(page);
                    display.getCurrentPageLabel().setText("Page " + (page + 1));
                    if ((page + 1) == Integer.parseInt(numberOfPages)) {
                        display.getNextButton().setEnabled(false);
                    } else {
                        display.getNextButton().setEnabled(true);
                    }
                }
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
        page = 0;
        fetchProfileList(page);
        getNumberOfPages();
        container.clear();
        container.add(display.asWidget());
        bind();
        display.getCurrentPageLabel().setText("Page " + (page + 1));
        display.getPreviousButton().setEnabled(false);
    }

    public String getProfileId (String profileId) {
        return profileId;
    }

    public void setProfile(List<Profile> profile) {
        this.profile = profile;
    }

    private void getNumberOfPages() {
        rpcService.getNumberOfPages(new AsyncCallback<String>() {
            public void onSuccess(String result) {
                numberOfPages = result;
            }
            public void onFailure(Throwable caught) {
            }
        });
    }

    private void fetchProfileList(int offset) {
        rpcService.getListOfProfiles(offset, new AsyncCallback<List<Profile>>() {
            public void onSuccess(List<Profile> result) {
                profile = result;
                display.setData(result);
            }
            public void onFailure(Throwable caught) {
            }
        });
    }

    private void deleteSelectedProfiles() {
        List<Integer> selectedRows = display.getSelectedRows();
        numberOfDeletedProfiles = 0;
        if (selectedRows.size() > 0) {
            List<String> ids = new ArrayList<>();

            for (int i = 0; i < selectedRows.size(); ++i) {
                ids.add(profile.get(selectedRows.get(i)).getId());
                numberOfDeletedProfiles += 1;
            }

            rpcService.deleteProfiles(ids, new AsyncCallback<List<Profile>>() {
                public void onSuccess(List<Profile> result) {
                    Window.alert("Successfully deleted " + numberOfDeletedProfiles + " profiles!");
                    eventBus.fireEvent(new ShowProfileListEvent());
                }

                public void onFailure(Throwable caught) {
                    Window.alert("Error deleting selected profiles");
                }
            });
        }
    }
}
