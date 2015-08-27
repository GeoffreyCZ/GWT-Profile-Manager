package com.lingoking.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.lingoking.shared.model.Profile;
import com.google.gwt.user.client.ui.Panel;

public class ListProfilesPresenter implements Presenter {

    Profile profile;
    Display view;

    public interface Display {
        void clear();
        void setName(String name);
        Widget asWidget();
        void setPresenter(ListProfilesPresenter presenter);
    }

    public ListProfilesPresenter(Profile profile, Display view) {
        this.profile = profile;
        this.view = view;
        bind();
    }

    public void bind() {
        view.setPresenter(this);
        view.clear();
        view.setName(profile.getFirstName());
    }

    @Override
    public void go(Panel panel) {
        panel.add(view.asWidget());
    }

    public void onShowProfile() {
        view.setName(profile.getFirstName() + " " + profile.getLastName());
    }

}
