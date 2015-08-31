package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class EditProfileEvent extends GwtEvent<EditProfileEventHandler>{
    public static Type<EditProfileEventHandler> TYPE = new Type<EditProfileEventHandler>();
    private final String id;

    public EditProfileEvent(String id) {
        this.id = id;
    }

    public String getId() { return id; }

    @Override
    public Type<EditProfileEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EditProfileEventHandler handler) {
        handler.onEditProfile(this);
    }
}
