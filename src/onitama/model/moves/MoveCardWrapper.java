package onitama.model.moves;

import onitama.utils.ObservedSubject;

public class MoveCardWrapper extends ObservedSubject<MoveCard> {

    private MoveCard card;


    public MoveCard getCard() {
        return card;
    }

    public void setCard(MoveCard card) {
        this.card = card;
        fireUpdated();;
    }

    @Override
    protected MoveCard getMessage() {
        return this.card;
    }
}
