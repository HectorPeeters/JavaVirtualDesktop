package core.ui.uicomponents;

import core.InputKeyListener;
import core.assets.Loader;
import core.assets.ScalableImage;
import core.ui.AbstractUIComponent;
import core.ui.UIRect;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class UITextField extends AbstractUIComponent {

    protected boolean focused;
    private String text = "";
    private String showingText;

    protected ScalableImage boxImage;

    private InputKeyListener inputKeyListener;

    public UITextField(UIRect rect) {
        super(rect);
        boxImage = new ScalableImage(Loader.getSpritesheetImage("grey_box.png"), 7, 7, 7, 7);
    }

    public boolean update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        if (inputKeyListener == null) {
            inputKeyListener = new InputKeyListener();
            gameContainer.getInput().addKeyListener(inputKeyListener);
        }
        Input input = gameContainer.getInput();
        if (input.isMousePressed(0)) {
            focused = rect.contains(input.getMouseX(), input.getMouseY());
            inputKeyListener.setEnabled(focused);
        }
        if (focused) {
            text = inputKeyListener.getString();
            return true;
        }
        return false;
    }

    protected String getShowString(GameContainer gameContainer) {
        showingText = text;
        while (gameContainer.getDefaultFont().getWidth(showingText) >= rect.width - 8) {
            showingText = showingText.substring(1, showingText.length());
        }
        return showingText;
    }

    public void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        boxImage.draw(rect.x, rect.y, rect.width, rect.height);
        graphics.drawString(getShowString(gameContainer), rect.x + 4, rect.y);
        if (focused)
            graphics.fillRect(rect.x + gameContainer.getDefaultFont().getWidth(showingText) + 4, rect.y, 1, 18);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
