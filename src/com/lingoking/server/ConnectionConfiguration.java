package com.lingoking.server;


import com.lingoking.shared.model.Profile;
import com.lingoking.shared.model.ProfileDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConnectionConfiguration {

    public static final String USER_NAME_DB = "root"; //"geoffrey";
    public static final String PASSWORD_DB = ""; //"pdbpass159753";
    public static final String DB_IP = "127.0.0.1"; // 134.119.42.29";
    public static final String DB_NAME = "profileDB";
    public static final String TABLE_NAME = "profiles";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + "/" + DB_NAME, USER_NAME_DB, PASSWORD_DB);
        } catch (Exception e) {
            System.out.println("Connection to the DB failed: " + e);
        }
        return connection;
    }

    public static void insertIntoDB(Profile profile) {
        Connection connection;
        Statement statement;
        try {
            connection = getConnection();
            statement = connection.createStatement();

            String sql = "INSERT INTO " + DB_NAME + "." + TABLE_NAME + " VALUES (null , '" +
                    profile.getFirstName() + "', '" +
                    profile.getLastName() + "', '" +
                    profile.getEmail() + "');";
            statement.executeUpdate(sql);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static ArrayList<Profile> fetchAllProfilesFromDB() {
        ArrayList<Profile> listOfProfiles = new ArrayList<>();
        Connection connection;
        Statement statement;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            String sql = "SELECT firstName, lastName, email FROM " + DB_NAME + "." + TABLE_NAME + ";";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Profile profile = new Profile(null, rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"));
                listOfProfiles.add(profile);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return listOfProfiles;
    }
}
