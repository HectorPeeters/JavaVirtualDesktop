package core.programs;

import core.Settings;
import core.ui.UIPanel;
import core.ui.UIRect;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public abstract class AbstractProgram {

    protected UIPanel panel;

    public AbstractProgram(String title) {
        this.panel = new UIPanel(new UIRect(0, 0, 600, 400));
        panel.setTitle(title);
        initUI();
        setLocationNull();
    }

    abstract void initUI();

    abstract void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i);

    public boolean updateProgram(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        update(gameContainer, stateBasedGame, i);
        return panel.update(gameContainer, stateBasedGame, i);
    }

    public void renderProgram(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        panel.render(gameContainer, stateBasedGame, graphics);
    }

    public void setLocationNull() {
        panel.setLocation((Settings.WIDTH - panel.getRect().width) / 2, (Settings.HEIGHT - panel.getRect().height) / 2);
    }

    public void terminate() {
        panel.close();
    }

    public boolean isTerminated() {
        return panel.isClosed();
    }

}