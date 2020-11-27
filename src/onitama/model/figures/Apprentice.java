package onitama.model.figures;

import onitama.model.board.AbstractField;
import onitama.model.Player;

import java.security.InvalidParameterException;

public class Apprentice implements Figure{

    protected final Player player;
    protected AbstractField currentField;

    public Apprentice(Player p){
        player = p;
    }


    @Override
    public void moveTo(AbstractField f) {
        f.accept(this);
        currentField.setFigure(null);
        currentField = f;
    }

    @Override
    public void die() {
        currentField.setFigure(null);
        currentField = null;
        player.removeFigure(this);
    }

    @Override
    public void hitByFigure(Figure f) {
        if(f.getPlayer() == player) throw new InvalidParameterException("Can't hit own figure");
        die();
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    public AbstractField getCurrentField() {
        return currentField;
    }

    public void setCurrentField(AbstractField currentField) {
        this.currentField = currentField;
    }

    @Override
    public FigureType getFigureType() {
        return FigureType.Apprentice;
    }

    @Override
    public String toString() {
        return "A";
    }
}
