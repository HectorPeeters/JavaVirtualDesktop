package core.ui.uicomponents;

import core.assets.Loader;
import core.ui.AbstractUIComponent;
import core.ui.UIRect;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class UIHorizontalSlider extends AbstractUIComponent {

    private float value = 0f;
    private Image sliderImage;
    private Image knobImage;

    private float prevMouseX;
    private boolean dragging;

    public UIHorizontalSlider(UIRect rect) {
        super(rect);
        sliderImage = Loader.getSpritesheetImage("grey_sliderHorizontal.png");
        knobImage = Loader.getSpritesheetImage("grey_tickGrey.png");
    }

    public boolean update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        boolean returnValue = false;
        UIRect knobRect = new UIRect((int) (rect.x - 6 + value * rect.width), rect.y - 2, 12, 12);
        Input input = gameContainer.getInput();
        if (input.isMouseButtonDown(0)) {
            if (knobRect.contains(input.getMouseX(), input.getMouseY()) || dragging) {
                float change = input.getMouseX() - prevMouseX;
                value += change / rect.width;
                if (value > 1)
                    value = 1;
                if (value < 0)
                    value = 0;
                dragging = true;
                returnValue = true;
            }
        } else {
            dragging = false;
        }
        prevMouseX = input.getMouseX();
        return returnValue;
    }

    public void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        sliderImage.draw(rect.x, rect.y, rect.width, 8);
        knobImage.draw(rect.x - 6 + value * rect.width, rect.y - 2, 12, 12);
    }

    public float getValue() {
        return (float) Math.round(value * 100.0) / 100.0f;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
