package onitama.model.moves;

import onitama.utils.ObservedSubject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hand extends ObservedSubject<List<MoveCard>> implements Serializable {

    private final List<MoveCard> cards;
    private final int max;

    public Hand(int max){
        this.max = max;
        cards = new ArrayList<>();
    }

    public boolean addCard(MoveCard card){
        if(cards.size() < max){
            cards.add(card);
            fireUpdated();
            return true;
        }
        return false;
    }

    public boolean removeCard(MoveCard card){
        boolean success = cards.remove(card);
        if (success)
            fireUpdated();
        return success;
    }

    public boolean setCard(MoveCard card, int i){
        if(i >= max) return false;
        if(i < cards.size())
            cards.remove(i);
        cards.add(i,card);
        return true;
    }

    public boolean setCard(MoveCard card, MoveCard exchanged){
        int pos = cards.indexOf(exchanged);
        if(pos == -1) return false;

        cards.remove(exchanged);
        cards.add(pos,card);
        fireUpdated();
        return true;
    }

    public int getMax() {
        return max;
    }

    public List<MoveCard> getCards() {
        return cards;
    }

    @Override
    protected List<MoveCard> getMessage() {
        return cards;
    }
}
