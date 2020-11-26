package onitama.model.figures;

import onitama.model.board.Field;
import onitama.model.Player;
import onitama.model.board.FigureType;

public interface Figure {
    void move(Field f);
    void die();
    void hitByFigure(Figure f);
    void setCurrentField(Field currentField);
    Player getPlayer();
    Field getCurrentField();
    FigureType getFigureType();
}
