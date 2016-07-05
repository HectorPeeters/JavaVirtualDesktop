package core.database;

import core.debug.Debug;

import java.sql.*;

public final class DatabaseManager {

    private static Connection currentConnection;

    public static void connect(String url, String username, String password) {
        Debug.logInfo("Connecting to database...");
        try {
            currentConnection = DriverManager.getConnection(url, username, password);
            Debug.logInfo("Successfully connected to database " + url);
        } catch (SQLException e) {
            Debug.logError("Failed to connect to database " + url);
        }
    }

    public static void addUser(String username, String password) {
        try {
            String statement = "INSERT INTO users (username, password) values (\'" + username + "\', \'" +  Hasher.hash(password) + "\')";
            Statement st = currentConnection.createStatement();
            st.execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean doesUserExist(String username) {
        try {
            String statement = "SELECT * FROM users WHERE `username` = \"" + username + "\"";
            Statement st = currentConnection.createStatement();
            ResultSet rs = st.executeQuery(statement);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getPassword(String username) {
        try {
            String statement = "SELECT password FROM users WHERE `username` = \"" + username + "\"";
            Statement st = currentConnection.createStatement();
            ResultSet rs = st.executeQuery(statement);
            if (rs.next()) {
                return rs.getString("password");
            } else {
                System.err.println("user " + username + " does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getRepoPath(String repoName) {
        try {
            String statement = "SELECT url FROM repos WHERE `name` = \"" + repoName + "\"";
            Statement st = currentConnection.createStatement();
            ResultSet rs = st.executeQuery(statement);
            if (rs.next()) {
                return rs.getString("password");
            } else {
                System.err.println("repo " + repoName + " does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

}
