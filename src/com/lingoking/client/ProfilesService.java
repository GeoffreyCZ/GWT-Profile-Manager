package com.lingoking.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.lingoking.shared.model.Profile;

import java.util.ArrayList;

@RemoteServiceRelativePath("profilesService")
public interface ProfilesService extends RemoteService {

    Profile createProfile(Profile profile);
    ArrayList<Profile> deleteProfiles(ArrayList<String> ids);
    ArrayList<Profile> getListOfProfiles();
    Profile editProfile(String id, Profile profile);
    Profile fetchProfile(String id);
//    Boolean login(Profile profile);

    class App {
        private static ProfilesServiceAsync ourInstance = GWT.create(ProfilesService.class);

        public static synchronized ProfilesServiceAsync getInstance() {
            return ourInstance;
        }
    }
}

