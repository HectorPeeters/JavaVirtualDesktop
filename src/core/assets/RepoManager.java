package core.assets;

import core.FileIO;
import core.Settings;
import core.database.DatabaseManager;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public final class RepoManager {

    public static void loadRepos() {
        String[] allFiles = FileIO.getAllFilesInDir(Settings.REPO_FOLDER);
        for (String s : allFiles)
            System.out.println(s);
    }

    public static boolean installRepo(String name) {
        String type = DatabaseManager.getRepoType(name);
        String url = DatabaseManager.getRepoUrl(name);
        if (url.equals(""))
            return false;
        return installRepo(name, type, url);
    }

    public static boolean installRepo(String name, String type, String url) {
        try {
            FileIO.createFile(Settings.REPO_FOLDER + name + ".rep");

            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(Settings.REPO_FOLDER + name + ".rep");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeRepo(String name) {
        File f = new File(Settings.REPO_FOLDER + name + ".rep");
        boolean deleted = f.exists();
        f.delete();
        return deleted;
    }
}
