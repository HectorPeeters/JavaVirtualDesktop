package core.states;

import java.util.ArrayList;
import java.util.List;

public final class States {

    private static List<String> states = new ArrayList<>();

    public static int getID(String name) {
        if (!states.contains(name))
            states.add(name);
        return states.indexOf(name);
    }

}
