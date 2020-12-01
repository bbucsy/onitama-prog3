package onitama.view;

import onitama.controller.ai.PlayerController;

import javax.swing.*;
import java.awt.*;

/**
 * The window of the MainMenu
 */
public class MainMenuFrame extends JFrame {


    /**
     * Option panel for the first player
     */
    private final PlayerMenuPanel playerPanel1;
    /**
     * Option panel for the second panel
     */
    private final PlayerMenuPanel playerPanel2;
    /**
     * Button to create a new game
     */
    private final JButton newGame;
    /**
     * Button to load a saved game
     */
    private final JButton loadGame;

    /**
     * Constructor of the frame
     */
    public MainMenuFrame() {
        super("Onitama");
        playerPanel1 = new PlayerMenuPanel("Player 1");
        playerPanel2 = new PlayerMenuPanel("Player 2");
        newGame = new JButton("New game");
        loadGame = new JButton("Load game");
        initializeLayout();
    }

    /**
     * Sets the layout of the frame and sets up the contained components
     */
    private void initializeLayout() {
        this.setLayout(new BorderLayout());

        JPanel pPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        pPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        pPanel.add(playerPanel1);
        pPanel.add(playerPanel2);

        JPanel controlPanel = new JPanel();
        controlPanel.add(newGame);
        controlPanel.add(loadGame);

        this.add(pPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.PAGE_END);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    /**
     *
     * @return An array containing the PlayerControllers set by the player panels
     */
    public PlayerController[] getPlayerControllers() {
        return new PlayerController[]{playerPanel1.getPlayerType(), playerPanel2.getPlayerType()};
    }

    /**
     *
     * @return Array of strings containing the names of the players set by the player panels
     */
    public String[] getPlayerNames() {
        return new String[]{playerPanel1.getName(), playerPanel2.getName()};
    }

    /**
     *
     * @return The button component for creating a new game
     */
    public JButton getNewGame() {
        return newGame;
    }

    /**
     *
     * @return The button component for loading a new game.
     */
    public JButton getLoadGame() {
        return loadGame;
    }
}
