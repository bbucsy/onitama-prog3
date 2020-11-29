package onitama.view;

import onitama.controller.GameController;
import onitama.controller.MainController;
import onitama.controller.ai.AbstractAi;
import onitama.controller.ai.PlayerController;
import onitama.model.Game;

import javax.swing.*;
import java.awt.*;


public class MainMenuFrame extends JFrame {


    private final PlayerMenuPanel playerPanel1;
    private final PlayerMenuPanel playerPanel2;
    private final JPanel controlPanel;
    private final JButton newGame;
    private final JButton loadGame;
    private final JTextField p1Name;
    private final JTextField p2Name;

    public MainMenuFrame(){
        super("Onitama");
        playerPanel1 = new PlayerMenuPanel("Player 1");
        playerPanel2 = new PlayerMenuPanel("Player 2");
        controlPanel = new JPanel();
        p1Name = new JTextField(20);
        p2Name = new JTextField(20);
        newGame = new JButton("New game");
        loadGame = new JButton("Load game");
        initializeLayout();
        initializeListeners();
    }

    private void initializeLayout() {
        this.setSize(600,500);
        this.setLayout(new BorderLayout());

        JPanel pPanel = new JPanel(new GridLayout(1,2,10,0));
        pPanel.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));
        pPanel.add(playerPanel1);
        pPanel.add(playerPanel2);


        controlPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        controlPanel.add(newGame);
        controlPanel.add(loadGame);

        this.add(pPanel,BorderLayout.CENTER);
        this.add(controlPanel,BorderLayout.PAGE_END);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }


    private void initializeListeners() {

        newGame.addActionListener(actionEvent -> {
            MainController.getInstance().launchGame(new PlayerController[]{playerPanel1.getPlayerType(),playerPanel2.getPlayerType()});
        });

    }


}
