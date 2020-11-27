package onitama.view;

import onitama.model.figures.FigureType;
import onitama.model.figures.Figure;
import onitama.model.moves.Move;
import onitama.utils.ImagePanel;
import onitama.utils.SubjectObserver;


import java.awt.*;
import java.awt.image.BufferedImage;

public class SquarePanel extends ImagePanel implements SubjectObserver<Figure> {

    public enum HighlightLevel{
        NO,
        MEDIUM,
        HIGH
    }

    private HighlightLevel highlight = HighlightLevel.NO;
    private final Point position;
    private FigureType figure = FigureType.None;
    private Color figureColor;
    private Move possibleMove;

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
        if(figure != FigureType.None && figureColor != null)
            paintFigure(g);
    }

    @Override
    public void update(Figure message) {
        if (message == null) this.figure = FigureType.None;
        else{
            this.figure = message.getFigureType();
            this.figureColor = message.getPlayer().getColor();
        }
        this.repaint();
    }

    private void paintFigure(Graphics g){
        Color temp = g.getColor();
        g.setColor(figureColor);
        Dimension d = this.getSize();
        switch (figure){
            case Apprentice:
                g.fillRect((int)(d.width* 0.2),(int)(d.height*0.2),(int)(d.width*0.6),(int)(d.height*0.6));
                break;
            case Master:
                int[] x = new int[] {(int)(d.width*0.25),(int)(d.width*0.5),(int)(d.width*0.75)};
                int[] y = new int[] {(int)(d.height*0.25),(int)(d.height*0.75),(int)(d.height*0.25)};
                g.fillPolygon(new Polygon(x,y,3));
        }
        g.setColor(temp);
    }

    private Color getHighlightColor(){
        switch (highlight){
            case MEDIUM: return new Color(255,165,0,125);
            case HIGH: return new Color(128,0,0,160);
            default: return new Color(0,0,0,0);
        }
    }

    public Move getPossibleMove() {
        return possibleMove;
    }

    public void setPossibleMove(Move possibleMove) {
        this.possibleMove = possibleMove;
    }

    public void setHighlight(HighlightLevel highlight) {
        this.highlight = highlight;
        this.repaint();
    }
}
