package core.ui.uicomponents;

import core.assets.ScalableImage;
import core.ui.AbstractUIComponent;
import core.ui.UIRect;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class UIImage extends AbstractUIComponent {

    private ScalableImage image;

    public UIImage(Image image, UIRect rect) {
        super(rect);
        this.image = new ScalableImage(image, 0, 0, 0, 0);
    }

    public UIImage(ScalableImage image, UIRect rect) {
        super(rect);
        this.image = image;
    }

    public boolean update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        return false;
    }

    public void draw(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        image.draw(rect.x, rect.y, rect.width, rect.height);
    }
}