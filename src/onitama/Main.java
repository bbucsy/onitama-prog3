package onitama;

import onitama.controller.GameController;
import onitama.model.*;
import onitama.utils.ResourceManager;
import onitama.view.GameFrame;

import java.io.*;

public class Main {

    private static final String[] resources = new String[] {"board.png","card.png"};

    public static void main(String[] args) {

        try{
            //eager load resources
            ResourceManager.getInstance().loadResources(resources);
            Game game = new Game();
            GameFrame frame = new GameFrame(game);
            GameController controller = new GameController(game,frame,null,null);
            controller.startGame();

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
