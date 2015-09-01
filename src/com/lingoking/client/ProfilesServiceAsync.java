package com.lingoking.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;

import com.lingoking.shared.model.Profile;
import com.lingoking.shared.model.ProfileDetails;

public interface ProfilesServiceAsync {

    void createProfile(Profile profile, AsyncCallback<Profile> callback);
    void deleteProfiles(ArrayList<String> ids, AsyncCallback<ArrayList<Profile>> callback);
    void getListOfProfiles(AsyncCallback<ArrayList<Profile>> callback);
    void editProfile(String id, AsyncCallback<Profile> callback);
    void fetchProfile(String id, AsyncCallback<Profile> callback);
}

