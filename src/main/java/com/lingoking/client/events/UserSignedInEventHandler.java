package com.lingoking.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface UserSignedInEventHandler extends EventHandler {
    void onUserSignedIn(UserSignedInEvent event);
}
