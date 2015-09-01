package com.lingoking.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import java.util.ArrayList;
import java.util.HashMap;

import com.lingoking.client.ProfilesService;
import com.lingoking.shared.model.Profile;

@SuppressWarnings("serial")
public class ProfilesServiceImpl extends RemoteServiceServlet implements
        ProfilesService {

    private static final String[] profilesFirstNameData = new String[] {
            "Hollie", "Emerson", "Healy", "Brigitte", "Elba", "Claudio",
            "Dena", "Christina", "Gail", "Orville", "Rae", "Mildred",
            "Candice", "Louise", "Emilio", "Geneva", "Heriberto", "Bulrush",
            "Abigail", "Chad", "Terry", "Bell"};

    private final String[] profilesLastNameData = new String[] {
            "Voss", "Milton", "Colette", "Cobb", "Lockhart", "Engle",
            "Pacheco", "Blake", "Horton", "Daniel", "Childers", "Starnes",
            "Carson", "Kelchner", "Hutchinson", "Underwood", "Rush", "Bouchard",
            "Louis", "Andrews", "English", "Snedden"};

    private final String[] profilesEmailData = new String[] {
            "mark@example.com", "hollie@example.com", "boticario@example.com",
            "emerson@example.com", "healy@example.com", "brigitte@example.com",
            "elba@example.com", "claudio@example.com", "dena@example.com",
            "brasilsp@example.com", "parker@example.com", "derbvktqsr@example.com",
            "qetlyxxogg@example.com", "antenas_sul@example.com",
            "cblake@example.com", "gailh@example.com", "orville@example.com",
            "post_master@example.com", "rchilders@example.com", "buster@example.com",
            "user31065@example.com", "ftsgeolbx@example.com"};

    private final HashMap<String, Profile> profiles = new HashMap<String, Profile>();

    public ProfilesServiceImpl() {
        initProfiles();
    }

    private void initProfiles() {
        for (int i = 0; i < profilesFirstNameData.length && i < profilesLastNameData.length && i < profilesEmailData.length; ++i) {
            Profile profile = new Profile(String.valueOf(i), profilesFirstNameData[i], profilesLastNameData[i], profilesEmailData[i]);
            profiles.put(profile.getId(), profile);
        }
    }

    public Profile createProfile (Profile profile) {
        ConnectionConfiguration.insertIntoDB(profile);
        return profile;
    }

    public Profile editProfile(Profile profile) {
        profiles.remove(profile.getId());
        profiles.put(profile.getId(), profile);
        return profile;
    }

    public ArrayList<Profile> getListOfProfiles() {
        ArrayList<Profile> profiles;
        profiles = ConnectionConfiguration.fetchAllProfilesFromDB();
        return profiles;
    }

    public Boolean deleteProfile(String id) {
        profiles.remove(id);
        return true;
    }

    public ArrayList<Profile> deleteProfiles(ArrayList<String> ids) {

        for (int i = 0; i < ids.size(); ++i) {
            deleteProfile(ids.get(i));        }

        return getListOfProfiles();
    }

    public Profile getProfile(String id) {
        return profiles.get(id);
    }
}
