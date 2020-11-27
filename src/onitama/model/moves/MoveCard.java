package onitama.model.moves;

import onitama.model.board.AbstractField;
import onitama.model.board.Board;
import onitama.model.figures.Figure;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveCard {

    private final ArrayList<Point> relativeMoves;
    private String name;
    private int orientation = 1;

    private MoveCard() {
        relativeMoves = new ArrayList<>();
        name = "MoveCard";
    }

    public List<Move> getAvailableMoves(Figure f) {
        ArrayList<Move> moves = new ArrayList<>();
        Point base = f.getCurrentField().getPosition();
        Board board = f.getCurrentField().getBoard();
        for (Point p : getRelativeMoves()) {
            Point newPos = new Point(base.x + p.x, base.y + p.y);
            if(positionInBound(newPos)){
                AbstractField targetField = board.getField(newPos);
                if(!hitOwnFigure(f,targetField.getFigure()))
                    moves.add(new Move(f,targetField,this));
            }
        }

        return moves;
    }

    private boolean positionInBound(Point pos){
        return (pos.x >=0 && pos.x <= 4 && pos.y >=0 && pos.y <= 4);
    }

    private boolean hitOwnFigure(Figure movingFigure, Figure target){
        if(target == null) return false;
        return (movingFigure.getPlayer() == target.getPlayer());
    }

    public MoveCard changeOrientation(){
        orientation*=-1;
        return this;
    }

    public ArrayList<Point> getRelativeMoves() {
        ArrayList<Point> result = new ArrayList<>();
        for(Point p: relativeMoves){
            result.add(new Point(p.x*orientation,p.y*orientation));
        }
        return result;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
