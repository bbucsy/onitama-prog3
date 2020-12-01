package onitama.model.board;

import onitama.model.Player;
import onitama.model.figures.Apprentice;

import java.awt.*;

/**
 * A regular Field, but it starts with an apprentice figure on it
 */
public class ApprenticeField extends SimpleField {

    /**
     * Constructor of the field.
     * Makes a figure, and adds it to the model.
     *
     * @param b     The containing board.
     * @param p     The position of the field.
     * @param owner The player that owns the starting figure
     */
    public ApprenticeField(Board b, Point p, Player owner) {
        super(b, p);

        Apprentice starter = new Apprentice(owner);
        starter.setCurrentField(this);
        this.setFigure(starter);
        owner.addFigure(starter);
    }
}
