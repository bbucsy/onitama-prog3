package onitama;

import onitama.controller.GameController;
import onitama.model.*;
import onitama.model.board.Board;
import onitama.model.moves.Move;
import onitama.model.moves.MoveCardStorage;
import onitama.view.GameFrame;

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
        try{
            GameFrame gf = new GameFrame();
            Game game = new Game();
            GameController controller = new GameController(game,gf);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
