package onitama;

import onitama.controller.GameController;
import onitama.controller.MainController;
import onitama.controller.ai.AbstractAi;
import onitama.model.*;
import onitama.utils.MoveCardStorage;
import onitama.utils.ResourceManager;
import onitama.view.ErrorReport;
import onitama.view.GameFrame;
import onitama.view.MainMenuFrame;

import java.io.*;

public class Main {

    private static final String[] resources = new String[] {"board.png","card.png"};

    public static void main(String[] args) {

        try{
            //eager load resources
            ResourceManager.getInstance().loadResources(resources);
            // load card pack;
            MoveCardStorage.getInstance().loadCards("cards.json");

            MainController mc = MainController.getInstance();

            mc.getMainFrame().setVisible(true);

           /* Game g = new Game();
            GameFrame f = new GameFrame(g);
            GameController gc = new GameController(g,f,new AbstractAi[]{null,null});
            gc.startGame();*/

        }catch (Exception e){
            ErrorReport er = new ErrorReport(e);
        }

    }



}
