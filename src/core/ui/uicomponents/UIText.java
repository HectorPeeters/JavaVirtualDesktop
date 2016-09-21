package core.ui.uicomponents;

import core.ui.AbstractUIComponent;
import core.ui.UIRect;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class UIText extends AbstractUIComponent {

    private String text = "";

    public UIText(String text, int x, int y) {
        super(new UIRect(x, y, 0, 0));
        this.text = text;
    }

    public boolean update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        return false;
    }

    public void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawString(text, rect.x, rect.y);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
