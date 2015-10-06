package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by Michal on 6. 10. 2015.
 */
public class ShowWelcomePageEvent extends GwtEvent<ShowWelcomePageEventHandler> {
    public static Type<ShowWelcomePageEventHandler> TYPE = new Type<ShowWelcomePageEventHandler>();

    public Type<ShowWelcomePageEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(ShowWelcomePageEventHandler handler) {
        handler.onShowWelcomePage(this);
    }
}
