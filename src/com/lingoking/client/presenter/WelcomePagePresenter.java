package com.lingoking.client.presenter;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.client.views.CreateProfileView;
import com.lingoking.client.views.EditProfileView;
import com.lingoking.client.views.ListProfilesView;
import com.lingoking.shared.model.Profile;

public class WelcomePagePresenter implements Presenter{

    Display display;

    public interface Display {
        void clear();
        Widget asWidget();
        void setPresenter(WelcomePagePresenter presenter);

    }

    public WelcomePagePresenter(Display display) {
        this.display = display;
        bind();
    }

    public void bind() {
        display.setPresenter(this);
        display.clear();
    }

    @Override
    public void go(Panel panel) {
        panel.add(display.asWidget());
    }

    public void onCreateProfileButtonClicked() {
        RootPanel.get().clear();
        Profile me = new Profile();
        CreateProfileView view = new CreateProfileView();
        CreateProfilePresenter presenter = new CreateProfilePresenter(view);
        presenter.go(RootPanel.get());
        History.newItem("CreateProfile");
    }

    public void onEditProfileButtonClicked() {
        RootPanel.get().clear();
        EditProfileView view = new EditProfileView();
        EditProfilePresenter presenter = new EditProfilePresenter(view);
        presenter.go(RootPanel.get());

    }

    public void onListProfilesButtonClicked() {
        RootPanel.get().clear();
        Profile me = new Profile();
        ListProfilesView view = new ListProfilesView();
        ListProfilesPresenter presenter = new ListProfilesPresenter(me, view);
        presenter.go(RootPanel.get());

    }
}
