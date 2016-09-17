package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileIO {

    public static String readFile(String path) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Class.class.getResourceAsStream(path)));
        StringBuilder out = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                out.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    // FIXME: 7/6/16
    public static String[] getAllFilesInDir(String dir) {
        List<String> results = new ArrayList<>();
        File[] files = new File(dir).listFiles();
        for (File f : files)
            if (f.isFile())
                results.add(f.getPath());
        String[] resultArray = new String[results.size()];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = results.get(i);
        }
        return resultArray;
    }

    public static boolean createFile(String file) {
        File f = new File(file);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

}
