package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by Michal on 6. 10. 2015.
 */
public class UserNotSignedIn extends GwtEvent<UserNotSignedInHandler> {
    public static Type<UserSignedInEventHandler> TYPE = new Type<>();

    @Override
    public Type<UserSignedInEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(UserSignedInEventHandler handler) {
        handler.onUserSignedIn(this);
    }
}