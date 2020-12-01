package onitama.model.figures;

import onitama.model.Player;
import onitama.model.board.AbstractField;

import java.io.Serializable;

/**
 * An interface of every figure in the game.
 */
public interface Figure extends Serializable {

    /**
     * @param f The destination of the movement
     */
    void moveTo(AbstractField f);

    /**
     * If another figure is trying to move to the field of this figure, they collide.
     *
     * @param f The figure trying to kill this figure.
     */
    void hitByFigure(Figure f);

    /**
     * @return The owner of the figure
     */
    Player getPlayer();

    /**
     * @return The field that this figure is on.
     */
    AbstractField getCurrentField();

    /**
     * Sets the currentField of this figure. Doesn't goeas through the procedure of moving a piece.
     * Used only in initializations.
     *
     * @param currentField The field that this figure is set to be on.
     */
    void setCurrentField(AbstractField currentField);

    /**
     * @return The type of the figure
     */
    FigureType getFigureType();
}
