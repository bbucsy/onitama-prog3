package onitama.model.board;

import onitama.model.figures.Apprentice;
import onitama.model.figures.Figure;
import onitama.model.figures.Master;

import java.awt.*;

public class SimpleField extends AbstractField {

    private final Board board;
    private final Point pos;
    private Figure currentFigure;

    public SimpleField(Board b, Point p) {
        pos = p;
        board = b;
    }


    @Override
    public void accept(Apprentice a) {
        if (currentFigure != null)
            currentFigure.hitByFigure(a);
        setFigure(a);
    }

    @Override
    public void accept(Master m) {
        if (currentFigure != null)
            currentFigure.hitByFigure(m);
        setFigure(m);
    }

    @Override
    public Figure getFigure() {
        return currentFigure;
    }

    @Override
    public void setFigure(Figure f) {
        currentFigure = f;
        this.fireUpdated();
    }

    @Override
    public Point getPosition() {
        return pos;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    protected Figure getMessage() {
        return currentFigure;
    }
}
