package core.assets;

import org.newdawn.slick.Image;

public class SpriteSheet {

    private int xImages;
    private int yImages;
    private Image[] images;

    public SpriteSheet(Image original, int xImages, int yImages) {
        this.xImages = xImages;
        this.yImages = yImages;
        this.images = new Image[xImages * yImages];
        generate(original);
    }

    private void generate(Image original) {
        int imageWidth = original.getWidth() / xImages;
        int imageHeight = original.getHeight() / yImages;
        for (int x = 0; x < xImages; x++) {
            for (int y = 0; y < yImages; y++) {
                images[x + y * xImages] = original.getSubImage(x * imageWidth, y * imageHeight, imageWidth, imageHeight);
            }
        }
    }

    public Image getImageAt(int x, int y) {
        return images[x + y * xImages];
    }
}
