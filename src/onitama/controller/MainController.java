package onitama.controller;

import onitama.controller.ai.PlayerController;
import onitama.model.Game;
import onitama.utils.MoveCardStorage;
import onitama.view.GameFrame;
import onitama.view.MainMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class MainController {

    private static MainController instance;
    private final MainMenuFrame mainFrame;

    private MainController() {
        mainFrame = new MainMenuFrame();

        initialize();
    }

    public static MainController getInstance() {
        if (instance == null)
            instance = new MainController();
        return instance;
    }

    private void initialize() {

        mainFrame.getNewGame().addActionListener(event -> {
            GameLauncher gl = new GameLauncher(mainFrame.getPlayerControllers(), mainFrame.getPlayerNames());
            gl.execute();
        });

        mainFrame.getLoadGame().addActionListener(event -> {
            FileDialog fd = new FileDialog(mainFrame, "Choose a saved game", FileDialog.LOAD);
            fd.setFile("*.savegame");
            fd.setVisible(true);
            String fileName = fd.getDirectory() + fd.getFile();

            Game loadedModel;
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
                loadedModel = (Game) in.readObject();
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame, "Couldn't load game, please try another file.", "Load failed", JOptionPane.WARNING_MESSAGE);
                return;
            }

            GameLauncher gl = new GameLauncher(loadedModel, mainFrame.getPlayerControllers(), mainFrame.getPlayerNames());
            gl.execute();
        });
    }

    public void saveGame(Game model) {
        if (model.getState() != Game.GameState.RUNNING) {
            JOptionPane.showMessageDialog(mainFrame, "The game is already finished, there is no point in saving it", "Save failed", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        FileDialog fileDialog = new FileDialog(mainFrame, "Save game", FileDialog.SAVE);
        fileDialog.setFile("*.savegame");
        fileDialog.setVisible(true);

        String path = fileDialog.getDirectory() + fileDialog.getFile();
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(model);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, "Couldn't save game", "Save failed", JOptionPane.WARNING_MESSAGE);
        }


    }

    public MainMenuFrame getMainFrame() {
        return mainFrame;
    }

    private class GameLauncher extends SwingWorker<Object, Object> {

        private final Game model;
        private final PlayerController[] players;
        private final String[] playerNames;

        private GameLauncher(Game model, PlayerController[] players, String[] playerNames) {
            super();
            this.model = model;
            this.players = players;
            this.playerNames = playerNames;
        }

        private GameLauncher(PlayerController[] players, String[] playerNames) {
            super();
            this.model = new Game(MoveCardStorage.getInstance().getRandomCards(5));
            this.players = players;
            this.playerNames = playerNames;
        }


        @Override
        protected Object doInBackground() {
            mainFrame.setVisible(false);

            for (int i = 0; i < playerNames.length; i++) {
                model.getPlayer(i).setName(playerNames[i]);
            }

            for (int i = 0; i < players.length; i++) {
                if (players[i] != null) {
                    players[i].setModel(model);
                    players[i].setPlayer(model.getPlayer(i));
                }
            }
            GameFrame frame = new GameFrame(model);
            GameController gc = new GameController(model, frame, players);
            gc.startGame();
            frame.dispose();

            return null;
        }

        @Override
        protected void done() {
            mainFrame.setVisible(true);
        }

    }
}
