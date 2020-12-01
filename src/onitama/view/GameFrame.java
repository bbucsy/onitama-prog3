package onitama.view;

import onitama.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * This is the main window of a Game
 * This holds the board, the cardholders and displays of each player.
 */
public class GameFrame extends JFrame {

    /**
     * The model of the game
     */
    private final Game model;
    /**
     * The board view
     */
    private final BoardPanel boardPanel;
    /**
     * The views of each players hand, and the exchange card
     */
    private final CardHolderPanel[] cardHolders;
    /**
     * The main panel containing the board and the
     */
    private final JPanel gamePanel;
    /**
     * The panel containing the player stat views, and the exchange card's holder
     */
    private final JPanel sidePanel;

    /**
     * Menu item for saving the game
     */
    private JMenuItem saveMenuItem;

    /**
     * Menu item for going back to MainMenu
     */
    private JMenuItem backToMainMenuItem;

    /**
     * Menu item to exit the program
     */
    private JMenuItem exitMenuItem;

    /**
     * Constructor of the Frame.
     * This registers the observer components to the observables.
     * To one model there can be more than one view, but only one controller should own a model
     * @param model The model of the game
     */
    public GameFrame(Game model) {
        super("Onitama");
        this.model = model;
        boardPanel = new BoardPanel();
        gamePanel = new JPanel(new BorderLayout());
        sidePanel = new JPanel(new GridLayout(5, 1, 20, 0));
        cardHolders = new CardHolderPanel[3];
        initialize();
    }

    /**
     * Calls all the separate initializers, and then displays the frame
     */
    private void initialize() {
        initializeLayout();
        initializeObservers();
        initializeJMenu();

        this.setVisible(true);
    }

    /**
     * Sets the dimensions and layout of the frame
     */
    private void initializeLayout() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 1000);
        this.setResizable(false);

        for (int i = 0; i < 3; i++)
            cardHolders[i] = new CardHolderPanel((i == 2) ? 1 : 2);

        gamePanel.add(cardHolders[0], BorderLayout.NORTH);
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(cardHolders[1], BorderLayout.SOUTH);

        sidePanel.add(new PlayerInfoPanel(model.getPlayer(0)));
        sidePanel.add(new JLabel());
        sidePanel.add(cardHolders[2]);
        sidePanel.add(new JLabel());
        sidePanel.add(new PlayerInfoPanel(model.getPlayer(1)));

        this.add(gamePanel, BorderLayout.CENTER);
        this.add(sidePanel, BorderLayout.EAST);
    }


    /**
     * Registers the observer components to the observable models
     */
    private void initializeObservers() {
        for (int i = 0; i < 2; i++)
            model.getPlayer(i).getHand().attachObserver(cardHolders[i]);
        model.getExchangeCard().attachObserver(cardHolders[2]);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                model.getBoard().getField(i, j).attachObserver(boardPanel.getSquare(i, j));
            }
        }

    }

    /**
     * Creates a JMenu and control buttons, but the Action listeners should be
     * added by a controller
     */
    private void initializeJMenu() {

        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_ESCAPE);
        file.getAccessibleContext().setAccessibleDescription("File menu");
        bar.add(file);


        saveMenuItem = new JMenuItem("Save Game");
        file.add(saveMenuItem);

        backToMainMenuItem = new JMenuItem("Exit to MainMenu");
        file.add(backToMainMenuItem);

        exitMenuItem = new JMenuItem("Exit Program");
        file.add(exitMenuItem);

        this.setJMenuBar(bar);
    }


    /**
     *
     * @return The menu item for the button to save the actual game
     */
    public JMenuItem getSaveMenuItem() {
        return saveMenuItem;
    }

    /**
     *
     * @return The menu item for the button to go back to main menu
     */
    public JMenuItem getBackToMainMenuItem() {
        return backToMainMenuItem;
    }

    /**
     *
     * @return The menu item for the button to exit the program
     */
    public JMenuItem getExitMenuItem() {
        return exitMenuItem;
    }

    /**
     *
     * @return The panel containing the squares
     */
    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     *
     * @return The Card holdars of the players and the exchange card
     */
    public CardHolderPanel[] getCardHolders() {
        return cardHolders;
    }
}
