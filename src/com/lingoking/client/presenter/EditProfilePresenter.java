package com.lingoking.client.presenter;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.lingoking.shared.model.Profile;

/**
 * Created by Michal on 27. 8. 2015.
 */
public class EditProfilePresenter implements Presenter {

    Profile profile;
    Display view;

    public interface Display {
        void clear();
        void setName(String name);
        Widget asWidget();
        void setPresenter(EditProfilePresenter presenter);
    }

    public EditProfilePresenter(Display view) {
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
        view.setName(profile.getFirstName() + "<br>" + profile.getLastName());
    }

}