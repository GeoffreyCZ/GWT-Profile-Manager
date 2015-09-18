package com.lingoking.server;

import com.lingoking.shared.model.Address;
import com.lingoking.shared.model.Profile;

import java.security.NoSuchAlgorithmException;
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
            connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + "/" + DB_NAME  + "?characterEncoding=UTF-8", USER_NAME_DB, PASSWORD_DB);
        } catch (Exception se) {
            System.out.println("Connection to the DB failed: " + se);
        }
        return connection;
    }

    public static void insertIntoDB(Profile profile) {
        String hashedPassword;
        String salt = null;
        try {
            salt = PasswordHash.getSalt();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        hashedPassword = PasswordHash.getPassword(profile.getPassword(), salt);

        Connection connection;
        PreparedStatement statement;
        connection = getConnection();
        try {
            String sql = "INSERT INTO " + DB_NAME + "." + TABLE_NAME + " VALUES (null , ?, '" +
            //                    profile.getFirstName() + "', '" +
                    profile.getLastName() + "', '" +
                    profile.getEmailAddress() + "', '" +
                    hashedPassword + "', '" +
                    profile.getPhoneNumber() + "', '" +
                    profile.getAddress().getStreet() + "', '" +
                    profile.getAddress().getStreetNumber() + "', '" +
                    profile.getAddress().getCity() + "', '" +
                    profile.getAddress().getPostcode() + "', '" +
                    profile.getAvatar() + "', '" +
                    salt + "');";
            statement = connection.prepareStatement(sql);
            System.out.println(sql);
            statement.setString(1, profile.getFirstName());
            statement.executeUpdate();
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

    public static Boolean searchInDB(String email) {
        Connection connection;
        Statement statement;
        connection = getConnection();
        try {
            statement = connection.createStatement();
            String sql = "SELECT emailAddress FROM " + DB_NAME + "." + TABLE_NAME + " WHERE emailAddress = '" + email + "';";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("emailAddress").equals(email)) {
                    return true;
                }
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
        return false;
    }

    public static String getSalt(String email) {
        Connection connection;
        Statement statement;
        connection = getConnection();
        String salt = "";
        try {
            statement = connection.createStatement();
            String sql = "SELECT salt FROM " + DB_NAME + "." + TABLE_NAME + " WHERE emailAddress = '" + email + "';";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                salt = rs.getString("salt");
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
        return salt;
    }

    public static Boolean searchLoginCredentials(Profile profile) {
        Connection connection;
        Statement statement;
        connection = getConnection();
        try {
            statement = connection.createStatement();
            String sql = "SELECT emailAddress, password FROM " + DB_NAME + "." + TABLE_NAME + " WHERE emailAddress = '"
                    + profile.getEmailAddress() + "' AND password = '" + profile.getPassword() + "';";
            ResultSet rs = statement.executeQuery(sql);
            System.out.println(sql);
            while (rs.next()) {
                System.out.println(rs.getString("emailAddress") + " " + rs.getString("password"));
                if (rs.getString("emailAddress").equals(profile.getEmailAddress())) {
                    return true;
                }
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
        return false;
    }

    public static ArrayList<Profile> fetchAllProfilesFromDB() {
        ArrayList<Profile> listOfProfiles = new ArrayList<>();
        Connection connection;
        Statement statement;
        connection = getConnection();
        try {
            statement = connection.createStatement();
            String sql = "SELECT id, firstName, lastName, emailAddress, avatarURL FROM " + DB_NAME + "." + TABLE_NAME + " ORDER BY firstName ASC, lastName ASC;";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Profile profile = new Profile(rs.getString("id"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("emailAddress"), rs.getString("avatarURL"));
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
                    "', emailAddress = '" + newProfileData.getEmailAddress() +
                    "', password = '" + newProfileData.getPassword() +
                    "', phoneNumber = '" + newProfileData.getPhoneNumber() +
                    "', street = '" + newProfileData.getAddress().getStreet() +
                    "', streetNumber = '" + newProfileData.getAddress().getStreetNumber() +
                    "', city = '" + newProfileData.getAddress().getCity() +
                    "', postcode = '" + newProfileData.getAddress().getPostcode() +
                    "', avatarURL = '" + newProfileData.getAvatar() +"' WHERE id = " + id + ";";
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
            String sql = "SELECT firstName, lastName, emailAddress, phoneNumber, street, streetNumber, city, postcode, avatarURL FROM " + DB_NAME + "." + TABLE_NAME + " WHERE id = " + id + ";";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                profile.setFirstName(rs.getString("firstName"));
                profile.setLastName(rs.getString("lastName"));
                profile.setEmailAddress(rs.getString("emailAddress"));
                profile.setPhoneNumber(rs.getString("phoneNumber"));
                address.setStreet(rs.getString("street"));
                address.setStreetNumber(rs.getString("streetNumber"));
                address.setCity(rs.getString("city"));
                address.setPostcode(rs.getString("postcode"));
                profile.setAddress(address);
                profile.setAvatar(rs.getString("avatarURL"));
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
