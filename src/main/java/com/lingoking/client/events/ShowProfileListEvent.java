package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class ShowProfileListEvent extends GwtEvent<ShowProfileListEventHandler> {
    public static Type<ShowProfileListEventHandler> TYPE = new Type<ShowProfileListEventHandler>();

    @Override
    public Type<ShowProfileListEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowProfileListEventHandler handler) {
        handler.onShowProfileList(this);
    }
}