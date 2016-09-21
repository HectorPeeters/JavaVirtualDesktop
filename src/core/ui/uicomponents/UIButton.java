package core.ui.uicomponents;

import core.assets.Loader;
import core.assets.ScalableImage;
import core.ui.AbstractUIComponent;
import core.ui.UIRect;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class UIButton extends AbstractUIComponent {

    private ScalableImage image;
    private ScalableImage hoverImage;
    private ScalableImage pressedImage;

    private boolean pressed;
    private boolean prevDown;
    private boolean down;
    private boolean hover;

    public UIButton(UIRect rect) {
        super(rect);
        image = new ScalableImage(Loader.getSpritesheetImage("grey_button00.png"), 7, 7, 7, 7);
        hoverImage = new ScalableImage(Loader.getSpritesheetImage("grey_button00.png"), 7, 7, 7, 7);
        pressedImage = new ScalableImage(Loader.getSpritesheetImage("grey_button02.png"), 7, 7, 7, 7);
    }

    public boolean update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        Input input = gameContainer.getInput();
        if (rect.contains(input.getMouseX(), input.getMouseY())) {
            hover = true;
            down = input.isMouseButtonDown(0);
        } else {
            hover = false;
            down = false;
        }

        pressed = !prevDown && down;
        prevDown = down;
        return false;
    }

    public void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        if (!hover) {
            image.draw(rect.x, rect.y, rect.width, rect.height);
        } else {
            if (down) {
                pressedImage.draw(rect.x, rect.y, rect.width, rect.height);
            } else {
                hoverImage.draw(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }

    public boolean isDown() {
        return down;
    }

    public boolean isPressed() {
        return pressed;
    }
}