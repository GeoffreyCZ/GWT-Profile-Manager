package com.lingoking.server;

import com.lingoking.shared.model.Address;
import com.lingoking.shared.model.Profile;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class ConnectionConfiguration {

    private static LoadConfiguration loadConfiguration = new LoadConfiguration(new File("config/config.xml"));
    private static final String dbUserName = loadConfiguration.findSymbol("dbUserName");
    private static final String dbPassword = loadConfiguration.findSymbol("dbPassword");
    private static final String dbIp = loadConfiguration.findSymbol("dbIp");
    private static final String dbName = loadConfiguration.findSymbol("dbName");
    private static final String tableName = loadConfiguration.findSymbol("tableName");

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + dbIp + "/" + dbName  +
                    "?characterEncoding=UTF-8", dbUserName, dbPassword);
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
            String sql = "INSERT INTO " + dbName + "." + tableName + " VALUES (null , ?, '" +
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

    public static Boolean searchInDB(String id, String email) {
        Connection connection;
        Statement statement;
        connection = getConnection();
        try {
            statement = connection.createStatement();
            String sql = "SELECT emailAddress FROM " + dbName + "." + tableName + " WHERE emailAddress = '"
                    + email + "' AND id != '" + id + "';";
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
            String sql = "SELECT salt FROM " + dbName + "." + tableName + " WHERE emailAddress = '" + email + "';";
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
            String sql = "SELECT emailAddress, password FROM " + dbName + "." + tableName + " WHERE emailAddress = '"
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
            String sql = "SELECT id, firstName, lastName, emailAddress, avatarURL FROM " + dbName + "." + tableName +
                    " ORDER BY firstName ASC, lastName ASC, emailAddress ASC;";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Profile profile = new Profile(rs.getString("id"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("emailAddress"), rs.getString("avatarURL"));
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
            String sql = "DELETE FROM " + dbName + "." + tableName + " WHERE id IN (" + ids + ");";
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
            String sql = "UPDATE " + dbName + "." + tableName + " SET " +
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
            String sql = "SELECT firstName, lastName, emailAddress, phoneNumber, street, streetNumber, city, postcode, " +
                    "avatarURL FROM " + dbName + "." + tableName + " WHERE id = " + id + ";";
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
