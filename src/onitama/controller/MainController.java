package onitama.controller;

import onitama.controller.ai.AbstractAi;
import onitama.model.Game;
import onitama.view.GameFrame;
import onitama.view.MainMenuFrame;

import javax.swing.*;

public class MainController {

    private static MainController instance;
    private final MainMenuFrame mainFrame;

    private MainController() {
        mainFrame = new MainMenuFrame();

    }

    public static MainController getInstance() {
        if (instance == null)
            instance = new MainController();
        return instance;
    }


    public void launchGame( AbstractAi[] players){
        SwingWorker gameLauncher = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                mainFrame.setVisible(false);
                Game model = new Game();

                for (int i = 0; i < players.length; i++) {
                    if(players[i] != null){
                        players[i].setModel(model);
                        players[i].setPlayer(model.getPlayer(i));
                    }
                }
                    GameFrame frame = new GameFrame(model);
                    GameController gc = new GameController(model,frame,players);
                    gc.startGame();
                    frame.dispose();

                    return null;
            }

            @Override
            protected void done() {
                mainFrame.setVisible(true);
            }

        };
        gameLauncher.execute();
    }

    public MainMenuFrame getMainFrame() {
        return mainFrame;
    }
}
