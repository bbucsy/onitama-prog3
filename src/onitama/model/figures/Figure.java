package onitama.model.figures;

import onitama.model.board.Field;
import onitama.model.Player;

public interface Figure {
    void move(Field f);
    void die();
    void hitByFigure(Figure f);
    Field getCurrentField();
    void setCurrentField(Field currentField);
    Player getPlayer();
}
