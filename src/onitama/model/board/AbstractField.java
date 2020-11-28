package onitama.model.board;

import onitama.model.figures.Apprentice;
import onitama.model.figures.Figure;
import onitama.model.figures.Master;
import onitama.utils.ObservedSubject;

import java.awt.*;
import java.io.Serializable;

public abstract class AbstractField extends ObservedSubject<Figure> implements Serializable {
    public abstract void accept(Apprentice a);
    public abstract void accept(Master m);
    public abstract void setFigure(Figure f);
    public abstract Board getBoard();
    public abstract Figure getFigure();
    public abstract Point getPosition();

}
