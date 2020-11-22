package onitama.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {

    protected BufferedImage bg;

    public ImagePanel(String path) {
        super();
        loadImage(path);
    }

    public ImagePanel() {
        super();
    }

    public ImagePanel(BufferedImage image){

    }

    protected void loadImage(String path) {
        try {
            bg = processImageBefore(ImageIO.read(new File(path)));
        } catch (IOException e) {
            System.out.println("Cannot find image");
        }
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

    public Image getBackgroundImage() {
        return bg;
    }

    public void setBackgroundImage(BufferedImage img) {
        bg = processImageBefore(img);
    }

}
