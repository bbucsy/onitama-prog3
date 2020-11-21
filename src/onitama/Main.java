package onitama;

import onitama.model.*;
import onitama.model.board.Board;
import onitama.model.moves.Move;
import onitama.model.moves.MoveCardStorage;

import java.util.List;
import java.util.Random;

public class Main {

    private static void Display(Game g){
        for (int i = 0; i < 0; i++) {
            System.out.println("\n");
        }
        System.out.println(g);
    }

    public static void main(String[] args) {
	// write your code here
        try {
            Game g = new Game();
            Player p1 = g.getPlayer(0);
            Player p2 = g.getPlayer(1);
            Board b = g.getBoard();
            Display(g);
            p1.setSelectedCard(p1.getCards().get(0));
            p1.setSelectedFigure(p1.getFigures().get(2));
            p1.makeMove(0);
            Display(g);


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
