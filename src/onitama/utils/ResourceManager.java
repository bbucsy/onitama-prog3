package onitama.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {

    private static final ResourceManager instance = new ResourceManager();
    private Map<String, BufferedImage> imgStore;

    private ResourceManager() {
        imgStore = new HashMap<>();
    }

    public static ResourceManager getInstance() {
        return instance;
    }

    public BufferedImage getResource(String name){
        return imgStore.get(name);
    }

    public void loadResources(String[] paths) throws IOException {
            for (String path : paths)
                imgStore.put( path, ImageIO.read(new File("res/" +path)));
    }


}
