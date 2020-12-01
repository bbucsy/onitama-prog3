package onitama.model.moves;

import onitama.model.Game;
import onitama.model.board.AbstractField;
import onitama.model.board.Board;
import onitama.model.figures.Figure;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This stores the possible moves a figure can make from a given position.
 * The objects from this class aren't created via a constructor, but stored
 * in a JSON file and loaded by the <code>MoveCardStorage</code> singleton
 * using gson library.
 */
public class MoveCard implements Serializable {

    /**
     * A list of relative positions that a figure can move, facing in the way of the first player.
     */
    private final ArrayList<Point> relativeMoves;
    /**
     * The name of the card.
     */
    private final String name;
    /**
     * A multiplier, that is applied to the relative moves.
     * if its -1 it means that tha card is rotated in 180 degrees.
     */
    private int orientation = 1;

    /**
     * Private constructor, only here so that the constant properties can be final.
     * The constructor actually isn't called, tha cards are loaded using gson library.
     */
    private MoveCard() {
        relativeMoves = new ArrayList<>();
        name = "MoveCard";
    }

    /**
     * Calculates all the moves a given figure can make with the card, in that state of the game.
     * @param f The figure to be moved
     * @return  The available legal moves the figure can make
     */
    public List<Move> getAvailableMoves(Figure f) {
        ArrayList<Move> moves = new ArrayList<>();
        Point base = f.getCurrentField().getPosition();
        Board board = f.getCurrentField().getBoard();
        for (Point p : getRelativeMoves()) {
            Point newPos = new Point(base.x + p.x, base.y + p.y);
            if (positionInBound(newPos)) {
                AbstractField targetField = board.getField(newPos);
                if (!hitOwnFigure(f, targetField.getFigure()))
                    moves.add(new Move(f, targetField, this));
            }
        }
        return moves;
    }

    /**
     * Checks if a position is in bounds of the board.
     * @param pos the checked position
     * @return  true if its a legal position
     */
    private boolean positionInBound(Point pos) {
        return (pos.x >= 0 && pos.x <= 4 && pos.y >= 0 && pos.y <= 4);
    }

    /**
     * Checks if destination has another figure of the same owner
     * @param movingFigure the figure to be moved
     * @param target the destination field
     * @return true if move is illegal
     */
    private boolean hitOwnFigure(Figure movingFigure, Figure target) {
        if (target == null) return false;
        return (movingFigure.getPlayer() == target.getPlayer());
    }

    /**
     * Uses in memory writing and the serializable implementation of the class
     * to make a deep copy of it
     * @return  A deep copy of the card
     * @throws IOException If the in memory writing failed
     * @throws ClassNotFoundException If the deserialization failed
     */
    public MoveCard deepClone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(temp);
        out.writeObject(this);

        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(temp.toByteArray()));
        return (MoveCard) in.readObject();
    }


    /**
     * Changes the orientation of the card.
     * @return Itself. So that it can be chained.
     */
    public MoveCard changeOrientation() {
        orientation *= -1;
        return this;
    }


    /**
     * Return the relative moves in the right orientation
     * @return  The possible moves relative to the position of a figure
     */
    public ArrayList<Point> getRelativeMoves() {
        ArrayList<Point> result = new ArrayList<>();
        for (Point p : relativeMoves) {
            result.add(new Point(p.x * orientation, p.y * orientation));
        }
        return result;
    }

    /**
     *
     * @return The name of the card
     */
    public String getName() {
        return name;
    }

}
