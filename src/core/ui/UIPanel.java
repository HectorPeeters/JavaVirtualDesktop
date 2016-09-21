package core.ui;

import core.assets.Loader;
import core.assets.ScalableImage;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;

public class UIPanel {

    private List<AbstractUIComponent> uiComponents = new ArrayList<>();

    private int minWidth = 120;
    private int minHeight = 40;

    private boolean closed;

    private int prevX;
    private int prevY;
    private boolean isDragging;
    private boolean resizing;

    private UIRect rect;
    private UIRect moveRect;
    private UIRect resizeRect;
    private UIRect closeRect;

    private ScalableImage backgroundImage;
    private Image closeIcon;

    private String title = "title";
    private boolean resizable;

    public UIPanel(UIRect rect) {
        this.rect = rect;
        moveRect = new UIRect(rect.x, rect.y, rect.width, 20);
        resizeRect = new UIRect(rect.x + rect.width - 20, rect.y + rect.height - 20, 20, 20);
        closeRect = new UIRect(rect.x + rect.width - 16, rect.y + 4, 12, 12);

        backgroundImage = new ScalableImage(Loader.getSpritesheetImage("grey_panel.png"), 7, 7, 7, 7);
        closeIcon = Loader.getSpritesheetImage("grey_crossGrey.png");
    }

    public boolean update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        boolean hasUpdated = false;
        for (AbstractUIComponent uiComponent : uiComponents)
            if (uiComponent.update(gameContainer, stateBasedGame, i)) {
                hasUpdated = true;
                break;
            }

        Input input = gameContainer.getInput();
        if (input.isMouseButtonDown(0)) {
            if (closeRect.contains(input.getMouseX(), input.getMouseY()) && !isDragging && !resizing) {
                close();
                hasUpdated = true;
            } else if ((moveRect.contains(input.getMouseX(), input.getMouseY()) || isDragging) && !resizing) {
                move(input.getMouseX() - prevX, input.getMouseY() - prevY);
                isDragging = true;
                resizing = false;
                hasUpdated = true;
            } else if ((resizeRect.contains(input.getMouseX(), input.getMouseY()) || resizing) && !isDragging) {
                if (resizable) {
                    resize(input.getMouseX() - prevX, input.getMouseY() - prevY);
                    resizing = true;
                    isDragging = false;
                    hasUpdated = true;
                }
            } else if (rect.contains(prevX, prevY)) {
                hasUpdated = true;
            }
        } else {
            isDragging = false;
            resizing = false;
        }
        prevX = input.getMouseX();
        prevY = input.getMouseY();

        return hasUpdated;
    }

    private void resize(int changeWidth, int changeHeight) {
        rect.width += changeWidth;
        rect.height += changeHeight;
        if (rect.width < minWidth)
            rect.width = minWidth;
        if (rect.height < minHeight)
            rect.height = minHeight;
        moveRect = new UIRect(rect.x, rect.y, rect.width, 20);
        resizeRect = new UIRect(rect.x + rect.width - 20, rect.y + rect.height - 20, 20, 20);
        closeRect = new UIRect(rect.x + rect.width - 16, rect.y + 4, 12, 12);
    }

    private void move(int changeX, int changeY) {
        rect.x += changeX;
        rect.y += changeY;
        moveRect.x += changeX;
        moveRect.y += changeY;
        resizeRect.x += changeX;
        resizeRect.y += changeY;
        closeRect.x += changeX;
        closeRect.y += changeY;
        for (AbstractUIComponent uiComponent : uiComponents) {
            uiComponent.rect.x += changeX;
            uiComponent.rect.y += changeY;
        }
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        backgroundImage.draw(rect.x, rect.y, rect.width, rect.height);
        closeIcon.draw(closeRect.x, closeRect.y, closeRect.width, closeRect.height);
        graphics.setColor(Color.black);
        graphics.drawString(title, rect.x + 4, rect.y);

        uiComponents.stream().filter(uiComponent -> uiComponent.enabled).forEach(uiComponent -> uiComponent.draw(gameContainer, stateBasedGame, graphics));
    }

    public void addComponent(AbstractUIComponent uiComponent) {
        uiComponent.rect.x += rect.x;
        uiComponent.rect.y += rect.y;
        uiComponents.add(uiComponent);
    }

    public void setSize(int width, int height) {
        resize(width - rect.width, height - rect.height);
    }

    public void setLocation(int x, int y) {
        move(x - rect.x, y - rect.y);
    }

    public UIRect getRect() {
        return rect;
    }

    public void setMinSize(int minWidth, int minHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
    }

    public void close() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }
}
