package onitama.view;

import onitama.utils.ImagePanel;


import java.awt.*;
import java.awt.image.BufferedImage;

public class SquarePanel extends ImagePanel {

    public enum HighlightLevel{
        NO,
        MEDIUM,
        HIGH
    }

    private HighlightLevel highlight = HighlightLevel.NO;
    private Point position;

    public SquarePanel(Point position) {
        super();
        this.position = position;
        loadImage("res/board.png");
    }

    @Override
    protected BufferedImage processImageBefore(BufferedImage img) {
        int sHeight = img.getHeight(null)/5;
        int sWidth = img.getWidth(null) / 5;
        return img.getSubimage(position.y*sWidth,position.x * sHeight,sWidth,sHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (highlight != HighlightLevel.NO){
            Dimension d = getSize();
            Color temp = g.getColor();
            g.setColor(getHighlightColor());
            g.fillRect(0,0,d.width,d.height);
            g.setColor(temp);
        }
    }

    private Color getHighlightColor(){
        switch (highlight){
            case MEDIUM: return new Color(255,165,0,125);
            case HIGH: return new Color(128,0,0,160);
            default: return new Color(0,0,0,0);
        }
    }

    public void setHighlight(HighlightLevel highlight) {
        this.highlight = highlight;
    }
}
