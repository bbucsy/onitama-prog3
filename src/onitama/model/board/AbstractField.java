package onitama.model.board;

import onitama.model.figures.Apprentice;
import onitama.model.figures.Figure;
import onitama.model.figures.Master;
import onitama.utils.ObservedSubject;

import java.awt.*;
import java.io.Serializable;

/**
 * An abstract representation of a Field(Square) on the board.
 * This extends <code>ObservedSubject</code>, so that every Field has to
 * implement the corresponding functions.
 */
public abstract class AbstractField extends ObservedSubject<Figure> implements Serializable {

    /**
     * Field accepts an Apprentice figure
     *
     * @param a The Apprentice that wants to move to this square
     */
    public abstract void accept(Apprentice a);

    /**
     * Field accepts a Master figure
     *
     * @param m The Master that wants to move to this square
     */
    public abstract void accept(Master m);

    /**
     * @return The board that this Field is part of
     */
    public abstract Board getBoard();

    /**
     * @return The figure that stays on this Field
     */
    public abstract Figure getFigure();

    /**
     * @param f The figure to be set on this Field.
     */
    public abstract void setFigure(Figure f);

    /**
     * @return The position of this Field on the containing board.
     */
    public abstract Point getPosition();

}
