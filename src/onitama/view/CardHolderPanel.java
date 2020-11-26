package onitama.view;

import onitama.model.moves.MoveCard;
import onitama.model.moves.MoveCardStorage;
import onitama.utils.ObservedSubject;
import onitama.utils.SubjectObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CardHolderPanel extends JPanel implements SubjectObserver<List<MoveCard>> {

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
        }
        this.setBackground(Color.lightGray);
    }

    public CardPanel getCards(int i) {
        return cards[i];
    }

    @Override
    public void update(ObservedSubject<List<MoveCard>> sender, List<MoveCard> message) {
        for (int i = 0; i < 2; i++) {
           cards[i].setCard(message.get(i));
        }
    }
}
