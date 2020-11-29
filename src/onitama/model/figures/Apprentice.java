package onitama.model.figures;

import onitama.model.Player;
import onitama.model.board.AbstractField;


public class Apprentice implements Figure {

    protected final Player player;
    protected AbstractField currentField;

    public Apprentice(Player p) {
        player = p;
    }

    protected void die() {
        currentField.setFigure(null);
        currentField = null;
        player.removeFigure(this);
    }

    @Override
    public void moveTo(AbstractField f) {
        f.accept(this);
        currentField.setFigure(null);
        currentField = f;
    }


    @Override
    public void hitByFigure(Figure f) {
        if (f.getPlayer() == player) throw new IllegalArgumentException("Can't hit own figure");
        die();
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }


    @Override
    public FigureType getFigureType() {
        return FigureType.APPRENTICE;
    }

    public AbstractField getCurrentField() {
        return currentField;
    }

    public void setCurrentField(AbstractField currentField) {
        this.currentField = currentField;
    }
}
