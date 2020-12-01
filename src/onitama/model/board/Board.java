package onitama.model.board;

import onitama.model.Game;

import java.awt.*;
import java.io.Serializable;

/**
 * The model representation of the 5*5 size game board.
 */
public class Board implements Serializable {

    /**
     * A matrix of the Fields contained
     */
    private final AbstractField[][] fields;
    /**
     * The containing Game model
     */
    private final Game game;

    /**
     * Constructor of the board.
     * Creates the Matrix of fields, and sets the figures on their starting field.
     *
     * @param g The model containing the board
     */
    public Board(Game g) {
        game = g;
        fields = new AbstractField[5][5];
        createBoard();
    }

    /**
     * Creates the matrix of fields
     */
    private void createBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if (i == 0 || i == 4) {
                    if (j == 2) {
                        //Shrine field
                        fields[i][j] = new ShrineField(this, new Point(i, j), game.getPlayer(i / 4));
                    } else {
                        //Apprentice field
                        fields[i][j] = new ApprenticeField(this, new Point(i, j), game.getPlayer(i / 4));
                    }
                } else {
                    //every other field
                    fields[i][j] = new SimpleField(this, new Point(i, j));
                }
            }
        }
    }

    /**
     * @param i Row, starting from 0
     * @param j Column, starting from 0
     * @return The field in the [i][j] position
     */
    public AbstractField getField(int i, int j) {
        return fields[i][j];
    }

    /**
     * @param p Position
     * @return The field in p position
     */
    public AbstractField getField(Point p) {
        return getField(p.x, p.y);
    }

}
