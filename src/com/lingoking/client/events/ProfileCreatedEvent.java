package com.lingoking.client.events;

import com.google.gwt.event.shared.GwtEvent;
import com.lingoking.shared.model.Profile;

/**
 * Created by Michal on 28. 8. 2015.
 */
public class ProfileCreatedEvent extends GwtEvent<ProfileCreatedEventHandler>{
    public static Type<ProfileCreatedEventHandler> TYPE = new Type<ProfileCreatedEventHandler>();
//    private final Profile createdProfile;

//    public ProfileCreatedEvent(Profile updatedProfile) {
//        this.createdProfile = updatedProfile;
//    }
//
//    public Profile getCreatedProfile() { return createdProfile; }


    @Override
    public GwtEvent.Type<ProfileCreatedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ProfileCreatedEventHandler handler) {
        handler.onProfileCreated(this);
    }
}
