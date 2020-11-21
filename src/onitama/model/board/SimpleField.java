package onitama.model.board;

import onitama.model.figures.Apprentice;
import onitama.model.figures.Figure;
import onitama.model.figures.Master;

import java.awt.*;

public class SimpleField implements Field{

    private Board board;
    private Figure currentFigure;
    private Point pos;

    public SimpleField(Board b,Point p){
        pos = p;
        board = b;
    }


    @Override
    public void accept(Apprentice a) {
        if(currentFigure != null)
            currentFigure.hitByFigure(a);
        currentFigure = a;
    }

    @Override
    public void accept(Master m) {
        if(currentFigure != null)
            currentFigure.hitByFigure(m);
        currentFigure = m;
    }

    @Override
    public void setFigure(Figure f) {
        currentFigure = f;
    }

    @Override
    public Figure getFigure() {
        return currentFigure;
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
    public String toString() {
        if (currentFigure == null) return " # ";
        else return " "+currentFigure.toString()+" ";
    }
}
