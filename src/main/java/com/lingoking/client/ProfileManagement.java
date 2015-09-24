package com.lingoking.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class ProfileManagement implements EntryPoint {

    public void onModuleLoad() {
        ProfilesServiceAsync rpcService = GWT.create(ProfilesService.class);
        HandlerManager eventBus = new HandlerManager(null);
        AppController appController = new AppController(rpcService, eventBus);
        appController.go(RootPanel.get());
    }
}
