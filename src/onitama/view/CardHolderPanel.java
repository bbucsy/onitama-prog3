package onitama.view;

import onitama.model.moves.MoveCard;
import onitama.utils.SubjectObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Panel that holds n pieces of card. It observes a subject that returns a list of cards, and if the
 * list has the same amount of cards that this panel holds, then updates the view.
 *
 * This is essentially the view of a <code>Hand</code>, and wraps card views so that it can
 * update them when a card is exchanged in the hand
 */
public class CardHolderPanel extends JPanel implements SubjectObserver<List<MoveCard>> {

    /**
     * Array of card views
     */
    private final CardPanel[] cards;
    /**
     * The number of card this holder has.
     */
    private final int n;

    /**
     * Constructor of view
     * @param n The number of card this holder has.
     */
    public CardHolderPanel(int n) {
        super();
        this.n = n;
        cards = new CardPanel[n];
        initialize();
    }


    /**
     * Creates n pieces of card views and puts them in a layout.
     */
    private void initialize() {
        for (int i = 0; i < n; i++) {
            cards[i] = new CardPanel();
            this.add(cards[i]);
        }
        this.setBackground(Color.lightGray);
    }

    /**
     *
     * @param i The index of the card view
     * @return The i-th card view.
     */
    public CardPanel getCard(int i) {
        return cards[i];
    }

    /**
     *
     * @param message A message sent by the observed object, usually containing the changes or the object itself
     */
    @Override
    public void update(List<MoveCard> message) {
        if (message.size() != n) return;
        for (int i = 0; i < n; i++) {
            cards[i].setCard(message.get(i));
        }
    }
}
