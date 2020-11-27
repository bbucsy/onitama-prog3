package onitama.model.figures;

import onitama.model.board.AbstractField;
import onitama.model.Player;

public interface Figure {
    void moveTo(AbstractField f);
    void die();
    void hitByFigure(Figure f);
    void setCurrentField(AbstractField currentField);
    Player getPlayer();
    AbstractField getCurrentField();
    FigureType getFigureType();
}
