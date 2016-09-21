package core.ui;

public class UIRect {

    public int x;
    public int y;
    public int width;
    public int height;

    public UIRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(int x, int y) {
        return (x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height);
    }

}
