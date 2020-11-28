package onitama.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

    private static final int PLACEHOLDER_S = 500;
    private static final int PLACEHOLDER_GRID = 10;

    private static final ResourceManager instance = new ResourceManager();
    private Map<String, BufferedImage> imgStore;

    private ResourceManager() {
        imgStore = new HashMap<>();
    }

    public static ResourceManager getInstance() {
        return instance;
    }

    public void loadResources(String[] paths) throws IOException {
            for (String path : paths)
                imgStore.put( path, ImageIO.read(new File("res/" +path)));
    }


    public BufferedImage getResource(String name){
        BufferedImage img = imgStore.get(name);

        if(img == null) {
            // if file is not found, generate a placeholder image
            img = new BufferedImage(PLACEHOLDER_S, PLACEHOLDER_S, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.createGraphics();
            int s_size = PLACEHOLDER_S / PLACEHOLDER_GRID;
            for (int i = 0; i < PLACEHOLDER_GRID; i++) {
                for (int j = 0; j < PLACEHOLDER_GRID; j++) {
                    g.setColor((i%2==j%2)?Color.black:Color.magenta);
                    g.fillRect(i*s_size,j*s_size,(i+1)*s_size,(j+1)*s_size);
                }
            }
            g.dispose();
        }
        return img;
    }



}
