package onitama.model.figures;

import onitama.model.board.Field;
import onitama.model.Player;
import onitama.model.board.FigureType;

public class Master extends Apprentice {


    public Master(Player p) {
        super(p);
    }

    @Override
    public void move(Field f) {
        f.accept(this);
        currentField.setFigure(null);
        currentField = f;
    }

    @Override
    public void die() {
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
