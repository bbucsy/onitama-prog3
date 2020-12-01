package onitama.controller;

import onitama.controller.ai.PlayerController;
import onitama.model.Game;
import onitama.utils.MoveCardStorage;
import onitama.view.GameFrame;
import onitama.view.MainMenuFrame;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * This is the main controller of the application.
 * <p>
 * This is a singleton class, and contains the single MainMenu frame that is created during
 * the lifecycle of the application. It has public functions to create new Game instances and the required Views and
 * Controllers to run the actual game. The game-instance's main loop is started by this controller on a background thread.
 * This class is also responsible for Loading and Saving a Game model.
 */
public class MainController {

    /**
     * The instance of the MainController
     */
    private static MainController instance;

    /**
     * The Frame of the main menu
     */
    private final MainMenuFrame mainFrame;

    /**
     * Private constructor, so that it can't be instantiated by other classes.
     * It creates the MainMenu frame
     */
    private MainController() {
        mainFrame = new MainMenuFrame();
        initialize();
    }

    /**
     * Returns the instance of this singleton class, if it's the first call, it creates the instance.
     *
     * @return The instance of the class
     */
    public static MainController getInstance() {
        if (instance == null)
            instance = new MainController();
        return instance;
    }

    /**
     * This function initializes the instance object on creation.
     * It ads the required ActionListeners to the MainMenu frames buttons
     */
    private void initialize() {

        // Creates a new fresh game
        mainFrame.getNewGame().addActionListener(event -> {
            //Get a new SwingWorker to start the game.
            GameLauncher gl;
            try {
                gl = new GameLauncher(mainFrame.getPlayerControllers(), mainFrame.getPlayerNames());
            } catch (Exception e){
                e.printStackTrace();
                //Show error message if serialization can't be done.
                JOptionPane.showMessageDialog(mainFrame, "Couldn't create game, because cards couldn't be cloned", "Load failed", JOptionPane.WARNING_MESSAGE);
                return;
            }
            gl.execute();
        });

        // Load a Game file
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
                //Show error message if serialization can't be done.
                JOptionPane.showMessageDialog(mainFrame, "Couldn't load game, please try another file.", "Load failed", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //Get a new SwingWorker to start the loaded game.
            GameLauncher gl = new GameLauncher(loadedModel, mainFrame.getPlayerControllers(), mainFrame.getPlayerNames());
            gl.execute();
        });
    }

    /**
     * This Function is responsible to save a game.
     * Shows an error message if the game is already finished, and there is no point in saving it.
     *
     * @param model The Game model to be saved.
     */
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
            // Show error message;
            JOptionPane.showMessageDialog(mainFrame, "Couldn't save game", "Save failed", JOptionPane.WARNING_MESSAGE);
        }


    }

    /**
     * @return The Frame of the Main Menu
     */
    public MainMenuFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * This is responsible for instantiating a Game object with the necessary Controllers
     * and Views. Because the <code>GameController</code> needs a different thread for the game-loop to
     * function correctly, it is started as a background process int the SwingWorker.
     * This class is a private subclass of the MainController class.
     */
    private class GameLauncher extends SwingWorker<Object, Object> {

        /**
         * The model of the game to be launched
         */
        private final Game model;
        /**
         * The PlayerController objects for each player. This array
         * must have at least 2 elements, but there can be nulls.
         */
        private final PlayerController[] players;
        /**
         * The names of the players.
         */
        private final String[] playerNames;

        /**
         * Constructor of the GameLauncher class, used when the game model has been created somewhere else.
         *
         * @param model       The game model object.
         * @param players     The PlayerController objects for each player.
         * @param playerNames The names of the players.
         */
        private GameLauncher(Game model, PlayerController[] players, String[] playerNames) {
            super();
            this.model = model;
            this.players = players;
            this.playerNames = playerNames;
        }

        /**
         * Constructor of the GameLauncher class, used when the a new Game model needs to be created.
         *
         * @param players     The PlayerController objects for each player.
         * @param playerNames The names of the players.
         */
        private GameLauncher(PlayerController[] players, String[] playerNames) throws IOException, ClassNotFoundException {
            super();
            this.model = new Game(MoveCardStorage.getInstance().getRandomCards(5));
            this.players = players;
            this.playerNames = playerNames;
        }

        /**
         * The main process of the object.
         * It sets the necessary references inside the controllers and the model,
         * then creates the View and Controller for the model.
         * It starts the main loop, and after it returns (the game is finished) it disposes the View.
         *
         * @return SwingWorker requires the Background process to have a return value, in this case always returns null.
         */
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

        /**
         * Called when the background process is returned.
         * It makes the MainMenu visible.
         */
        @Override
        protected void done() {
            mainFrame.setVisible(true);
        }

    }
}
