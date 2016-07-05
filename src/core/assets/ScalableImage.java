package core.assets;

import org.newdawn.slick.Image;

public class ScalableImage {

    private int left;
    private int right;
    private int top;
    private int bottom;

    private Image topLeftImage;
    private Image topImage;
    private Image topRightImage;
    private Image leftImage;
    private Image middleImage;
    private Image rightImage;
    private Image bottomLeftImage;
    private Image bottomImage;
    private Image bottomRightImage;

    public ScalableImage(Image main, int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        generateImages(main);
    }

    private void generateImages(Image main) {
        int width = main.getWidth();
        int height = main.getHeight();

        //corners
        topLeftImage = main.getSubImage(0, 0, left, top);
        topRightImage = main.getSubImage(width - right, 0, right, top);
        bottomLeftImage = main.getSubImage(0, height - bottom, left, bottom);
        bottomRightImage = main.getSubImage(width - right, height - bottom, right, bottom);

        //edges
        topImage = main.getSubImage(left, 0, width - left - right, top);
        leftImage = main.getSubImage(0, top, left, height - top - bottom);
        rightImage = main.getSubImage(width - right, top, right, height - top - bottom);
        bottomImage = main.getSubImage(left, height - bottom, width - left - right, bottom);

        //middle
        middleImage = main.getSubImage(left, top, width - left - right, height - top - bottom);
    }

    public void draw(int x, int y, int width, int height) {
        //corners
        topLeftImage.draw(x, y);
        topRightImage.draw(x + width - right, y);
        bottomLeftImage.draw(x, y + height - bottom);
        bottomRightImage.draw(x + width - right, y + height - bottom);

        //edges
        topImage.draw(x + left, y, width - left - right, top);
        leftImage.draw(x, y + top, left, height - top - bottom);
        rightImage.draw(x + width - right, y + top, right, height - top - bottom);
        bottomImage.draw(x + left, y + height - bottom, width - left - right, bottom);

        //middle
        middleImage.draw(x + left, y + top, width - left - right, height - top - bottom);
    }
}
