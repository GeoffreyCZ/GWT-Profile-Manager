package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class CreateProfileCancelledEvent extends GwtEvent<CreateProfileCancelledEventHandler> {
    public static Type<CreateProfileCancelledEventHandler> TYPE = new Type<CreateProfileCancelledEventHandler>();

    @Override
    public Type<CreateProfileCancelledEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CreateProfileCancelledEventHandler handler) {
        handler.onCreateProfileCancelled(this);
    }
}
