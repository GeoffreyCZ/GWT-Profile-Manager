package com.lingoking.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface UserNotSignedInEventHandler extends EventHandler {
    void onUserNotSignedIn(UserNotSignedIn event);
}
