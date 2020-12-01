package onitama.model.board;

import onitama.model.Player;
import onitama.model.figures.Master;

import java.awt.*;

/**
 * A field representing the starting point of the Master
 */
public class ShrineField extends SimpleField {

    /**
     * The 'owner' of the shrine
     */
    private final Player player;

    /**
     * Constructor of a Shrine field. Creates the master on it.
     *
     * @param b   The containing board.
     * @param pos The position of the field.
     * @param p   The Player model that owns the shrine.
     */
    public ShrineField(Board b, Point pos, Player p) {
        super(b, pos);
        player = p;

        //create a starting figure
        Master starter = new Master(p);
        starter.setCurrentField(this);
        p.addFigure(starter);
        this.setFigure(starter);
    }

    /**
     * Accepts a master figure on itself. If its the enemy master, than the owner of the shrine lost the match.
     *
     * @param m The master that wants to move to the field
     */
    @Override
    public void accept(Master m) {
        if (player != m.getPlayer())
            player.loose();
        super.accept(m);
    }

}
