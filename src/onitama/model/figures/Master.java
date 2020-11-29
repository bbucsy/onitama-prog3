package onitama.model.figures;

import onitama.model.board.AbstractField;
import onitama.model.Player;

public class Master extends Apprentice {


    public Master(Player p) {
        super(p);
    }

    @Override
    public void moveTo(AbstractField f) {
        f.accept(this);
        currentField.setFigure(null);
        currentField = f;
    }

    @Override
    protected void die() {
        player.loose();
        super.die();
    }

    @Override
    public FigureType getFigureType() {
        return FigureType.Master;
    }

    @Override
    public String toString() {
        return "M";
    }
}
