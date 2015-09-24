package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class ShowProfileEvent extends GwtEvent<ShowProfileEventHandler> {
    public static Type<ShowProfileEventHandler> TYPE = new Type<ShowProfileEventHandler>();

    @Override
    public Type<ShowProfileEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowProfileEventHandler handler) {
        handler.onShowProfile(this);
    }
}