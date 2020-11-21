package onitama.model.board;

import onitama.model.figures.Apprentice;
import onitama.model.figures.Figure;
import onitama.model.figures.Master;

import java.awt.*;

public interface Field {
    void accept(Apprentice a);
    void accept(Master m);
    void setFigure(Figure f);
    Board getBoard();
    Figure getFigure();
    Point getPosition();

}
