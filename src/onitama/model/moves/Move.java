package onitama.model.moves;

import onitama.model.Player;
import onitama.model.board.AbstractField;
import onitama.model.figures.Figure;

/**
 * Represents a move in the game. This class stores which figure is moving, where and  with what card.
 */
public class Move {

    /**
     * The figure that moves
     */
    private final Figure figure;
    /**
     * The destination field of the move
     */
    private final AbstractField destination;
    /**
     * The card used to create the move
     */
    private final MoveCard parentCard;
    /**
     * the owner of the moving figure
     */
    private final Player player;

    /**
     * Constructor of a Move
     * @param figure    The figure that moves
     * @param destination   The destination field of the move
     * @param parentCard    The card used to create the move
     */
    public Move(Figure figure, AbstractField destination, MoveCard parentCard) {
        this.figure = figure;
        this.destination = destination;
        this.parentCard = parentCard;
        this.player = figure.getPlayer();
    }

    /**
     *
     * @return The figure that moves
     */
    public Figure getFigure() {
        return figure;
    }

    /**
     *
     * @return The destination field of the move
     */
    public AbstractField getDestination() {
        return destination;
    }

    /**
     *
     * @return The destination field of the move
     */
    public MoveCard getParentCard() {
        return parentCard;
    }

    /**
     *
     * @return the owner of the moving figure
     */
    public Player getPlayer() {
        return player;
    }
}
