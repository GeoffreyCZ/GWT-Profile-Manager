package com.lingoking.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.lingoking.shared.model.ErrorMessages;
import com.lingoking.shared.model.Profile;

import java.util.ArrayList;
import java.util.List;

@RemoteServiceRelativePath("profilesService")
public interface ProfilesService extends RemoteService {

    ErrorMessages createProfile(Profile profile);
    List<Profile> deleteProfiles(List<String> ids);
    List<Profile> getListOfProfiles(int offset);
    ErrorMessages editProfile(String id, Profile profile);
    Profile fetchProfile(String id);
    Boolean login(Profile profile);
    Boolean checkEmail(String id, String email);
    String getNumberOfPages();

    class App {
        private static ProfilesServiceAsync ourInstance = GWT.create(ProfilesService.class);

        public static synchronized ProfilesServiceAsync getInstance() {
            return ourInstance;
        }
    }
}

