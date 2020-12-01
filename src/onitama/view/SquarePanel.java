package onitama.view;

import onitama.model.figures.Figure;
import onitama.model.figures.FigureType;
import onitama.model.moves.Move;
import onitama.utils.ImagePanel;
import onitama.utils.SubjectObserver;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * View of a field in the board model.
 *
 * It extends an ImagePanel, to display the background, but overrides
 * the process image method, to show different parts of the background
 * depending its position in the board model.
 *
 * It also has the responsibility to draw out any character that is on the field model
 * and highlight itself when the controller tells it.
 */
public class SquarePanel extends ImagePanel implements SubjectObserver<Figure> {

    /**
     * The position of the square on the board
     */
    private final Point position;
    /**
     * The type of highlight required to be displayed
     */
    private HighlightLevel highlight = HighlightLevel.NO;
    /**
     * The type of figure that is on the square
     */
    private FigureType figure = FigureType.NONE;
    /**
     * The color of the figure
     */
    private Color figureColor;
    /**
     * If there is a possible and legal move to this field
     * the controller can store it int the view, so that it can be executed on click
     */
    private Move possibleMove;

    /**
     * Constructor of a square
     * @param position the position of the square in the board
     */
    public SquarePanel(Point position) {
        super();
        this.position = position;
        loadImage("board.png");

    }

    /**
     * Override of the parent class's method.
     * cuts the background into 5*5 little squares and selects the
     * one according to its position
     * @param img   The image to be processed
     * @return The process background image
     */
    @Override
    protected BufferedImage processImageBefore(BufferedImage img) {
        int sHeight = img.getHeight(null) / 5;
        int sWidth = img.getWidth(null) / 5;
        return img.getSubimage(position.y * sWidth, position.x * sHeight, sWidth, sHeight);
    }

    /**
     * On top of painting the background the object uses this method
     * to fisplay a highlighted state and/or a figure
     * @param g The graphics object that is used for the painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (highlight != HighlightLevel.NO) {
            Dimension d = getSize();
            Color temp = g.getColor();
            g.setColor(getHighlightColor());
            g.fillRect(0, 0, d.width, d.height);
            g.setColor(temp);
        }
        if (figure != FigureType.NONE && figureColor != null)
            paintFigure(g);
    }

    /**
     * When a field model is updated, this updates the view
     * @param message A message sent by the observed field
     */
    @Override
    public void update(Figure message) {
        if (message == null) this.figure = FigureType.NONE;
        else {
            this.figure = message.getFigureType();
            this.figureColor = message.getPlayer().getColor();
        }
        this.repaint();
    }

    /**
     * This is used to paint a figure to the square
     * @param g The graphics object that is used for the painting
     */
    private void paintFigure(Graphics g) {
        Color temp = g.getColor();
        g.setColor(figureColor);
        Dimension d = this.getSize();
        switch (figure) {
            case APPRENTICE:
                g.fillRect((int) (d.width * 0.2), (int) (d.height * 0.2), (int) (d.width * 0.6), (int) (d.height * 0.6));
                break;
            case MASTER:
                int[] x = new int[]{(int) (d.width * 0.25), (int) (d.width * 0.5), (int) (d.width * 0.75)};
                int[] y = new int[]{(int) (d.height * 0.25), (int) (d.height * 0.75), (int) (d.height * 0.25)};
                g.fillPolygon(new Polygon(x, y, 3));
        }
        g.setColor(temp);
    }

    /**
     *
     * @return The color corresponding to the level of highlighting
     */
    private Color getHighlightColor() {
        switch (highlight) {
            case MEDIUM:
                return new Color(255, 165, 0, 125);
            case HIGH:
                return new Color(128, 0, 0, 160);
            default:
                return new Color(0, 0, 0, 0);
        }
    }

    /**
     *
     * @return The stored move if any, that is aimed at this square
     */
    public Move getPossibleMove() {
        return possibleMove;
    }

    /**
     *
     * @param possibleMove The stored move, that is aimed at this square
     */
    public void setPossibleMove(Move possibleMove) {
        this.possibleMove = possibleMove;
    }

    /**
     *
     * @param highlight The type of highlighting that this square should display
     */
    public void setHighlight(HighlightLevel highlight) {
        this.highlight = highlight;
        this.repaint();
    }

    /**
     * An enum storing the types of highlighting a square can display
     */
    public enum HighlightLevel {
        NO,
        MEDIUM,
        HIGH
    }
}
