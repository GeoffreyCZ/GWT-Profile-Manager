package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by Michal on 6. 10. 2015.
 */
public class UserNotSignedInEvent extends GwtEvent<UserNotSignedInEventHandler> {
    public static Type<UserNotSignedInEventHandler> TYPE = new Type<>();

    @Override
    public Type<UserNotSignedInEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UserNotSignedInEventHandler handler) {
        handler.onUserNotSignedIn(this);
    }
}