package server;

import utils.FileIO;

import java.util.HashMap;
import java.util.Map;

public class Users {

    //// FIXME: 9/19/16 file loading doesn't work

    private static Map<String, String> users = new HashMap<>();

    public static boolean doesUserExist(String username) {
        if (!users.containsKey(username))
            loadUser(username);
        return users.keySet().contains(username);
    }

    public static String getPassword(String username) {
        return users.get(username);
    }

    public static void loadUser(String username) {
        if (!FileIO.doesFileExist("res/users/" + username + ".usr")) {
            return;
        }
        String password = FileIO.readFile("res/users/" + username + ".usr").replaceAll("\n", "");
        users.put(username, password);
    }

}
