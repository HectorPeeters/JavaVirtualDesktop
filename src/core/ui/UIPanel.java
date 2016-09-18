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
        moveRect = new UIRect(rect.getX(), rect.getY(), rect.getWidth(), 20);
        resizeRect = new UIRect(rect.getX() + rect.getWidth() - 20, rect.getY() + rect.getHeight() - 20, 20, 20);
        closeRect = new UIRect(rect.getX() + rect.getWidth() - 16, rect.getY() + 4, 12, 12);

        backgroundImage = new ScalableImage(Loader.getSpritesheetImage("grey_panel.png"), 7, 7, 7, 7);
        closeIcon = Loader.getSpritesheetImage("grey_crossGrey.png");
    }

    public boolean update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        boolean hasUpdated = false;
        for (AbstractUIComponent uiComponent : uiComponents)
            if (uiComponent.update(gameContainer, stateBasedGame, i) && !hasUpdated)
                hasUpdated = true;

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
        rect.setWidth(rect.getWidth() + changeWidth);
        rect.setHeight(rect.getHeight() + changeHeight);
        if (rect.getWidth() < minWidth)
            rect.setWidth(minWidth);
        if (rect.getHeight() < minHeight)
            rect.setHeight(minHeight);
        moveRect = new UIRect(rect.getX(), rect.getY(), rect.getWidth(), 20);
        resizeRect = new UIRect(rect.getX() + rect.getWidth() - 20, rect.getY() + rect.getHeight() - 20, 20, 20);
        closeRect = new UIRect(rect.getX() + rect.getWidth() - 16, rect.getY() + 4, 12, 12);
    }

    private void move(int changeX, int changeY) {
        rect.setX(rect.getX() + changeX);
        rect.setY(rect.getY() + changeY);
        moveRect.setX(moveRect.getX() + changeX);
        moveRect.setY(moveRect.getY() + changeY);
        resizeRect.setX(resizeRect.getX() + changeX);
        resizeRect.setY(resizeRect.getY() + changeY);
        closeRect.setX(closeRect.getX() + changeX);
        closeRect.setY(closeRect.getY() + changeY);
        for (AbstractUIComponent uiComponent : uiComponents) {
            uiComponent.rect.setX(uiComponent.rect.getX() + changeX);
            uiComponent.rect.setY(uiComponent.rect.getY() + changeY);
        }
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        backgroundImage.draw(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
        closeIcon.draw(closeRect.getX(), closeRect.getY(), closeRect.getWidth(), closeRect.getHeight());
        graphics.setColor(Color.black);
        graphics.drawString(title, rect.getX() + 4, rect.getY());

        for (AbstractUIComponent uiComponent : uiComponents)
            if (uiComponent.enabled)
                uiComponent.draw(gameContainer, stateBasedGame, graphics);
    }

    public void addComponent(AbstractUIComponent uiComponent) {
        uiComponent.rect.setX(uiComponent.rect.getX() + rect.getX());
        uiComponent.rect.setY(uiComponent.rect.getY() + rect.getY());
        uiComponents.add(uiComponent);
    }

    public void setSize(int width, int height) {
        resize(width - rect.getWidth(), height - rect.getHeight());
    }

    public void setLocation(int x, int y) {
        move(x - rect.getX(), y - rect.getY());
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
