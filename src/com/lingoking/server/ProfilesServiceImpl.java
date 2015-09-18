package com.lingoking.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Profile> getListOfProfiles() {
        List<Profile> profiles;
        profiles = ConnectionConfiguration.fetchAllProfilesFromDB();
        return profiles;
    }

    public Boolean login(Profile profile) {
        String hashedPassword;
        String salt;
        salt = ConnectionConfiguration.getSalt(profile.getEmailAddress());
        hashedPassword = PasswordHash.getPassword(profile.getPassword(), salt);
        profile.setPassword(hashedPassword);
        return ConnectionConfiguration.searchLoginCredentials(profile);
    }

    public Boolean checkEmail(String email) {
        return ConnectionConfiguration.searchInDB(email);
    }

    public Boolean deleteProfiles(String id) {
        ConnectionConfiguration.deleteProfilesFromDB(id);
        return true;
    }

    public List<Profile> deleteProfiles(List<String> ids) {
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
