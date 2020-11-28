package onitama.view;

import onitama.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static java.lang.System.exit;

public class GameFrame extends JFrame {

    private final Game model;
    private final BoardPanel boardPanel;
    private final CardHolderPanel[] cardHolders;
    private final JPanel gamePanel;
    private final JPanel sidePanel;

    private JMenuItem saveMenuItem;
    private JMenuItem exitToMainMenuItem;

    public GameFrame(Game model) {
        super("Onitama");
        this.model = model;
        boardPanel = new BoardPanel();
        gamePanel = new JPanel(new BorderLayout());
        sidePanel = new JPanel(new GridLayout(5, 1, 20, 0));
        cardHolders = new CardHolderPanel[3];
        initialize();
    }

    private void initialize() {
        initializeLayout();
        initializeObservers();
        initializeJMenu();

        this.setVisible(true);
    }

    private void initializeLayout() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900, 1000);
        this.setResizable(false);

        for (int i = 0; i < 3; i++)
            cardHolders[i] = new CardHolderPanel((i == 2) ? 1 : 2);

        gamePanel.add(cardHolders[0], BorderLayout.NORTH);
        gamePanel.add(boardPanel, BorderLayout.CENTER);
        gamePanel.add(cardHolders[1], BorderLayout.SOUTH);

        sidePanel.add(new JLabel());
        sidePanel.add(new JLabel());
        sidePanel.add(cardHolders[2]);

        this.add(gamePanel, BorderLayout.CENTER);
        this.add(sidePanel, BorderLayout.EAST);
    }


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


    private void initializeJMenu() {

        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_ESCAPE);
        file.getAccessibleContext().setAccessibleDescription("File menu");
        bar.add(file);


        saveMenuItem = new JMenuItem("Save Game");
        saveMenuItem.addActionListener(e -> {
        });
        file.add(saveMenuItem);

        exitToMainMenuItem = new JMenuItem("Exit to MainMenu");
        exitToMainMenuItem.addActionListener(e -> {
        });
        file.add(exitToMainMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit Program");
        exitMenuItem.addActionListener(e -> {
            int userChoice = JOptionPane.showConfirmDialog(getContentPane(),
                    "Do you really want to exit?", "Confirm exit", JOptionPane.YES_NO_OPTION);
            if (userChoice == JOptionPane.YES_OPTION) {
                exit(0);
            }
        });
        file.add(exitMenuItem);

        this.setJMenuBar(bar);
    }


    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public CardHolderPanel[] getCardHolders() {
        return cardHolders;
    }
}
