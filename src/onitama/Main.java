package onitama;

import onitama.controller.GameController;
import onitama.controller.ai.AbstractAi;
import onitama.controller.ai.RandomAi;
import onitama.model.*;
import onitama.view.GameFrame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Main {

    public static void main(String[] args) {
        /*try{
            Game game = new Game();
            GameFrame frame = new GameFrame(game);
            //GameController controller = new GameController(game,frame,null,new RandomAi(game,game.getPlayer(1)));
            GameController controller = new GameController(game,frame,null,null);
            controller.StartGame();
        }catch (Exception e){
            e.printStackTrace();
        }*/

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("test.savegame"));
            Game game = (Game)in.readObject();
            in.close();

            GameFrame frame = new GameFrame(game);
            GameController controller = new GameController(game,frame,null,null);
            controller.StartGame();


        }catch (Exception e){
            e.printStackTrace();
        }





    }

}
