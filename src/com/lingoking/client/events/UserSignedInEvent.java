package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class UserSignedInEvent extends GwtEvent<UserSignedInEventHandler> {
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