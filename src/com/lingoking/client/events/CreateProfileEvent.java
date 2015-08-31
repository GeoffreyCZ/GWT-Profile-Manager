package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by Michal on 28. 8. 2015.
 */
public class CreateProfileEvent extends GwtEvent<CreateProfileEventHandler> {
    public static Type<CreateProfileEventHandler> TYPE = new Type<CreateProfileEventHandler>();

    @Override
    public Type<CreateProfileEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(CreateProfileEventHandler handler) {
        handler.onCreateProfile(this);
    }
}
