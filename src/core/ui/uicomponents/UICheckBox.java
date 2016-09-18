package core.ui.uicomponents;

import core.assets.Loader;
import core.assets.ScalableImage;
import core.ui.AbstractUIComponent;
import core.ui.UIRect;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class UICheckBox extends AbstractUIComponent {

    private boolean checked;

    private ScalableImage boxImage;
    private Image crossImage;

    public UICheckBox(UIRect rect) {
        super(rect);
        boxImage = new ScalableImage(Loader.getSpritesheetImage("grey_box.png"), 7, 7, 7, 7);
        crossImage = Loader.getSpritesheetImage("grey_checkmarkGrey.png");
    }

    public boolean update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        Input input = gameContainer.getInput();
        if (rect.contains(input.getMouseX(), input.getMouseY())) {
            if (input.isMousePressed(0))
                checked = !checked;
        }
        return false;
    }

    public void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        boxImage.draw(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        if (checked)
            crossImage.draw(rect.getX() + rect.getWidth() / 4, rect.getY() + rect.getHeight() / 4, rect.getWidth() / 2, rect.getHeight() / 2);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
