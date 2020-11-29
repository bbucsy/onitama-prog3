package onitama.view;

import onitama.controller.ai.PlayerController;

import javax.swing.*;
import java.awt.*;


public class MainMenuFrame extends JFrame {


    private final PlayerMenuPanel playerPanel1;
    private final PlayerMenuPanel playerPanel2;
    private final JButton newGame;
    private final JButton loadGame;

    public MainMenuFrame(){
        super("Onitama");
        playerPanel1 = new PlayerMenuPanel("Player 1");
        playerPanel2 = new PlayerMenuPanel("Player 2");
        newGame = new JButton("New game");
        loadGame = new JButton("Load game");
        initializeLayout();
    }

    private void initializeLayout() {
        this.setSize(600,500);
        this.setLayout(new BorderLayout());

        JPanel pPanel = new JPanel(new GridLayout(1,2,10,0));
        pPanel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        pPanel.add(playerPanel1);
        pPanel.add(playerPanel2);

        JPanel controlPanel = new JPanel();
        controlPanel.add(newGame);
        controlPanel.add(loadGame);

        this.add(pPanel,BorderLayout.CENTER);
        this.add(controlPanel,BorderLayout.PAGE_END);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    public PlayerController[] getPlayerControllers(){
        return new PlayerController[]{playerPanel1.getPlayerType(),playerPanel2.getPlayerType()};
    }

    public String[] getPlayerNames(){
        return new String[] {playerPanel1.getName(),playerPanel2.getName()};
    }

    public JButton getNewGame() {
        return newGame;
    }

    public JButton getLoadGame() {
        return loadGame;
    }
}
