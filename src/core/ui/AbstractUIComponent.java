package core.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AbstractUIComponent {

    public boolean enabled = true;
    protected UIRect rect;

    public AbstractUIComponent(UIRect rect) {
        this.rect = rect;
    }

    public abstract boolean update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i);

    public abstract void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics);

}
