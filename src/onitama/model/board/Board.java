package onitama.model.board;

import onitama.model.Game;
import onitama.model.Player;
import onitama.model.figures.Apprentice;
import onitama.model.figures.Figure;
import onitama.model.figures.Master;

import java.awt.*;

public class Board {

    private Field[][] fields;
    private Game game;

    private void createBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 2 && (i == 0 || i == 4))
                    fields[i][j] = new ShrineField(this, new Point(i,j) ,game.getPlayer(i / 4));
                else
                    fields[i][j] = new SimpleField(this,new Point(i,j));
            }
        }

    }

    private void FillBoard(){

        Player p1 = game.getPlayer(0);
        Player p2 = game.getPlayer(1);

        // fill player 1 Figures
        for (int i = 0; i < 5; i++){
            Field f = fields[0][i];
            Figure newFigure = (i == 2)? new Master(p1) : new Apprentice(p1);
            newFigure.setCurrentField(f);
            f.setFigure(newFigure);
            p1.addFigure(newFigure);
        }
        // fill player 2 Figures
        for (int i = 0; i < 5; i++){
            Field f = fields[4][i];
            Figure newFigure = (i == 2)? new Master(p2) : new Apprentice(p2);
            newFigure.setCurrentField(f);
            f.setFigure(newFigure);
            p2.addFigure(newFigure);
        }

    }


    public Board(Game g) {
        game = g;
        fields = new Field[5][5];
        createBoard();
        FillBoard();
    }

    public Field getField(int i, int j) {
        if (i < 0 || i > 4 || j < 0 || j > 4)
            return null;
        else
            return fields[i][j];
    }

    public Field getField(Point p) {
       return getField(p.x,p.y);
    }


    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                ret.append(fields[i][j].toString());
            }
            ret.append("\n");
        }
        return ret.toString();
    }
}
