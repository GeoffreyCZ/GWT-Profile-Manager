package com.lingoking.shared.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProfileDetails implements Serializable {
    private String id;
    private String displayName;


    public ProfileDetails() {
        new ProfileDetails("0", "");
    }

    public ProfileDetails(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
}