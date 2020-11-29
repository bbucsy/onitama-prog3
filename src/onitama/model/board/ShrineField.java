package onitama.model.board;

import onitama.model.Player;
import onitama.model.figures.Master;

import java.awt.*;

public class ShrineField extends SimpleField {

    private final Player player;

    public ShrineField(Board b, Point pos, Player p) {
        super(b, pos);
        player = p;
    }

    @Override
    public void accept(Master m) {
        if (player != m.getPlayer())
            player.loose();
        super.accept(m);
    }

}
