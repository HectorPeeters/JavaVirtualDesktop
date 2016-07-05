package core.debug;

import org.newdawn.slick.Color;

public enum LogColor {

    RED(Color.red),
    GREEN(Color.green);


    private final Color color;

    LogColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
