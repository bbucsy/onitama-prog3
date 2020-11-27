package onitama.model.moves;

import onitama.model.Player;
import onitama.model.board.AbstractField;
import onitama.model.figures.Figure;

public class Move {

    private final Figure figure;
    private final AbstractField destination;
    private final MoveCard parentCard;
    private final Player player;

    public Move(Figure figure, AbstractField destination, MoveCard parentCard) {
        this.figure = figure;
        this.destination = destination;
        this.parentCard = parentCard;
        this.player = figure.getPlayer();
    }


    public Figure getFigure() {
        return figure;
    }

    public AbstractField getDestination() {
        return destination;
    }

    public MoveCard getParentCard() {
        return parentCard;
    }

    public Player getPlayer() {
        return player;
    }
}
