package onitama.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A singleton containing all the necessary image resources.
 * Because the program doesn't use a lot of images, its easier to load them at the beginning of the
 * applications lifecycle, and use this singleton to access them.
 * If an item requests a resource that is not loaded it can
 * generate a placeholder so that the program doesn't crashes.
 */
public class ImageResourceManager {

    /**
     * Constant size of the placeholder image
     */
    private static final int PLACEHOLDER_S = 500;
    /**
     * The placeholder is a checked board, this constant gives the number of squares
     */
    private static final int PLACEHOLDER_GRID = 10;

    /**
     * The instance of the class
     */
    private static final ImageResourceManager instance = new ImageResourceManager();

    /**
     * A map that contains all the images with their names as the kex
     */
    private final Map<String, BufferedImage> imgStore;

    /**
     * Private constructor
     */
    private ImageResourceManager() {
        imgStore = new HashMap<>();
    }

    /**
     *
     * @return The instance of the class
     */
    public static ImageResourceManager getInstance() {
        return instance;
    }

    /**
     * Loads the resources given. The function searches the resources
     * in the 'res' folder.
     * @param paths An array of strings containing the names of the resources.
     */
    public void loadResources(String[] paths){
        for (String path : paths){
            try {
                imgStore.put(path, ImageIO.read(new File("res/" + path)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns an image with a given name. If it wasn't loaded before
     * the function will generate a placeholder image
     * @param name  The name of the image
     * @return  The image resource with that name
     */
    public BufferedImage getResource(String name) {
        BufferedImage img = imgStore.get(name);

        if (img == null) {
            // if file is not found, generate a placeholder image
            img = new BufferedImage(PLACEHOLDER_S, PLACEHOLDER_S, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.createGraphics();
            int s_size = PLACEHOLDER_S / PLACEHOLDER_GRID;
            for (int i = 0; i < PLACEHOLDER_GRID; i++) {
                for (int j = 0; j < PLACEHOLDER_GRID; j++) {
                    g.setColor((i % 2 == j % 2) ? Color.black : Color.magenta);
                    g.fillRect(i * s_size, j * s_size, (i + 1) * s_size, (j + 1) * s_size);
                }
            }
            g.dispose();
        }
        return img;
    }


}
