package onitama.model.figures;

import onitama.model.Player;
import onitama.model.board.AbstractField;

/**
 * This is the model representing an Apprentice type figure in the game.
 */
public class Apprentice implements Figure {

    /**
     * The owner of the figure
     */
    protected final Player player;

    /**
     * The field that the figure is currently on.
     * If there is no field set, that means that the figure is out of game(died)
     */
    protected AbstractField currentField;

    /**
     * The constructor of the class
     *
     * @param p The owner of the figure
     */
    public Apprentice(Player p) {
        player = p;
    }

    /**
     * When called, removes itself from the model.
     *
     * @throws IllegalStateException If the figure is already out of the game.
     */
    protected void die() {
        if (currentField == null)
            throw new IllegalStateException("Figure doesn't stand on field. Probably already dead.");
        currentField.setFigure(null);
        currentField = null;
        player.removeFigure(this);
    }

    /**
     * Moves to the given field
     *
     * @param f The destination of the movement
     */
    @Override
    public void moveTo(AbstractField f) {
        f.accept(this);
        currentField.setFigure(null);
        currentField = f;
    }

    /**
     * If another figure is trying to move to the field of this figure, they collide.
     * As of now no mather what the other figure is, the one being hit dies if its a legal move
     *
     * @param f The figure trying to kill this figure.
     * @throws IllegalArgumentException If the two figures has the same owner, that move is considered illegal
     */
    @Override
    public void hitByFigure(Figure f) {
        if (f.getPlayer() == player) throw new IllegalArgumentException("Can't hit own figure");
        die();
    }

    /**
     * @return The owner of the figure
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }


    /**
     * @return The type of the figure
     */
    @Override
    public FigureType getFigureType() {
        return FigureType.APPRENTICE;
    }

    /**
     * @return The field that this figure is on.
     */
    public AbstractField getCurrentField() {
        return currentField;
    }

    /**
     * Sets the currentField of this figure. Doesn't goeas through the procedure of moving a piece.
     * Used only in initializations.
     *
     * @param currentField The field that this figure is set to be on.
     */
    public void setCurrentField(AbstractField currentField) {
        this.currentField = currentField;
    }
}
