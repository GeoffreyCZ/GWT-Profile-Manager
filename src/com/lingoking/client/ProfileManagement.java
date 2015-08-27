package com.lingoking.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.lingoking.client.presenter.WelcomePagePresenter;
import com.lingoking.client.views.WelcomePageView;


public class ProfileManagement implements EntryPoint {

    @Override
    public void onModuleLoad() {
        WelcomePageView view = new WelcomePageView();
        WelcomePagePresenter presenter = new WelcomePagePresenter(view);
        presenter.go(RootPanel.get());
    }
}
