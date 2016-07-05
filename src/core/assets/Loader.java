package core.assets;

import core.debug.Debug;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Loader {

    public static int loadedImages;

    private static final Map<String, Image> images = new HashMap<>();
    private static final Map<String, Image> spritesheetImages = new HashMap<>();

    private static final HashMap<Element, Image> spritesheetsToLoad = new HashMap<>();

    public static void queueSpritesheet(String xmlFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Class.class.getResourceAsStream(xmlFile));

            Node rootElement = doc.getDocumentElement();
            NodeList childNodes = rootElement.getChildNodes();

            String imageFile = new File(xmlFile).getParentFile().getPath() + "/" + ((Element) rootElement).getAttribute("imagePath");
            Image image = getImage(imageFile);

            for (int i = 0; i < childNodes.getLength(); i++) {
                Node n = childNodes.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE)
                    spritesheetsToLoad.put((Element) n, image);
            }

        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static boolean load() {
        if (!spritesheetsToLoad.isEmpty()) {
            Iterator iterator = spritesheetsToLoad.keySet().iterator();
            Element e = (Element) iterator.next();
            Image image = spritesheetsToLoad.get(e);
            String name = e.getAttribute("n");
            int x = Integer.parseInt(e.getAttribute("x"));
            int y = Integer.parseInt(e.getAttribute("y"));
            int width = Integer.parseInt(e.getAttribute("w"));
            int height = Integer.parseInt(e.getAttribute("h"));
            spritesheetImages.put(name, image.getSubImage(x, y, width, height));
            iterator.remove();
            loadedImages++;
            Debug.logInfo("Loaded spritesheet image " + name);
        }
        return spritesheetsToLoad.isEmpty();
    }

    public static Image getSpritesheetImage(String name) {
        if (spritesheetImages.containsKey(name)) {
            return spritesheetImages.get(name);
        } else {
            Debug.logError("Loader - spritesheetImage " + name + " does not exist");
            return null;
        }
    }

    public static Image getImage(String path) {
        if (!images.containsKey(path)) {
            try {
                Image image = new Image(Class.class.getResourceAsStream(path), new File(path).getName(), false);
                images.put(path, image);
                loadedImages++;
                Debug.logInfo("Successfully loaded image " + path);
            } catch (SlickException e) {
                e.printStackTrace();
                return null;
            }
        }
        return images.get(path);
    }

}
