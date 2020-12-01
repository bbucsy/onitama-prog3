package onitama.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class extends a JPanel and overrides the paintcomponent
 * method so that a background image can be added.
 */
public class ImagePanel extends JPanel {

    /**
     * The background image
     */
    protected BufferedImage bg;

    /**
     * Constructor of an image panel.
     * @param path The name of the image resource to be set as background
     */
    public ImagePanel(String path) {
        super();
        loadImage(path);
    }

    /**
     * Empty constructor
     */
    public ImagePanel() {
        super();
    }

    /**
     * Function to load an image from the ImageResourceManager singleton
     * @param name The name of the resource
     */
    protected void loadImage(String name) {
        bg = processImageBefore(ImageResourceManager.getInstance().getResource(name));
    }

    /**
     * If there are some need for processing the image this can be overriden
     * @param img   The image to be processed
     * @return  The processed image
     */
    protected BufferedImage processImageBefore(BufferedImage img) {
        return img;
    }

    /**
     * The overriden method to paint a background.
     * @param g The graphics object that is used for the painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawImage(bg, 0, 0, d.width, d.height, null);
    }

}
