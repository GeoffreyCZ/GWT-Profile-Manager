package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class EditProfileCancelledEvent extends GwtEvent<EditProfileCancelledEventHandler> {
    public static Type<EditProfileCancelledEventHandler> TYPE = new Type<EditProfileCancelledEventHandler>();

    @Override
    public Type<EditProfileCancelledEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EditProfileCancelledEventHandler handler) {
        handler.onEditProfileCancelled(this);
    }
}
