package onitama.view;

import onitama.model.moves.MoveCard;
import onitama.utils.SubjectObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CardHolderPanel extends JPanel implements SubjectObserver<List<MoveCard>> {

    private final CardPanel[] cards;
    private final int n;

    public CardHolderPanel(int n) {
        super();
        this.n = n;
        cards = new CardPanel[n];
        initialize();
    }



    private void initialize(){
        for (int i = 0; i < n; i++) {
            cards[i] = new CardPanel();
            this.add(cards[i]);
        }
        this.setBackground(Color.lightGray);
    }

    public CardPanel getCard(int i) {
        return cards[i];
    }

    @Override
    public void update(List<MoveCard> message) {
        if (message.size() != n) return;
        for (int i = 0; i < n; i++) {
           cards[i].setCard(message.get(i));
        }
    }
}
