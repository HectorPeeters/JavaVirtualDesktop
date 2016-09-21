package core.ui.uicomponents;

import core.ui.UIRect;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class UIPasswordField extends UITextField {

    private String text = "";
    private String showingText;

    public UIPasswordField(UIRect rect) {
        super(rect);
    }

    public void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        boxImage.draw(rect.x, rect.y, rect.width, rect.height);
        graphics.drawString(getShowString(gameContainer), rect.x + 4, rect.y);
        if (focused)
            graphics.fillRect(rect.x + gameContainer.getDefaultFont().getWidth(showingText) + 4, rect.y, 1, 18);
    }

    private String getPasswordString(String password) {
        return new String(new char[password.length()]).replace("\0", "*");
    }

}
