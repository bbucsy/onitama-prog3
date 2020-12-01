package onitama.model.board;

import onitama.model.figures.Apprentice;
import onitama.model.figures.Figure;
import onitama.model.figures.Master;

import java.awt.*;

/**
 * The most basic type of field
 */
public class SimpleField extends AbstractField {

    /**
     * The containing board.
     */
    private final Board board;
    /**
     * The position of the field.
     */
    private final Point pos;
    /**
     * The figure currently on the field
     */
    private Figure currentFigure;

    /**
     * Constructor of the field
     *
     * @param b The containing board
     * @param p The position of the field
     */
    public SimpleField(Board b, Point p) {
        pos = p;
        board = b;
    }


    /**
     * Accepts an Apprentice to the field, and there is already a figure
     * occupying the field, the field collides them
     *
     * @param a The Apprentice that wants to move to this square
     */
    @Override
    public void accept(Apprentice a) {
        if (currentFigure != null)
            currentFigure.hitByFigure(a);
        setFigure(a);
    }

    /**
     * Accepts a Master to the field, and there is already a figure
     * occupying the field, the field collides them
     *
     * @param m The Master that wants to move to this square
     */
    @Override
    public void accept(Master m) {
        if (currentFigure != null)
            currentFigure.hitByFigure(m);
        setFigure(m);
    }

    /**
     * @return The figure currently on this field
     */
    @Override
    public Figure getFigure() {
        return currentFigure;
    }

    /**
     * @param f The figure to be set on this field
     */
    @Override
    public void setFigure(Figure f) {
        currentFigure = f;
        this.fireUpdated();
    }

    /**
     * @return The position of the field on the board
     */
    @Override
    public Point getPosition() {
        return pos;
    }

    /**
     * @return The board containing this field
     */
    @Override
    public Board getBoard() {
        return board;
    }

    /**
     * This is the function that generates the message, when the field is updated in some way.
     *
     * @return The message that is forwarded to the observers
     */
    @Override
    protected Figure getMessage() {
        return currentFigure;
    }
}
