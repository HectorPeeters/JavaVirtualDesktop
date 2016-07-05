package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

}
