package onitama.model.figures;

import onitama.model.Player;
import onitama.model.board.AbstractField;

/**
 * This is the model representing an Master type figure in the game.
 */
public class Master extends Apprentice {


    /**
     * Constructor of a master
     *
     * @param p The owner of the figure
     */
    public Master(Player p) {
        super(p);
    }

    /**
     * @param f The destination of the movement
     */
    @Override
    public void moveTo(AbstractField f) {
        f.accept(this);
        currentField.setFigure(null);
        currentField = f;
    }

    /**
     * On top of removing itself from the model,
     * this function has the responsibility to signal the owner of the figure
     * that it lost, because the master has died.
     */
    @Override
    protected void die() {
        player.loose();
        super.die();
    }

    /**
     * @return The type of the figure
     */
    @Override
    public FigureType getFigureType() {
        return FigureType.MASTER;
    }

}
