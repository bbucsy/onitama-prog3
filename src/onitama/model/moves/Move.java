package onitama.model.moves;

import onitama.model.board.Field;
import onitama.model.figures.Figure;

public class Move {

    private Figure figure;
    private Field destination;

    public Move(Figure figure, Field destination) {
        this.figure = figure;
        this.destination = destination;
    }

    public Figure getFigure() {
        return figure;
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public Field getDestination() {
        return destination;
    }

    public void setDestination(Field destination) {
        this.destination = destination;
    }

    public void makeMove(){
        figure.move(destination);
    }

}
