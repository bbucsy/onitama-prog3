package onitama;

import onitama.controller.GameController;
import onitama.controller.ai.AbstractAi;
import onitama.controller.ai.RandomAi;
import onitama.model.*;
import onitama.view.GameFrame;

public class Main {

    public static void main(String[] args) {
        try{
            Game game = new Game();
            GameFrame frame = new GameFrame(game);
            //GameController controller = new GameController(game,frame,null,new RandomAi(game,game.getPlayer(1)));
            GameController controller = new GameController(game,frame,null,null);
            controller.StartGame();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
