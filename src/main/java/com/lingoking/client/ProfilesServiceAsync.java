package com.lingoking.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.List;

import com.lingoking.shared.model.ErrorMessages;
import com.lingoking.shared.model.Profile;

public interface ProfilesServiceAsync {

    void createProfile(Profile profile, AsyncCallback<ErrorMessages> callback);
    void deleteProfiles(List<String> ids, AsyncCallback<List<Profile>> callback);
    void getListOfProfiles(int offset, AsyncCallback<List<Profile>> callback);
    void editProfile(String id, Profile profile, AsyncCallback<ErrorMessages> callback);
    void fetchProfile(String id, AsyncCallback<Profile> callback);
    void login(Profile profile, AsyncCallback<Boolean> callback);
    void checkEmail(String id, String email, AsyncCallback<Boolean> callback);
    void getNumberOfPages(AsyncCallback<String> callback);
}

