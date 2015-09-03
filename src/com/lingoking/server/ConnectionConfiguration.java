package com.lingoking.server;

import com.lingoking.shared.model.Address;
import com.lingoking.shared.model.Profile;

import java.sql.*;
import java.util.ArrayList;

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
        } catch (Exception se) {
            System.out.println("Connection to the DB failed: " + se);
        }
        return connection;
    }

    public static void insertIntoDB(Profile profile) {
        Connection connection;
        Statement statement;
        connection = getConnection();
        try {
            statement = connection.createStatement();
            String sql = "INSERT INTO " + DB_NAME + "." + TABLE_NAME + " VALUES (null , '" +
                    profile.getFirstName() + "', '" +
                    profile.getLastName() + "', '" +
                    profile.getEmail() + "', '" +
                    profile.getPassword() + "', '" +
                    profile.getPhoneNumber() + "', '" +
                    profile.getAddress().getStreet() + "', '" +
                    profile.getAddress().getStreetNumber() + "', '" +
                    profile.getAddress().getCity() + "', '" +
                    profile.getAddress().getPostcode() + "', '" +
                    profile.getAvatar() + "');";
            System.out.println(sql);
            statement.executeUpdate(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Profile> fetchAllProfilesFromDB() {
        ArrayList<Profile> listOfProfiles = new ArrayList<>();
        Connection connection;
        Statement statement;
        connection = getConnection();
        try {
            statement = connection.createStatement();
            String sql = "SELECT id, firstName, lastName, email FROM " + DB_NAME + "." + TABLE_NAME + ";";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Profile profile = new Profile(rs.getString("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"));
                listOfProfiles.add(profile);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listOfProfiles;
    }

    public static void deleteProfilesFromDB(String ids) {
        Connection connection;
        Statement statement;
        connection = getConnection();
        try {
            statement = connection.createStatement();
            String sql = "DELETE FROM " + DB_NAME + "." + TABLE_NAME + " WHERE id IN (" + ids + ");";
            statement.executeUpdate(sql);
            System.out.println(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Profile editProfileInDB(String id, Profile newProfileData) {
        Connection connection;
        Statement statement;
        connection = getConnection();
        try {
            statement = connection.createStatement();
            String sql = "UPDATE " + DB_NAME + "." + TABLE_NAME + " SET " +
                    "firstName = '" + newProfileData.getFirstName() +
                    "', lastName = '" + newProfileData.getLastName() +
                    "', email = '" + newProfileData.getEmail() +
                    "', password = '" + newProfileData.getPassword() +
                    "', phoneNumber = '" + newProfileData.getPhoneNumber() +
                    "', street = '" + newProfileData.getAddress().getStreet() +
                    "', streetNumber = '" + newProfileData.getAddress().getStreetNumber() +
                    "', city = '" + newProfileData.getAddress().getCity() +
                    "', postcode = '" + newProfileData.getAddress().getPostcode() +"' WHERE id = " + id + ";";
            statement.executeUpdate(sql);
            System.out.println(sql);
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
            connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newProfileData;
    }

    public static Profile fetchProfileFromDB(String id) {
        Connection connection;
        Statement statement;
        connection = getConnection();
        Profile profile = new Profile();
        Address address = new Address();
        try {
            statement = connection.createStatement();
            String sql = "SELECT firstName, lastName, email, phoneNumber, street, streetNumber, city, postcode FROM " + DB_NAME + "." + TABLE_NAME + " WHERE id = " + id + ";";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                profile.setFirstName(rs.getString("firstName"));
                profile.setLastName(rs.getString("lastName"));
                profile.setEmail(rs.getString("email"));
                profile.setPhoneNumber(rs.getString("phoneNumber"));
                address.setStreet(rs.getString("street"));
                address.setStreetNumber(rs.getString("streetNumber"));
                address.setCity(rs.getString("city"));
                address.setPostcode(rs.getString("postcode"));
                profile.setAddress(address);
            } else {
                System.out.println("Error fetching profile from DB");
            }

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return profile;
    }
}
