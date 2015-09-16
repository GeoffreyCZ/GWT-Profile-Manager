package com.lingoking.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.ArrayList;

import com.lingoking.client.ProfilesService;
import com.lingoking.shared.model.Profile;

@SuppressWarnings("serial")
public class ProfilesServiceImpl extends RemoteServiceServlet implements
        ProfilesService {

    public ProfilesServiceImpl() {
    }

    public Profile createProfile (Profile profile) {
        ConnectionConfiguration.insertIntoDB(profile);
        return profile;
    }

    public Profile editProfile(String id, Profile profile) {
        profile = ConnectionConfiguration.editProfileInDB(id, profile);
        return profile;
    }

    public ArrayList<Profile> getListOfProfiles() {
        ArrayList<Profile> profiles;
        profiles = ConnectionConfiguration.fetchAllProfilesFromDB();
        return profiles;
    }

//    public Boolean login(Profile profile) {
//        ConnectionConfiguration.searchInDB(profile);
//        return true;
//    }

    public Boolean deleteProfiles(String id) {
        ConnectionConfiguration.deleteProfilesFromDB(id);
        return true;
    }

    public ArrayList<Profile> deleteProfiles(ArrayList<String> ids) {
        for (int i = 0; i < ids.size(); ++i) {
            deleteProfiles(ids.get(i));
        }
        return getListOfProfiles();
    }

    public Profile fetchProfile(String id) {
        Profile profile;
        profile = ConnectionConfiguration.fetchProfileFromDB(id);
        return profile;
    }
}
