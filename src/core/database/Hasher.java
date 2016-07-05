package core.database;

public final class Hasher {

    public static String hash(String password) {
        return password.hashCode() + "";
    }

}
