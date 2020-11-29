package onitama.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    protected BufferedImage bg;

    public ImagePanel(String path) {
        super();
        loadImage(path);
    }

    public ImagePanel() {
        super();
    }

    protected void loadImage(String name) {
        bg = processImageBefore(ImageResourceManager.getInstance().getResource(name));
    }

    protected BufferedImage processImageBefore(BufferedImage img) {
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawImage(bg, 0, 0, d.width, d.height, null);
    }

}
