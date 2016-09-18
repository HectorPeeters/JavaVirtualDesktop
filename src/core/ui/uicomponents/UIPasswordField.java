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

public class UIPasswordField extends AbstractUIComponent {

    private boolean focused;
    private String text = "";
    private String showingText;

    private float currentCursorTime = 40;
    private boolean showCursor = true;

    private ScalableImage boxImage;

    private InputKeyListener inputKeyListener;

    public UIPasswordField(UIRect rect) {
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
        }
        return false;
    }

    private String getShowString(GameContainer gameContainer) {
        showingText = text;
        while (gameContainer.getDefaultFont().getWidth(showingText) >= rect.getWidth() - 8) {
            showingText = showingText.substring(1, showingText.length());
        }
        return getPasswordString(showingText);
    }

    public void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        boxImage.draw(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        graphics.drawString(getShowString(gameContainer), rect.getX() + 4, rect.getY());
        if (showCursor && focused)
            graphics.fillRect(rect.getX() + gameContainer.getDefaultFont().getWidth(showingText) + 4, rect.getY(), 1, 18);
    }

    private String getPasswordString(String password) {
        String result = "";
        for (int i = 0; i < password.length(); i++) {
            result += "*";
        }
        return result;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
