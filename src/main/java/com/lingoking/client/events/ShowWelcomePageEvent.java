package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by Michal on 6. 10. 2015.
 */
public class ShowWelcomePageEvent extends GwtEvent<ShowWelcomePageEventHandler> {
    public static Type<ShowWelcomePageEventHandler> TYPE = new Type<>();

    @Override
    public Type<ShowWelcomePageEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ShowWelcomePageEventHandler handler) {
        handler.onShowWelcomePage(this);
    }
}