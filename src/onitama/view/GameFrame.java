package onitama.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static java.lang.System.exit;

public class GameFrame extends JFrame {

    private BoardPanel boardPanel;
    private CardHolderPanel cardPanelUp;
    private CardHolderPanel cardPanelDown;
    private JPanel gamePanel;
    private JPanel sidePanel;
    private CardPanel exchangeCard;

    public GameFrame() {
        super("Onitama");
        boardPanel = new BoardPanel();
        cardPanelUp = new CardHolderPanel();
        cardPanelDown = new CardHolderPanel();
        exchangeCard = new CardPanel();
        gamePanel = new JPanel(new BorderLayout());
        sidePanel = new JPanel(new GridLayout(5,1,20,0));
        initialize();

    }

    private void initialize(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900,1000);
        this.setResizable(false);

        initializeJMenu();

        sidePanel.add(new JLabel());
        sidePanel.add(new JLabel());
        sidePanel.add(exchangeCard);

        gamePanel.add(cardPanelUp,BorderLayout.NORTH);
        gamePanel.add(boardPanel,BorderLayout.CENTER);
        gamePanel.add(cardPanelDown,BorderLayout.SOUTH);

        this.add(gamePanel,BorderLayout.CENTER);
        this.add(sidePanel,BorderLayout.EAST);

        this.setVisible(true);
    }

    private void initializeJMenu(){

        JMenuBar bar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_ESCAPE);
        file.getAccessibleContext().setAccessibleDescription("File menu");
        bar.add(file);



        JMenuItem saveMenuItem = new JMenuItem("Save Game");
        saveMenuItem.addActionListener(e -> { });
        file.add(saveMenuItem);

        JMenuItem exitToMainMenuItem = new JMenuItem("Exit to MainMenu");
        exitToMainMenuItem.addActionListener(e -> { });
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

}
