package onitama.model.figures;

import onitama.model.Player;
import onitama.model.board.AbstractField;

import java.io.Serializable;

public interface Figure extends Serializable {
    void moveTo(AbstractField f);

    void hitByFigure(Figure f);

    Player getPlayer();

    AbstractField getCurrentField();

    void setCurrentField(AbstractField currentField);

    FigureType getFigureType();
}
