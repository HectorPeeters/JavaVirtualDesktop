package core;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class InputKeyListener implements KeyListener {

    private boolean enabled;
    private String s = "";

    public void setString(String s) {
        this.s = s;
    }

    public String getString() {
        return s;
    }

    public void keyPressed(int i, char c) {
        if (enabled) {
            if (c == 8) {
                if (s != null && s.length() > 0)
                    s = s.substring(0, s.length() - 1);
            } else if (c == 13 || c == 9) {
            } else {
                s += c;
            }
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void keyReleased(int i, char c) {

    }

    public void setInput(Input input) {

    }

    public boolean isAcceptingInput() {
        return true;
    }

    public void inputEnded() {

    }

    public void inputStarted() {

    }
}
