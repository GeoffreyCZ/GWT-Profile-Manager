package com.lingoking.client.presenter;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.shared.model.Profile;

public class CreateProfilePresenter implements Presenter {

    Profile profile;
    Display view;

    public interface Display {
        void clear();
        void setName(String name);
        Widget asWidget();
        void setPresenter(CreateProfilePresenter presenter);
    }

    public CreateProfilePresenter(Display view) {
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
        view.setName("ssssss");
    }

}