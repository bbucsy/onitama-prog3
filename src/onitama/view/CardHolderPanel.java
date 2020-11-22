package onitama.view;

import onitama.model.moves.MoveCardStorage;

import javax.swing.*;
import java.awt.*;

public class CardHolderPanel extends JPanel {

    private CardPanel[] cards;

    public CardHolderPanel() {
        super();
        cards = new CardPanel[2];
        initialize();
    }



    private void initialize(){
        for (int i = 0; i < 2; i++) {
            cards[i] = new CardPanel();
            this.add(cards[i]);

            cards[i].setCard(MoveCardStorage.getRandomCard());

        }
        this.setBackground(Color.lightGray);
    }
}
