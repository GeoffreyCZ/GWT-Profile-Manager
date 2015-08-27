package com.lingoking.server;


import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConfiguration {

    public static final String USER_NAME_DB = "geoffrey";
    public static final String PASSWORD_DB = "pdbpass159753";
    public static final String DB_IP = "134.119.42.29";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + DB_IP + "profileDB", USER_NAME_DB, PASSWORD_DB);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

}
