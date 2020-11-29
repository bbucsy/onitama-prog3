package onitama.model.moves;

import onitama.utils.ObservedSubject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand extends ObservedSubject<List<MoveCard>> implements Serializable {

    private final List<MoveCard> cards;
    private final int max;

    public Hand(int max) {
        this.max = max;
        cards = new ArrayList<>();
    }

    public void addCard(MoveCard card) {
        if (cards.size() < max) {
            cards.add(card);
            fireUpdated();
        }
    }


    public void setCard(MoveCard card, int i) {
        if (i >= max) return;
        if (i < cards.size())
            cards.remove(i);
        cards.add(i, card);
    }

    public void setCard(MoveCard card, MoveCard exchanged) {
        int pos = cards.indexOf(exchanged);
        if (pos == -1) return;

        cards.remove(exchanged);
        cards.add(pos, card);
        fireUpdated();
    }

    public List<MoveCard> getCards() {
        return cards;
    }

    @Override
    protected List<MoveCard> getMessage() {
        return cards;
    }
}
