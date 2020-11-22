package onitama.model.moves;

import onitama.model.board.Board;
import onitama.model.figures.Figure;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveCard {

    private ArrayList<Point> relativeMoves;
    private String name;

    public MoveCard() {
        relativeMoves = new ArrayList<Point>();
    }

    public List<Move> getAvaibleMoves(Figure f) {
        ArrayList<Move> moves = new ArrayList<>();
        Point base = f.getCurrentField().getPosition();
        Board board = f.getCurrentField().getBoard();
        for (Point p : relativeMoves) {
            Point newPos = new Point(base.x + p.x, base.y + p.y);
            if(newPos.x >=0 && newPos.x <= 4 && newPos.y >=0 && newPos.y <= 4)
                moves.add(new Move(f,board.getField(newPos)));
        }

        return moves;
    }

    public ArrayList<Point> getRelativeMoves() {
        return relativeMoves;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
